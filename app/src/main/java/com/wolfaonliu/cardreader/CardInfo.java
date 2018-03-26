package com.wolfaonliu.cardreader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wolfaonliu.cardreader.Util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mogician on 2018/3/12.
 */

public class CardInfo {

    private Activity mainActivity;

    private String hardwareId = "";
    private String studentId = "";
    private String studentName = "";
    private String cardBalance = "";
    private String studentDept = "";
    private String personId = "";

    private ArrayList<TradingRecordInfo> tradeList = new ArrayList<>();
    private String[] showPart;

    private boolean isNewcapecCard = false;

    private ImageView pgI;
    private TextView atten;
    private CardView dealCard;
    private CardView container;
    private LinearLayout attentionBox;

    CardInfo(Activity m) {
        this.mainActivity = m;
        this.container = mainActivity.findViewById(R.id.card_container);
        this.pgI = mainActivity.findViewById(R.id.pgImg);
        this.atten = mainActivity.findViewById(R.id.attention);
        this.dealCard = mainActivity.findViewById(R.id.dealCard);
        this.attentionBox = mainActivity.findViewById(R.id.attentionBox);
        readpref();
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
        if (studentId != null)
            isNewcapecCard = true;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
        if (studentName != null)
            isNewcapecCard = true;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setCardBalance(String cardBalance) {
        if (cardBalance != null)
            isNewcapecCard = true;
        this.cardBalance = cardBalance + mainActivity.getString(R.string.yuan);
    }

    public void setStudentDept(String studentDept) {
        this.studentDept = studentDept;
    }

    public void onFinish(boolean isFull) {
        pgI.setVisibility(View.VISIBLE);
        if (!isFull) {
            //未能复现……
            atten.setText(mainActivity.getString(R.string.reading_alert));
            pgI.setColorFilter(Color.parseColor("#259b24"));
        } else if (isNewcapecCard) {
            atten.setText(mainActivity.getString(R.string.success));
//            String[] strings;
//            strings = readpref().toArray(new String[] {});
//            StringBuilder test= new StringBuilder();
//            for (String string : strings) test.append(string).append(" ");
//            atten.setText(test);
            pgI.setColorFilter(Color.parseColor("#259b24"));
        } else {
            atten.setText(mainActivity.getString(R.string.failed));
            pgI.setColorFilter(Color.parseColor("#e51c23"));
        }
    }

    public void addDeal(TradingRecordInfo tradingRecordInfo) {
        tradeList.add(tradingRecordInfo);
    }

    public void showInLog() {
        if (isNewcapecCard) {
            Log.d("姓名", studentName);
            Log.d("学号", studentId);
            Log.d("余额", cardBalance);
            if (studentDept != null)
                Log.d("学院", studentDept);
            Log.d("卡ID", hardwareId);
            Log.d("身份证", personId);
        } else {
            Log.d("ERROR", "非校园卡");
            Log.d("卡ID", hardwareId);
        }
    }

    public void readpref() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        Set<String> s = new HashSet<>();
        for (int i = 0; i < 8; i++)
            s.add(String.valueOf(i + 1));
        Set<String> p = pref.getStringSet("multi_select_list_preference_1", s);

        this.showPart = p.toArray(new String[]{});
    }

    private String[] title_init() {
        String[] title = new String[7];
        title[0] = mainActivity.getString(R.string.card_cate);
        title[1] = mainActivity.getString(R.string.name);
        title[2] = mainActivity.getString(R.string.student_Id);
        title[3] = mainActivity.getString(R.string.balance);
        title[4] = mainActivity.getString(R.string.hardware_Id);
        title[5] = mainActivity.getString(R.string.Dept);
        title[6] = mainActivity.getString(R.string.pId);
        return title;
    }

    private String[] body_init() {
        String[] body = new String[7];
        body[0] = mainActivity.getString(R.string.isStuCard);
        body[1] = studentName;
        body[2] = studentId;
        body[3] = cardBalance;
        body[4] = hardwareId;
        body[5] = studentDept;
        body[6] = personId;
        return body;
    }

    private Bundle bundle_init(String title, String body) {
        Bundle bundle = new Bundle();
        bundle.putString("name", title);
        bundle.putString("body", body);
        return bundle;
    }

    public boolean showJudge(int i) {
        for (int m = 0; m < showPart.length; m++) {
            if (Integer.parseInt(showPart[m]) == (i + 1))
                return true;
        }
        return false;
    }

    public boolean show() {
        InfoFragment infoFragment;
        if (isNewcapecCard) {
            container.setVisibility(View.VISIBLE);
            attentionBox.setVisibility(View.INVISIBLE);
            Util.aToast(mainActivity.getString(R.string.success), mainActivity);
            String[] title = title_init();
            String[] body = body_init();
            int runtime = 0;
            for (int i = 0; i < title.length; i++) {
                if (!showJudge(i))
                    continue;
                runtime++;
                infoFragment = new InfoFragment();
                infoFragment.setArguments(bundle_init(title[i], body[i]));
                if (runtime == 1)
                    mainActivity.getFragmentManager().beginTransaction().
                            replace(R.id.info_container, infoFragment).commit();
                else
                    mainActivity.getFragmentManager().beginTransaction().
                            add(R.id.info_container, infoFragment).commit();
            }

            if (runtime == 0) {
                attentionBox.setVisibility(View.VISIBLE);
                container.setVisibility(View.GONE);
            }

            if (!tradeList.isEmpty()) {

                if (!showJudge(7)) {
                    dealCard.setVisibility(View.INVISIBLE);
                } else {
                    DealFragment dealFragment = new DealFragment();

                    mainActivity.getFragmentManager().beginTransaction().replace(R.id.deal_container, dealFragment).commit();

                    for (int i = 0; i < tradeList.size(); i++) {
                        addDealPreference(dealFragment, tradeList.get(i));
                    }
                    dealCard.setVisibility(View.VISIBLE);
                }
            }

            if (studentName.isEmpty() || studentId.isEmpty() || cardBalance.isEmpty())
                return false;
        } else {
            DealFragment d = new DealFragment();
            mainActivity.getFragmentManager().beginTransaction().replace(R.id.deal_container, d).commit();
            container.setVisibility(View.GONE);
            dealCard.setVisibility(View.INVISIBLE);
            attentionBox.setVisibility(View.VISIBLE);
        }
        return true;

    }

    private void addDealPreference(DealFragment dealFragment, TradingRecordInfo tradingRecordInfo) {
        DealPreference dealPreference = new DealPreference(mainActivity, null);
        mainActivity.getFragmentManager().executePendingTransactions();
        dealPreference.setInfo(tradingRecordInfo);
        dealFragment.getPreferenceScreen().addPreference(dealPreference);
    }
}
