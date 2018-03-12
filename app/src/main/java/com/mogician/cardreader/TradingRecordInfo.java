package com.mogician.cardreader;

import java.io.Serializable;


//TODO 用于交易记录，整理到其他类后删除
public class TradingRecordInfo implements Serializable, Comparable<TradingRecordInfo> {
    private static final long serialVersionUID = -6533154177080647539L;
    private String tradingDateTime;
    private long tradingMoney;
    private int tradingType;

    public int compareTo(TradingRecordInfo tradingRecordInfo) {
        return tradingRecordInfo.getTradingDateTime().compareTo(this.tradingDateTime);
    }

    public String getTradingDateTime() {
        return this.tradingDateTime;
    }

    public long getTradingMoney() {
        return this.tradingMoney;
    }

    public int getTradingType() {
        return this.tradingType;
    }

    public void setTradingDateTime(String str) {
        this.tradingDateTime = str;
    }

    public void setTradingMoney(long j) {
        this.tradingMoney = j;
    }

    public void setTradingType(int i) {
        this.tradingType = i;
    }
}
