package com.example.yifeihappy.planechess;

import java.io.Serializable;

/**
 * Created by yifeihappy on 16-4-22.
 */
public class SerliBroacastData implements Serializable{
    private static final long serialVersionUID = 1L;
    private String tag;
    private String roomIP;
    private String playerIP;
    private String planeColor;
    private String playerName;
    private String playersNum;

    public String getTag() {
        return tag;
    }

    public String getRoomIP() {
        return roomIP;
    }

    public String getPlayerIP() {
        return  playerIP;
    }

    public String getPlaneColor() {
        return planeColor;
    }

    public String getPlayersNum() {
        return  playersNum;
    }

    public String getPlayerName() {
        return  playerName;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setRoomIP(String roomIP) {
        this.roomIP = roomIP;
    }

    public void setPlayerIP(String playerIP) {
        this.playerIP = playerIP;
    }

    public void setPlaneColor(String planeColor) {
        this.planeColor = planeColor;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayersNum(String playersNum) {
        this.playersNum = playersNum;
    }

    public SerliBroacastData(String tag,String roomIP,String playersNum,String playerIP,String planeColor,String playerName) {
        setTag(tag);
        setRoomIP(roomIP);
        setPlayerIP(playerIP);
        setPlaneColor(planeColor);
        setPlayerName(playerName);
        setPlayersNum(playersNum);
    }
    public SerliBroacastData(String tag,String roomIP,String playerIP,String planeColor,String playerName) {
        setTag(tag);
        setRoomIP(roomIP);
        setPlayerIP(playerIP);
        setPlaneColor(planeColor);
        setPlayerName(playerName);
        setPlayersNum("4");
    }
    public SerliBroacastData() {

    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(tag+",");
        stringBuffer.append(roomIP+",");
        stringBuffer.append(playersNum+",");
        stringBuffer.append(playerIP+",");
        stringBuffer.append(planeColor+",");
        stringBuffer.append(playerName+",");


        return  stringBuffer.toString();
    }
}
