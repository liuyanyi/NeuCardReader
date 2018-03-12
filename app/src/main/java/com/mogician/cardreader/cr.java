package com.mogician.cardreader;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO 工具类更名+整理
public class cr extends BaseAdapter {
    private List<TradingRecordInfo> a;
    private Activity b;
    private DateFormat c = new SimpleDateFormat("yyyyMMddHHmmss");
    private DateFormat d = new SimpleDateFormat("MM-dd HH:mm");

    public cr(Activity activity) {
        this.b = activity;
        this.a = new ArrayList();
    }

    public void a() {
        this.a.clear();
        notifyDataSetChanged();
    }

    public void a(TradingRecordInfo tradingRecordInfo) {
        Object obj = null;
        for (TradingRecordInfo tradingRecordInfo2 : this.a) {
            Object obj2 = (tradingRecordInfo2.getTradingDateTime().equals(tradingRecordInfo.getTradingDateTime()) && tradingRecordInfo2.getTradingMoney() == tradingRecordInfo.getTradingMoney() && tradingRecordInfo2.getTradingType() == tradingRecordInfo.getTradingType()) ? 1 : obj;
            obj = obj2;
        }
        if (obj == null) {
            this.a.add(tradingRecordInfo);
            Collections.sort(this.a);
            notifyDataSetChanged();
        }
    }

    public void a(List<TradingRecordInfo> list) {
        this.a.addAll(list);
        Collections.sort(this.a);
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.a.size();
    }

    public Object getItem(int i) {
        return this.a.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

}
