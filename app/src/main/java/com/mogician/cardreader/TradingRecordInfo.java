package com.mogician.cardreader;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;


//TODO 单个交易记录类
public class TradingRecordInfo implements Serializable, Comparable<TradingRecordInfo> {
    private static final long serialVersionUID = -6533154177080647539L;
    private String tradingDateTime;
    private String tradingMoney;
    private String tradingType;

    public int compareTo(@NonNull TradingRecordInfo tradingRecordInfo) {
        return tradingRecordInfo.getTradingDateTime().compareTo(this.tradingDateTime);
    }

    public String getTradingDateTime() {
        return this.tradingDateTime;
    }

    public String getTradingMoney() {
        return this.tradingMoney;
    }

    public String getTradingType() {
        return this.tradingType;
    }

    public void setTradingDateTime(String str) {
        this.tradingDateTime = str.substring(0, 4) + "." + str.substring(4, 6) + "." + str.substring(6, 8) + " " + str.substring(8, 10) + ":" + str.substring(10, 12);
    }

    public void setTradingMoney(long j) {
        this.tradingMoney = String.format(Locale.getDefault(), "%.2f", j / 100.0);
    }

    public void setTradingType(int i) {
        switch (i) {
            case 6:
                this.tradingType = "消费";
                break;
            case 2:
                this.tradingType = "充值";
                break;
            default:
                this.tradingType = String.valueOf(i);
        }

    }
}
