package com.mogician.cardreader;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Pattern;


//TODO 代码优化，预计大部分都可以去掉
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NfcAdapter nfcAdapter;
    private PendingIntent pi;
    private IntentFilter tagDetected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);


        if (this.nfcAdapter != null) {
            pi = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass())
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            processIntent(this.getIntent());
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_nfc) {

        } else if (id == R.id.nav_share) {
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, "Newcapec Card Reader : https://github.com/liuyanyi/NewcapecCardReader");
            startActivity(Intent.createChooser(textIntent, "分享Newcapec Card Reader"));
        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 当前app正在前端界面运行，这个时候有intent发送过来，那么系统就会调用onNewIntent回调方法，将intent传送过来
        // 我们只需要在这里检验这个intent是否是NFC相关的intent，如果是，就调用处理方法
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            processIntent(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pi, null, null);
        }
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private void processIntent(Intent intent) {
        //取出封装在intent中的TAG

        cardInfo card = readCard(intent);
        if (card != null) {
            card.onFinish();
            card.show();
            //card.showInLog();
        }
    }

    private cardInfo readCard(Intent intent) {

        cardInfo card = new cardInfo(this);

        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tagFromIntent == null)
            return null;
        card.setHardwareId(byteToHex(tagFromIntent.getId()));


        if (intent == null) {
            ErrorToast(7);
            return null;
        }
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
                    aToast("读卡失败！");
                } else {
                    byte[] a;


                    transceive = isoDep.transceive(g.a(fInts.j));
                    //名字
                    if (transceive != null && g.c(transceive)) {
                        a = g.a(transceive);
                        g.a(a, 0, transceive, 0, transceive.length - 2);
                        action = g.a(a, 0, a.length, "GB18030").trim();
//                        Log.d("名字", action);
                        if (StringUtils.g(action)) {
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
                        Log.d("***", action);
                        if (StringUtils.d(action) || a(action) || action.contains("000000")) {
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
                            intValue = Integer.valueOf(g.a(transceive, transceive.length), 16).intValue();
                            action = (intValue / 100) + "." + (intValue % 100);

//                                    bVar.e(action);
//                            Log.d("余额", action);
                            card.setCardBalance(action);
                        }
                        a(isoDep);
                    }

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
//        a = g.a(transceive);
//        Log.d("测试0,"+j, String.valueOf(a));
//        Log.d("测试0.5,"+j, g.a(transceive, transceive.length));
//        g.a(a, 0, transceive, 0, transceive.length - 2);
//        Log.d("测试1,"+j, String.valueOf(a));
//        action = g.a(a, 0, a.length, "UTF-8").trim();
////                        Log.d("***", action);
//        Log.d("测试2,"+j, action);
//        Log.d("eiiiiii", "======================================");
//        if (StringUtils.d(action) || a(action) || action.contains("000000")) {
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


                    //未知3，同交易记录
                    transceive = isoDep.transceive(g.a(fInts.f));
                    if (transceive != null && g.c(transceive)) {
                        transceive = isoDep.transceive(g.a(fInts.n));
                        if (transceive != null && g.c(transceive)) {
                            transceive = g.a(transceive);
                            intValue = Integer.valueOf(g.a(transceive, transceive.length), 16).intValue();
                            action = (intValue / 100) + "." + (intValue % 100);
//                                    bVar.f(action);
                            Log.d("未知3***", action);

                        }
                        a(isoDep);
                    }
//                    Log.d("***", String.valueOf(Float.parseFloat(this.w) + Float.parseFloat(this.x)) + "元");


                }
            }
            if (isoDep != null) {
                try {
                    isoDep.close();
                } catch (IOException e) {
                    Log.d("ERR", "close fail");
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
            String str = "****";
            Log.d("ERR", "ERROR");
            action = (e2 == null || e2.getLocalizedMessage() == null) ? "" : e2.getLocalizedMessage();
            Log.e(str, action);
            if (isoDep != null) {
                try {
                    isoDep.close();
                } catch (IOException e22) {
                    Log.d("ERR", "close fail");
                    e22.printStackTrace();
                }
            }

        }
        return card;
    }

    private void aToast(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }


    //TODO 去掉这堆东西……
    private b CJ = new cj(this);

    protected TextView m;
    protected TextView n;
    protected TextView o;
    protected TextView q;
    protected View r;
    protected View s;
    protected TextView t;

    protected cr v;
    protected String w = "0";
    protected String x = "0";
    protected String y;


    private String byteToHex(byte[] bArr) {
        int i = 0;
        String[] strArr = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String str = "";
        while (i < bArr.length) {
            int i2 = bArr[i] & 255;
            str = (str + strArr[(i2 >> 4) & 15]) + strArr[i2 & 15];
            i++;
        }
        return str;
    }

    //错误提示
    private void ErrorToast(int i) {
        switch (i) {
            case 1:
                aToast("Authentication Failed ");
                break;
            case 2:
                aToast("Failed reading ");
                break;
            case 3:
                aToast("Failed reading 0");
                break;
            case 4:
                aToast("Tag reading error");
                break;
            case 5:
                aToast("不是Mifare卡");
                break;
            case 6:
                aToast("不是CPU卡");
                break;
            case 7:
                aToast("未找到卡");
                break;
            case 8:
                aToast("tag类型不对");
                break;
        }
    }

    public static boolean a(char c) {
        Character.UnicodeBlock of = Character.UnicodeBlock.of(c);
        return of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || of == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || of == Character.UnicodeBlock.GENERAL_PUNCTUATION || of == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || of == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean a(String str) {
        char[] toCharArray = Pattern.compile("\\s*|\t*|\r*|\n*").matcher(str).replaceAll("").replaceAll("\\p{P}", "").trim().toCharArray();
        float length = (float) toCharArray.length;
        float f = 0.0f;
        for (char c : toCharArray) {
            if (!(Character.isLetterOrDigit(c) || a(c))) {
                f += 1.0f;
            }
        }
        return ((double) (f / length)) > 0.4d;
    }

    void a(IsoDep isoDep) {
        //TODO 修复这个类，以及更名
        for (int i = 0; i < 10; i++) {
            byte[] a = e.a(fInts.t);
            a[2] = (byte) (i + 1);
            try {
                a = isoDep.transceive(a);
//                DebugUtil.a("读钱包交易记录：", e.b(readCard));
                Log.d("读钱包交易记录：", e.b(a));
                if (a != null && g.c(a) && StringUtils.g(e.b(g.a(a)).replaceAll("0", ""))) {
                    TradingRecordInfo tradingRecordInfo = new TradingRecordInfo();
                    byte[] bArr = new byte[6];
                    byte[] bArr2 = new byte[1];
                    byte[] bArr3 = new byte[4];
                    e.a(a, 5, bArr3, 0, bArr3.length);
                    e.a(a, 9, bArr2, 0, bArr2.length);
                    e.a(a, 10, bArr, 0, bArr.length);
                    tradingRecordInfo.setTradingDateTime(e.e(a, 16));
                    tradingRecordInfo.setTradingType(Integer.valueOf(e.b(bArr2), 16).intValue());
                    tradingRecordInfo.setTradingMoney(Long.valueOf(e.b(bArr3), 16).longValue());
                    if (this.CJ != null) {

                        //TODO 读取交易信息至ListView中
                        //this.CJ.NfcTradeInfo(tradingRecordInfo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

}
