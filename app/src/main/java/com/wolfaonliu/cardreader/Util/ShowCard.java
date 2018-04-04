package com.wolfaonliu.cardreader.Util;

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

import com.wolfaonliu.cardreader.CardInfo;
import com.wolfaonliu.cardreader.DealFragment;
import com.wolfaonliu.cardreader.DealPreference;
import com.wolfaonliu.cardreader.InfoFragment;
import com.wolfaonliu.cardreader.R;
import com.wolfaonliu.cardreader.TradingRecordInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ShowCard {

    private CardInfo cardInfo;

    private Activity mainActivity;

    private ImageView pgI;
    private TextView atten;
    private CardView dealCard;
    private CardView container;
    private LinearLayout attentionBox;

    private String[] showPart;


    public ShowCard(Activity m) {
        this.mainActivity = m;
        this.container = mainActivity.findViewById(R.id.card_container);
        this.pgI = mainActivity.findViewById(R.id.pgImg);
        this.atten = mainActivity.findViewById(R.id.attention);
        this.dealCard = mainActivity.findViewById(R.id.dealCard);
        this.attentionBox = mainActivity.findViewById(R.id.attentionBox);
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public void showInLog() {
        if (cardInfo.isNewcapecCard()) {
            Log.d("姓名", cardInfo.getStudentName());
            Log.d("学号", cardInfo.getStudentId());
            Log.d("余额", cardInfo.getCardBalance());
            if (cardInfo.getStudentDept() != null)
                Log.d("学院", cardInfo.getStudentDept());
            Log.d("卡ID", cardInfo.getHardwareId());
            Log.d("身份证", cardInfo.getPersonId());
        } else {
            Log.d("ERROR", "非校园卡");
            Log.d("卡ID", cardInfo.getHardwareId());
        }
    }


    /*
        key
        显示条目：data_to_show
        显示模式：show_mode
        储存功能：data_save
        储存条目：data_to_save
    */
    private void readpref(String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        Set<String> s = new HashSet<>();
        for (int i = 0; i < 8; i++)
            s.add(String.valueOf(i + 1));
        Set<String> p = pref.getStringSet(key, s);

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
        body[1] = cardInfo.getStudentName();
        body[2] = cardInfo.getStudentId();
        body[3] = cardInfo.getCardBalance();
        body[4] = cardInfo.getHardwareId();
        body[5] = cardInfo.getStudentDept();
        body[6] = cardInfo.getPersonId();
        return body;
    }

    private Bundle bundle_init(String title, String body) {
        Bundle bundle = new Bundle();
        bundle.putString("name", title);
        bundle.putString("body", body);
        return bundle;
    }

    private boolean showJudge(int i) {
        for (String aShowPart : showPart) {
            if (Integer.parseInt(aShowPart) == (i + 1))
                return false;
        }
        return true;
    }

    public boolean show() {
        InfoFragment infoFragment;
        readpref("data_to_show");
        if (cardInfo == null)
            return false;
        if (cardInfo.isNewcapecCard()) {
            container.setVisibility(View.VISIBLE);
            attentionBox.setVisibility(View.INVISIBLE);
            Util.aToast(mainActivity.getString(R.string.success), mainActivity);
            String[] title = title_init();
            String[] body = body_init();
            int runtime = 0;
            for (int i = 0; i < title.length; i++) {
                if (showJudge(i))
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

            if (!cardInfo.getTradeList().isEmpty()) {

                ArrayList<TradingRecordInfo> tradeList = cardInfo.getTradeList();
                if (showJudge(7)) {
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
//            ActionMenuItemView menu = mainActivity.findViewById(R.id.refresh);
//            menu.setVisibility(View.VISIBLE);
            return !cardInfo.getStudentName().isEmpty() && !cardInfo.getStudentId().isEmpty() && !cardInfo.getCardBalance().isEmpty();
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


    public void onFinish(boolean isFull) {
        pgI.setVisibility(View.VISIBLE);
        if (!isFull) {
            //未能复现……
            atten.setText(mainActivity.getString(R.string.reading_alert));
            pgI.setColorFilter(Color.parseColor("#259b24"));
        } else if (cardInfo.isNewcapecCard()) {
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

}
