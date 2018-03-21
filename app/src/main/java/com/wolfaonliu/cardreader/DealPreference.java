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

/**
 * Created by Mogician on 2018/3/21.
 */

public class DealPreference extends Preference {

    private static final String TAG = "PreferenceWithTip";
    String pTitle = null;
    String tipstring = null;

    @SuppressLint("Recycle")
    public DealPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.i(TAG, "PreferenceWithTip invoked");
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DealPreference);
//        tipstring = ta.getString(R.styleable.PreferenceWithTip_tipstring);
//        pTitle = ta.getString(R.styleable.PreferenceWithTip_titlestring);
        ta.recycle();
    }

    public DealPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
//        TextView pTitleView = (TextView) view.findViewById(R.id.prefs_title);
//        pTitleView.setText(pTitle);
//        TextView pTipView = (TextView) view.findViewById(R.id.prefs_tip);
//        pTipView.setText(tipstring);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        return LayoutInflater.from(getContext()).inflate(R.layout.deal_item,
                parent, false);
    }
}
