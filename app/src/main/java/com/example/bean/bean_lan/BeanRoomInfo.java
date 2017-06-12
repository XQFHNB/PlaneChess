package com.example.bean.bean_lan;

import com.example.module_b_lan.network.broadcast.DataBroaCastSerlied;

/**
 * @author XQF
 * @created 2017/6/5
 */
public class BeanRoomInfo {
    private String mRoomeName;
    private String mIp;
    private DataBroaCastSerlied mRoomData;
    private String mPlayersSum;

    public String getPlayersSum() {
        return mPlayersSum;
    }

    public void setPlayersSum(String playersSum) {
        mPlayersSum = playersSum;
    }

    public String getRoomeName() {
        return mRoomeName;
    }

    public void setRoomeName(String roomeName) {
        mRoomeName = roomeName;
    }

    public String getIp() {
        return mIp;
    }

    public void setIp(String ip) {
        mIp = ip;
    }

    public DataBroaCastSerlied getRoomData() {
        return mRoomData;
    }

    public void setRoomData(DataBroaCastSerlied roomData) {

        mRoomData = roomData;
    }

    @Override
    public String toString() {
        return "房间名：" + mRoomeName + " ip:" + mIp;


    }
}
