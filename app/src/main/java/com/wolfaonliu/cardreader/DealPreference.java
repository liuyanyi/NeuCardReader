package com.wolfaonliu.cardreader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mogician on 2018/3/21.
 */

public class DealPreference extends Preference {

    private static final String TAG = "DealPreference";
    private Context c;
    private String date = null;
    private String type = null;
    private String money = null;


    @SuppressLint("Recycle")
    public DealPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        c = context;
        Log.i(TAG, "DealPreference invoked");
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DealPreference);
        date = ta.getString(R.styleable.DealPreference_Date_string);
        type = ta.getString(R.styleable.DealPreference_Type_string);
        money = ta.getString(R.styleable.DealPreference_Money_String);
        ta.recycle();
    }

    public DealPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView dateView = view.findViewById(R.id.time);
        dateView.setText(date);
        TextView typeView = view.findViewById(R.id.type);
        typeView.setText(type);
        TextView moneyView = view.findViewById(R.id.deal);
        moneyView.setText(money);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        return LayoutInflater.from(getContext()).inflate(R.layout.deal_item,
                parent, false);
    }

    public void setInfo(TradingRecordInfo t) {
        date = t.getTradingDateTime();
        int temp = t.getTradingType();
        switch (temp) {
            case 2:
                type = c.getString(R.string.recharge);
                break;
            case 6:
                type = c.getString(R.string.consumption);
                break;
            default:
                type = String.valueOf(temp);
                break;
        }
        money = t.getTradingMoney();
    }
}
