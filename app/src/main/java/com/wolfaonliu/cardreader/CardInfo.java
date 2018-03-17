package com.wolfaonliu.cardreader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    private TextView name;
    private TextView id;
    private TextView balance;
    private TextView hardware;
    private TextView dept;
    private TextView isCard;
    private TextView pId;
    private ImageView pgI;
    private TextView atten;
    private ProgressBar pgBar;
    private CardView dealCard;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ArrayList<TradingRecordInfo> getTradeList() {
        return tradeList;
    }

    CardInfo(Activity m) {
        this.mainActivity = m;
        this.isCard = mainActivity.findViewById(R.id.isNewcapec);
        this.name = mainActivity.findViewById(R.id.Name);
        this.id = mainActivity.findViewById(R.id.Id);
        this.balance = mainActivity.findViewById(R.id.Balance);
        this.hardware = mainActivity.findViewById(R.id.Hardware);
        this.dept = mainActivity.findViewById(R.id.Dept);
        this.pId = mainActivity.findViewById(R.id.personId);
        this.pgI = mainActivity.findViewById(R.id.pgImg);
        this.pgBar = mainActivity.findViewById(R.id.pgBar);
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


    public void onStart() {
        pgI.setVisibility(View.GONE);
        pgBar.setVisibility(View.VISIBLE);
        atten.setText("读取中");
    }

    public void onFinish(boolean isFull) {
        pgI.setVisibility(View.VISIBLE);
        pgBar.setVisibility(View.GONE);
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
    public boolean show() {
        if (isNewcapecCard) {
//            isCard.setText(mainActivity.getString(R.string.isStuCard));
            isCard.setText(readpref());

            name.setText(studentName);

            id.setText(studentId);

            balance.setText(cardBalance);

            hardware.setText(hardwareId);

            dept.setText(studentDept);

            pId.setText(personId);

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
            isCard.setText(mainActivity.getString(R.string.unsupport));

            hardware.setText(hardwareId);

            name.setText("");
            id.setText("");
            balance.setText("");
            dept.setText("");
            pId.setText("");
            dealCard.setVisibility(View.GONE);

        }
        return true;

    }
}
