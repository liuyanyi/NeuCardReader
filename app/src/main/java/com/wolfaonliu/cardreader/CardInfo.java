package com.wolfaonliu.cardreader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

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

    private boolean isNewcapecCard = false;

    private ImageView pgI;
    private TextView atten;
    private CardView dealCard;
    private LinearLayout container;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ArrayList<TradingRecordInfo> getTradeList() {
        return tradeList;
    }

    CardInfo(Activity m) {
        this.mainActivity = m;
        this.container = mainActivity.findViewById(R.id.info_container);
        this.pgI = mainActivity.findViewById(R.id.pgImg);
        this.atten = mainActivity.findViewById(R.id.attention);
        this.dealCard = mainActivity.findViewById(R.id.dealCard);

        this.mLayoutManager = new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
        this.mRecyclerView = mainActivity.findViewById(R.id.dealList);
        // 设置布局管理器
        this.mRecyclerView.setLayoutManager(mLayoutManager);
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

    public String readpref() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        return pref.getString("example_text", "budui");
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

    public boolean show() {
        InfoFragment infoFragment;
        if (isNewcapecCard) {
            container.setVisibility(View.VISIBLE);
            String[] title = title_init();
            String[] body = body_init();
            for (int i = 0; i < title.length; i++) {
                infoFragment = new InfoFragment();
                infoFragment.setArguments(bundle_init(title[i], body[i]));
                if (i == 0)
                    mainActivity.getFragmentManager().beginTransaction().
                            replace(R.id.info_container, infoFragment).commit();
                else
                    mainActivity.getFragmentManager().beginTransaction().
                            add(R.id.info_container, infoFragment).commit();
            }
            if (!getTradeList().isEmpty()) {
                dealCard.setVisibility(View.VISIBLE);
                // 设置adapter
                mAdapter = new DealAdapter(getTradeList(), mainActivity);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new ItemDivider(mainActivity, LinearLayoutManager.VERTICAL));
            }
            if (studentName.isEmpty() || studentId.isEmpty() || cardBalance.isEmpty())
                return false;
        } else {
            container.setVisibility(View.GONE);
            dealCard.setVisibility(View.GONE);
        }
        return true;

    }
}
