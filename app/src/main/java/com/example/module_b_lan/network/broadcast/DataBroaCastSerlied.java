package com.example.module_b_lan.network.broadcast;

import java.io.Serializable;

/**
 * Created by yifeihappy on 16-4-22.
 */
public class DataBroaCastSerlied implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tag;
    private String roomIP;
    private String playerIP;
    private String planeColor;
    private String playerName;
    private String playersNum;
    private int mIdPlane;
    private int mStartIndex;
    private int mEndIndex;
    private int mDice;
    private int mNextRole;

    public int getCurrentRole() {
        return mCurrentRole;
    }

    public void setCurrentRole(int currentRole) {
        mCurrentRole = currentRole;
    }

    private int mCurrentRole;

    public int getNextRole() {
        return mNextRole;
    }

    public void setNextRole(int nextRole) {
        mNextRole = nextRole;
    }

    public int getDice() {
        return mDice;
    }

    public void setDice(int dice) {
        mDice = dice;
    }

    public int getEndIndex() {
        return mEndIndex;
    }

    public void setEndIndex(int endIndex) {
        mEndIndex = endIndex;
    }

    public int getStartIndex() {

        return mStartIndex;
    }

    public void setStartIndex(int startIndex) {
        mStartIndex = startIndex;
    }

    public int getIdPlane() {
        return mIdPlane;
    }

    public void setIdPlane(int idPlane) {
        mIdPlane = idPlane;
    }

    public String getTag() {
        return tag;
    }

    public String getRoomIP() {
        return roomIP;
    }

    public String getPlayerIP() {
        return playerIP;
    }

    public String getPlaneColor() {
        return planeColor;
    }

    public String getPlayersNum() {
        return playersNum;
    }

    public String getPlayerName() {
        return playerName;
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

    public DataBroaCastSerlied(String tag, String roomIP, String playersNum, String playerIP, String planeColor, String playerName) {
        setTag(tag);
        setRoomIP(roomIP);
        setPlayerIP(playerIP);
        setPlaneColor(planeColor);
        setPlayerName(playerName);
        setPlayersNum(playersNum);
    }


    public DataBroaCastSerlied(String tag, String roomIP, String playerIP, String planeColor, String playerName) {
        setTag(tag);
        setRoomIP(roomIP);
        setPlayerIP(playerIP);
        setPlaneColor(planeColor);
        setPlayerName(playerName);
        setPlayersNum("4");
    }


    public DataBroaCastSerlied(String tag, int indexClient, int indexPlaneOfClient, int start, int end) {

    }

    /**
     * 游戏时的数据传播
     *
     * @param tag     消息类型标签
     * @param roomIp  房间地址
     * @param dice    色子数
     * @param planeId 移动飞机的resID
     * @param start   飞机移动的起始位置
     * @param end     飞机移动的结束位置
     */
    public DataBroaCastSerlied(String tag, String roomIp, int dice, int planeId, int start, int end, int currentRole, int nextRole) {
        setTag(tag);
        setCurrentRole(currentRole);
        setNextRole(nextRole);
        setDice(dice);
        setRoomIP(roomIp);
        setIdPlane(planeId);
        setStartIndex(start);
        setEndIndex(end);
    }


    public String getGameData() {
        StringBuffer sb = new StringBuffer();
        sb.append(
                tag + "," + roomIP + "," + mDice + ","
                        + mIdPlane + "," + mStartIndex + "," + mEndIndex + "," + mCurrentRole + "," + mNextRole);
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(tag + ",");
        stringBuffer.append(roomIP + ",");
        stringBuffer.append(playersNum + ",");
        stringBuffer.append(playerIP + ",");
        stringBuffer.append(planeColor + ",");
        stringBuffer.append(playerName + ",");


        return stringBuffer.toString();
    }
}
