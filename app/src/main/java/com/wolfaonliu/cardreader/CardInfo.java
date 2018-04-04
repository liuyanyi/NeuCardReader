package com.wolfaonliu.cardreader;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Mogician on 2018/3/12.
 */

public class CardInfo {


    private String hardwareId = "";
    private String studentId = "";
    private String studentName = "";
    private String cardBalance = "";
    private String studentDept = "";
    private String personId = "";

    private ArrayList<TradingRecordInfo> tradeList = new ArrayList<>();

    private boolean isNewcapecCard = false;

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

    public void setCardBalance(String cardBalance, Activity activity) {
        if (cardBalance != null)
            isNewcapecCard = true;
        this.cardBalance = cardBalance + activity.getString(R.string.yuan);
    }

    public void setStudentDept(String studentDept) {
        this.studentDept = studentDept;
    }

    public void addDeal(TradingRecordInfo tradingRecordInfo) {
        tradeList.add(tradingRecordInfo);
    }


    public boolean isNewcapecCard() {
        return isNewcapecCard;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getStudentDept() {
        return studentDept;
    }

    public String getPersonId() {
        return personId;
    }

    public ArrayList<TradingRecordInfo> getTradeList() {
        return tradeList;
    }

    public void setTradeList(ArrayList<TradingRecordInfo> tradeList) {
        this.tradeList = tradeList;
    }
}
