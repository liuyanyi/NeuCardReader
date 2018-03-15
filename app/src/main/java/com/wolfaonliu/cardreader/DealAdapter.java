package com.wolfaonliu.cardreader;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mogician on 2018/3/14.
 */

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    private ArrayList<TradingRecordInfo> mData;
    private Activity activity;

    public DealAdapter(ArrayList<TradingRecordInfo> data, Activity activity) {
        this.mData = data;
        this.activity = activity;
    }

    public void updateData(ArrayList<TradingRecordInfo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        // 实例化viewholder
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        String m = mData.get(position).getTradingMoney();
        int t = mData.get(position).getTradingType();
        holder.time.setText(mData.get(position).getTradingDateTime());
        holder.money.setText(m);
        switch (t) {
            case 2:
                holder.type.setText(activity.getString(R.string.recharge));
                break;
            case 6:
                holder.type.setText(activity.getString(R.string.consumption));
                break;
            default:
                holder.type.setText(t);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView money;
        TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            money = itemView.findViewById(R.id.deal);
            time = itemView.findViewById(R.id.time);
        }
    }
}
