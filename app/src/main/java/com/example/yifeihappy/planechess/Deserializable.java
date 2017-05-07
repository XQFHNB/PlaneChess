package com.example.yifeihappy.planechess;

import android.util.Log;

import java.util.StringTokenizer;

/**
 * Created by yifeihappy on 16-4-22.
 */
public class Deserializable {
    private StringTokenizer stringTokenizer = null;
    SerliBroacastData serliBroacastData = null;

    SerliBroacastData deSerliBroacastData(byte [] msg) {
        stringTokenizer = new StringTokenizer(new String(msg),",");
        String tag = stringTokenizer.nextToken();
        String roomIP = stringTokenizer.nextToken();
        String playersNum = stringTokenizer.nextToken();
        String playerIP = stringTokenizer.nextToken();
        String planeColor = stringTokenizer.nextToken();
        String playerName = stringTokenizer.nextToken();

        serliBroacastData = new SerliBroacastData(tag,roomIP,playersNum,playerIP,planeColor,playerName);
        return  serliBroacastData;
    }
    SerliBroacastData deSerliBroacastData(String msg) {
        stringTokenizer = new StringTokenizer(msg,",");
        String tag = stringTokenizer.nextToken();
        String roomIP = stringTokenizer.nextToken();
        String playersNum = stringTokenizer.nextToken();
        String playerIP = stringTokenizer.nextToken();
        String planeColor = stringTokenizer.nextToken();
        String playerName = stringTokenizer.nextToken();

        serliBroacastData = new SerliBroacastData(tag,roomIP,playersNum,playerIP,planeColor,playerName);
        return  serliBroacastData;
    }

}
