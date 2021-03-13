package com.wolfaonliu.cardreader;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Mogician on 2018/3/14.
 */

public class CardReader {


    public static CardInfo readCard(Activity activity, Intent intent) {
        //TODO 拆分成多个方法
        CardInfo card = new CardInfo(activity);

        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tagFromIntent == null)
            return null;
        card.setHardwareId(Util.byteToHex(tagFromIntent.getId()));


        String action = intent.getAction();
        if (!"android.nfc.action.TAG_DISCOVERED".equals(action) && !"android.nfc.action.TECH_DISCOVERED".equals(action) && !"android.nfc.action.NDEF_DISCOVERED".equals(action)) {
            return null;
        }

        IsoDep isoDep = IsoDep.get((Tag) intent.getParcelableExtra("android.nfc.extra.TAG"));
        if (isoDep == null) {
//            extraID();
//            a(this.NfcMainfare);
            return null;
        }


        try {

            isoDep.connect();
//            a(this.NfcMainfare);
//            extraID();
            isoDep.transceive(g.a(fInts.a));
            byte[] transceive = isoDep.transceive(g.a(fInts.c));
            if (transceive != null && g.c(transceive)) {
                transceive = isoDep.transceive(g.a(fInts.d));
                if (transceive == null || !g.c(transceive)) {
                    Util.aToast(activity.getString(R.string.read_failed), activity);
                } else {
                    byte[] a;


                    transceive = isoDep.transceive(g.a(fInts.j));
                    //名字
                    if (transceive != null && g.c(transceive)) {
                        a = g.a(transceive);
                        g.a(a, 0, transceive, 0, transceive.length - 2);
                        action = g.a(a, 0, a.length, "GB18030").trim();
//                        Log.d("名字", action);
                        if (Util.g(action)) {
                            card.setStudentName(action);
//                            Log.d("名字", action);
//                            aToast(action);
//                            Log.d("名字","r1");
                        }
                    }

                    transceive = isoDep.transceive(g.a(fInts.h));
                    //卡号
                    if (transceive != null && g.c(transceive)) {
                        a = g.a(transceive);
                        g.a(a, 0, transceive, 0, transceive.length - 2);
                        action = g.a(a, 0, a.length, "GB18030").trim();
//                        Log.d("卡号", action);
                        card.setStudentId(action);
                    }

                    transceive = isoDep.transceive(g.a(fInts.personId));
                    //身份证
                    if (transceive != null && g.c(transceive)) {
                        a = g.a(transceive);
                        g.a(a, 0, transceive, 0, transceive.length - 2);
                        action = g.a(a, 0, a.length, "GB18030").trim();
//                        Log.d("卡号", action);
                        card.setPersonId(action);
                    }


                    transceive = isoDep.transceive(g.a(fInts.g));
                    //学院
                    if (transceive != null && g.c(transceive)) {
                        a = g.a(transceive);
                        g.a(a, 0, transceive, 0, transceive.length - 2);
                        action = g.a(a, 0, a.length, "GB18030").trim();
//                        Log.d("***", action);
                        if (Util.d(action) || Util.a(action) || action.contains("000000")) {
//                                    bVar.d(action);
//                            Log.d("学院******", action);
                        } else {
//                          bVar.c(action);
//                            Log.d("学院***", action);
                            card.setStudentDept(action);
                        }
                    }

                    int intValue;
                    transceive = isoDep.transceive(g.a(fInts.e));
                    //余额
                    if (transceive != null && g.c(transceive)) {
                        transceive = isoDep.transceive(g.a(fInts.n));
                        if (transceive != null && g.c(transceive)) {
                            transceive = g.a(transceive);
                            intValue = Integer.valueOf(g.a(transceive, transceive.length), 16);
                            action = (intValue / 100) + "." + (intValue % 100);

//                                    bVar.e(action);
//                            Log.d("余额", action);
                            card.setCardBalance(action);
                        }
                    }


                    ReadTrade(isoDep, card);

//测试部分，用于遍历卡内储存地址
//
//                    for(int j=0;j<fInts.tt.length;j++)
////                        transceive = isoDep.transceive(g.a(fInts.tt[j]));
//                        for(int ii=0x961600;ii<0x961700;ii=ii+0x1){
//                            String sss;
//                        if(ii<=0xF)
//                            sss="00B0"+"00000"+Integer.toHexString(ii).toUpperCase();
//                        else if(ii<0xFF)
//                            sss="00B0"+"0000"+Integer.toHexString(ii).toUpperCase();
//                        else if(ii<0xFFF)
//                            sss="00B0"+"000"+Integer.toHexString(ii).toUpperCase();
//                        else if(ii<0xFFFF)
//                            sss="00B0"+"00"+Integer.toHexString(ii).toUpperCase();
//                        else if(ii<0xFFFFF)
//                            sss="00B0"+"0"+Integer.toHexString(ii).toUpperCase();
//                        else
//                            sss="00B0"+""+Integer.toHexString(ii).toUpperCase();
//
//
//                            Log.d("===", "============="+j+","+sss+"=================");
//
//                            transceive = isoDep.transceive(g.a(fInts.tt[j]));
//                    //测试
//                    if (transceive != null && g.c(transceive)) {
//                        transceive = isoDep.transceive(g.a(sss));
//                        if (transceive != null && g.c(transceive)) {
////                            Log.d("测试", byteToHex(transceive));
//                            transceive = g.a(transceive);
////                            System.out.println(transceive);
////                            for(byte e : transceive) {
////                                System.out.print(e + " ");
////                            }
//                            String str2=new String(transceive);
////                            System.out.println("\n打印2："+str2);
////                            Log.d("测试", byteToHex(transceive));
////                            Log.d("测试", transceive.toString().trim());
//                            Log.d("测试", new String(transceive,"GB18030").trim());
////                            Log.d("测试", new String(transceive,"UTF-8").trim());
////                            Log.d("测试", new String(transceive,"ISO-8859-1").trim());
//
//                            try {
//                                intValue = Integer.valueOf(g.a(transceive, transceive.length), 16).intValue();
//                                action = (intValue / 100) + "." + (intValue % 100);
////                                    bVar.e(action);
//                                Log.d("测试", action);
//                            }catch (NumberFormatException e){
//                                Log.d("测试", "boom");
//                            }
//
////                            card.setCardBalance(action);
//                        }else{
////                            Log.d("测试", "无2");
//                        }
////                        a(isoDep);
//                    }else{
////                        Log.d("测试", "无1");
//                    }
////                                Log.d("===", "======================================");
//
//                    }
//
//for(int j=0;j<fInts.tt.length;j++) {
//    transceive = isoDep.transceive(g.a(fInts.tt[j]));
//    if (transceive != null && g.c(transceive)) {
//        a = g.ReadTrade(transceive);
//        Log.d("测试0,"+j, String.valueOf(a));
//        Log.d("测试0.5,"+j, g.a(transceive, transceive.length));
//        g.a(ReadTrade, 0, transceive, 0, transceive.length - 2);
//        Log.d("测试1,"+j, String.valueOf(a));
//        action = g.a(ReadTrade, 0, ReadTrade.length, "UTF-8").trim();
////                        Log.d("***", action);
//        Log.d("测试2,"+j, action);
//        Log.d("eiiiiii", "======================================");
//        if (Util.d(action) || a(action) || action.contains("000000")) {
////                                    bVar.d(action);
////                            Log.d("学院******", action);
//        } else {
////                          bVar.c(action);
////                            Log.d("学院***", action);
////                            card.setStudentDept(action);
//        }
//    } else {
//        Log.d("测试", "无,"+j);
//        Log.d("eiiiiii", "======================================");
//    }
//
//}
//
//
//                    //未知3，同交易记录
//                    transceive = isoDep.transceive(g.a(fInts.f));
//                    if (transceive != null && g.c(transceive)) {
//                        transceive = isoDep.transceive(g.a(fInts.n));
//                        if (transceive != null && g.c(transceive)) {
//                            transceive = g.a(transceive);
//                            intValue = Integer.valueOf(g.a(transceive, transceive.length), 16);
//                            action = (intValue / 100) + "." + (intValue % 100);
////                                    bVar.f(action);
//                            Log.d("未知3***", action);
//
//                        }
//                        ReadTrade(isoDep);
//                    }
////                    Log.d("***", String.valueOf(Float.parseFloat(this.w) + Float.parseFloat(this.x)) + "元");


                }
            }
            try {
                isoDep.close();
            } catch (IOException e) {
                Log.d("ERR", "close fail");
                
            }
        } catch (IOException e2) {
            String str = "****";
            Log.d("ERR", "ERROR");
            action = e2.getLocalizedMessage() == null ? "" : e2.getLocalizedMessage();
            Log.e(str, action);
            try {
                isoDep.close();
            } catch (IOException e22) {
                Log.d("ERR", "close fail");
                
            }

        }
        return card;
    }


    public static void ReadTrade(IsoDep isoDep, CardInfo card) {
        for (int i = 0; i < 10; i++) {
            byte[] a = g.a(fInts.t);
            a[2] = (byte) (i + 1);
            try {
                a = isoDep.transceive(a);
//                DebugUtil.a("读钱包交易记录：", e.b(readCard));
//                Log.d("读钱包交易记录：", e.b(a));
                if (a != null && g.c(a) && Util.g(e.b(g.a(a)).replaceAll("0", ""))) {
                    TradingRecordInfo tradingRecordInfo = new TradingRecordInfo();
                    byte[] bArr = new byte[6];
                    byte[] bArr2 = new byte[1];
                    byte[] bArr3 = new byte[4];
                    g.a(a, 5, bArr3, 0, bArr3.length);
                    g.a(a, 9, bArr2, 0, bArr2.length);
                    g.a(a, 10, bArr, 0, bArr.length);
                    tradingRecordInfo.setTradingDateTime(e.e1(a, 16));
                    tradingRecordInfo.setTradingType(Integer.valueOf(e.b(bArr2), 16));
                    tradingRecordInfo.setTradingMoney(Long.valueOf(e.b(bArr3), 16));
//                    if (this.CJ != null) {
//                    Log.d("读钱包交易记录：", String.valueOf(Long.valueOf(e.b(bArr3), 16).longValue()));
//                    Log.d("读钱包交易记录：", String.valueOf(Integer.valueOf(e.b(bArr2), 16).intValue()));
//                    Log.d("读钱包交易记录：", e.e1(a, 16));
                    card.addDeal(tradingRecordInfo);
                    //TODO 读取交易信息至ListView中
                    //this.CJ.NfcTradeInfo(tradingRecordInfo);
//                    }
                }
            } catch (IOException e) {
//                                Log.d("读钱包交易记录：", "boom");
                
            }
        }
    }

}
