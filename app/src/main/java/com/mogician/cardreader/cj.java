package com.mogician.cardreader;

import android.view.View;

/**
 * Created by Mogician on 2018/3/11.
 */

//写入数据

//TODO 功能移入cardInfo类

class cj implements b {
    final /* synthetic */ MainActivity a;

    cj(MainActivity mainActivity) {
        this.a = mainActivity;
    }


    public void NfcMainfare() {
        this.a.q.setText(String.valueOf(Float.parseFloat(this.a.w) + Float.parseFloat(this.a.x)) + "元");
    }

    public void NfcTradeInfo(TradingRecordInfo tradingRecordInfo) {
        this.a.v.a(tradingRecordInfo);
        this.a.t.setText("共" + this.a.v.getCount() + "笔");
    }

    public void NfcName(String str) {
        this.a.y = str;
        this.a.m.setText(str);

    }

    public void NfcCardID(String str) {
        this.a.n.setText(str);
    }

    public void c(String str) {
        this.a.r.setVisibility(View.VISIBLE);
        this.a.s.setVisibility(View.VISIBLE);
        this.a.o.setText(str);
    }

    public void d(String str) {
        this.a.r.setVisibility(View.GONE);
        this.a.s.setVisibility(View.GONE);
    }

    public void e(String str) {
        this.a.w = str;
    }

    public void f(String str) {
        this.a.x = str;
    }
}
