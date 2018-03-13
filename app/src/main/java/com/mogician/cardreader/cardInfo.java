package com.mogician.cardreader;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Mogician on 2018/3/12.
 */

public class cardInfo {

    private MainActivity mainActivity;

    private String hardwareId = "";

    private String studentId = "";

    private String studentName = "";

    private String cardBalance = "";

    private String studentDept = "";

    private String personId = "";

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

    cardInfo(MainActivity m) {
        this.mainActivity = m;
        this.isCard = (TextView) mainActivity.findViewById(R.id.isNewcapec);
        this.name = (TextView) mainActivity.findViewById(R.id.Name);
        this.id = (TextView) mainActivity.findViewById(R.id.Id);
        this.balance = (TextView) mainActivity.findViewById(R.id.Balance);
        this.hardware = (TextView) mainActivity.findViewById(R.id.Hardware);
        this.dept = (TextView) mainActivity.findViewById(R.id.Dept);
        this.pId = (TextView) mainActivity.findViewById(R.id.personId);
        this.pgI = (ImageView) mainActivity.findViewById(R.id.pgImg);
        this.pgBar = (ProgressBar) mainActivity.findViewById(R.id.pgBar);
        this.atten = (TextView) mainActivity.findViewById(R.id.attention);
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
        this.cardBalance = cardBalance + "元";
    }

    public void setStudentDept(String studentDept) {
        this.studentDept = studentDept;
    }


    public void onStart() {
        pgI.setVisibility(View.GONE);
        pgBar.setVisibility(View.VISIBLE);
        atten.setText("读取中");
    }

    public void onFinish() {
        pgI.setVisibility(View.VISIBLE);
        pgBar.setVisibility(View.GONE);
        if (isNewcapecCard) {
            atten.setText("成功");
            pgI.setColorFilter(Color.parseColor("#66ccff"));
        } else {
            atten.setText("失败,非校园卡");
            pgI.setColorFilter(Color.RED);
        }
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

    public void show() {
        if (isNewcapecCard) {
            isCard.setText("校园卡");

            name.setText(studentName);

            id.setText(studentId);

            balance.setText(cardBalance);

            hardware.setText(hardwareId);

            dept.setText(studentDept);

            pId.setText(personId);
        } else {
            isCard.setText("非校园卡");

            hardware.setText(hardwareId);

            name.setText("");
            id.setText("");
            balance.setText("");
            dept.setText("");
            pId.setText("");

        }

    }
}
