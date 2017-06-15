package com.example.module_b_wan.utils;

/**
 * @author XQF
 * @created 2017/6/15
 */
public class DataText {

    public static final String TAG_GAME = "game";
    public static final String TAG_FIRST = "first";
    public static final String TAG_BEGIN = "begin";

    private String mTag;
    private String mName;
    private String mColor;

    private String mGameTag;
    private int mGameDice;
    private int mGameBtnClickId;
    private int mGameStart;
    private int mGameEnd;
    private int mGameCurrent;
    private int mGameNextRole;


    public static String getDataTextContentString(DataText dataText) {
        String tag = dataText.getTag();
        StringBuffer sb = new StringBuffer();
        if (tag.equals(TAG_FIRST) || tag.equals(TAG_BEGIN)) {
            sb.append(dataText.getTag() + ",");
            sb.append(dataText.getName() + ",");
            sb.append(dataText.getColor());
        } else if (tag.equals(TAG_GAME)) {
            sb.append(dataText.getTag() + ",");
            sb.append(dataText.getGameTag() + ",");
            sb.append(dataText.getGameDice() + ",");
            sb.append(dataText.getGameBtnClickId() + ",");
            sb.append(dataText.getGameStart() + ",");
            sb.append(dataText.getGameEnd() + ",");
            sb.append(dataText.getGameCurrent() + ",");
            sb.append(dataText.getGameNextRole());
        }
        return sb.toString();
    }


    public static DataText getDataTextFromString(String string) {
        String[] strs = string.split(",");
        DataText text = new DataText();
        text.setTag(strs[0]);
        if (strs[0].equals(TAG_FIRST) || strs[0].equals(TAG_BEGIN)) {
            text.setName(strs[1]);
            text.setColor(strs[2]);
        } else if (strs[0].equals(TAG_GAME)) {
            text.setGameTag(strs[1]);
            text.setGameDice(parseIntString(strs[2]));
            text.setGameBtnClickId(parseIntString(strs[2]));
            text.setGameStart(parseIntString(strs[3]));
            text.setGameEnd(parseIntString(strs[4]));
            text.setGameCurrent(parseIntString(strs[5]));
            text.setGameNextRole(parseIntString(strs[6]));
        }
        return text;
    }

    public static int parseIntString(String string) {
        return Integer.parseInt(string);
    }


    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getGameTag() {
        return mGameTag;
    }

    public void setGameTag(String gameTag) {
        mGameTag = gameTag;
    }

    public int getGameDice() {
        return mGameDice;
    }

    public void setGameDice(int gameDice) {
        mGameDice = gameDice;
    }

    public int getGameBtnClickId() {
        return mGameBtnClickId;
    }

    public void setGameBtnClickId(int gameBtnClickId) {
        mGameBtnClickId = gameBtnClickId;
    }

    public int getGameStart() {
        return mGameStart;
    }

    public void setGameStart(int gameStart) {
        mGameStart = gameStart;
    }

    public int getGameEnd() {
        return mGameEnd;
    }

    public void setGameEnd(int gameEnd) {
        mGameEnd = gameEnd;
    }

    public int getGameCurrent() {
        return mGameCurrent;
    }

    public void setGameCurrent(int gameCurrent) {
        mGameCurrent = gameCurrent;
    }

    public int getGameNextRole() {
        return mGameNextRole;
    }

    public void setGameNextRole(int gameNextRole) {
        mGameNextRole = gameNextRole;
    }
}
