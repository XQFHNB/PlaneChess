package com.example.module_b_lan.utils;

import android.util.Log;

import com.example.module_b_lan.network.broadcast.DataBroaCastSerlied;

import java.util.StringTokenizer;

/**
 * Created by yifeihappy on 16-4-22.
 */
public class UtilDeserializable {
    public static final String TAG = "test";

    private StringTokenizer stringTokenizer = null;
    DataBroaCastSerlied serliBroacastData = null;

    public DataBroaCastSerlied deSerliBroacastData(byte[] msg) {
        stringTokenizer = new StringTokenizer(new String(msg), ",");
        String tag = stringTokenizer.nextToken();
        String roomIP = stringTokenizer.nextToken();
        String playersNum = stringTokenizer.nextToken();
        String playerIP = stringTokenizer.nextToken();
        String planeColor = stringTokenizer.nextToken();
        String playerName = stringTokenizer.nextToken();

        serliBroacastData = new DataBroaCastSerlied(tag, roomIP, playersNum, playerIP, planeColor, playerName);
        return serliBroacastData;
    }

    public DataBroaCastSerlied deSerliBroacastData(String msg) {
        stringTokenizer = new StringTokenizer(msg, ",");
        String tag = stringTokenizer.nextToken();
        String roomIP = stringTokenizer.nextToken();
        String playersNum = stringTokenizer.nextToken();
        String playerIP = stringTokenizer.nextToken();
        String planeColor = stringTokenizer.nextToken();
        String playerName = stringTokenizer.nextToken();

        serliBroacastData = new DataBroaCastSerlied(tag, roomIP, playersNum, playerIP, planeColor, playerName);
        return serliBroacastData;
    }

    public static DataBroaCastSerlied getFromNetMsgData(String data) {
        Log.d(TAG, "有没有调用这个方格");
        String[] strs = data.split(",");
        String tag = strs[0];
        String roomIp = strs[1];
        int dice = Integer.parseInt(strs[2]);
        int planeId = Integer.parseInt(strs[3]);
        int start = Integer.parseInt(strs[4]);
        int end = Integer.parseInt(strs[5]);

        int currentRole = Integer.parseInt(strs[6]);
        int nextRole = Integer.parseInt(strs[7]);
        Log.d(TAG, "getFromNetMsgData: " + tag + " " + roomIp + " " + dice + " " + planeId + " " + start + " " + end + " " + currentRole + " " + nextRole);
        return new DataBroaCastSerlied(tag, roomIp, dice, planeId, start, end, currentRole, nextRole);
    }

}
