package com.mogician.cardreader;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mogician on 2018/3/12.
 */

public class cardInfo {

    private MainActivity mainActivity;

    private String hardwareId;

    private String studentId;

    private String studentName;

    private String cardBalance;

    private String studentDept=null;

    private boolean isNewcapecCard=false;

    private TextView name;
    private TextView id;
    private TextView balance;
    private TextView hardware;
    private TextView dept;
    private TextView isCard;

    cardInfo(MainActivity m){
        this.mainActivity=m;
        this.isCard = (TextView) mainActivity.findViewById(R.id.isNewcapec);
        this.name = (TextView) mainActivity.findViewById(R.id.Name);
        this.id = (TextView) mainActivity.findViewById(R.id.Id);
        this.balance = (TextView) mainActivity.findViewById(R.id.Balance);
        this.hardware = (TextView) mainActivity.findViewById(R.id.Hardware);
        this.dept = (TextView) mainActivity.findViewById(R.id.Dept);
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
        if(studentId!=null)
            isNewcapecCard=true;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
        if(studentName!=null)
            isNewcapecCard=true;
    }

    public void setCardBalance(String cardBalance) {
        if(cardBalance!=null)
            isNewcapecCard=true;
        this.cardBalance = cardBalance+"元";
    }

    public void setStudentDept(String studentDept) {
        this.studentDept = studentDept;
    }

    public void showInLog(){
        if(isNewcapecCard) {
            Log.d("姓名", studentName);
            Log.d("学号", studentId);
            Log.d("余额", cardBalance);
            if (studentDept != null)
                Log.d("学院", studentDept);
            Log.d("卡ID", hardwareId);
        }
        else{
            Log.d("ERROR","非校园卡");
            Log.d("卡ID", hardwareId);
        }
    }

    public void show(){
        if(isNewcapecCard){
            isCard.setText("校园卡");
            isCard.setVisibility(View.VISIBLE);

            name.setText(studentName);
            name.setVisibility(View.VISIBLE);

            id.setText(studentId);
            id.setVisibility(View.VISIBLE);

            balance.setText(cardBalance);
            balance.setVisibility(View.VISIBLE);

            hardware.setText(hardwareId);
            hardware.setVisibility(View.VISIBLE);

            dept.setText(studentDept);
            dept.setVisibility(View.VISIBLE);
        }
        else{
            isCard.setText("非校园卡");
            isCard.setVisibility(View.VISIBLE);

            hardware.setText(hardwareId);
            hardware.setVisibility(View.VISIBLE);

            name.setVisibility(View.GONE);
            id.setVisibility(View.GONE);
            balance.setVisibility(View.GONE);
            dept.setVisibility(View.GONE);
        }

    }
}
