package com.example.test.bean;

import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XQF
 * @created 2017/5/26
 */
public class BeanRole {

    public static final int START_BLUE_NORMAL = 0;
    public static final int START_RED_NORMAL = 13;
    public static final int START_YELLOW_NORMAL = 26;
    public static final int START_GREEN_NORMAL = 39;

    public static final int END_BLUE_NORMAL = 48;
    public static final int END_RED_NOMAL = 9;
    public static final int END_YELLOW_NORMAL = 22;
    public static final int END_GREEN_NORMAL = 35;

    public static final int START_BLUE_END = 60;
    public static final int START_RED_END = 70;
    public static final int START_YELLOW_END = 80;
    public static final int START_GREEN_END = 90;

    public static final int END_BLUE_END = 66;
    public static final int END_RED_END = 76;
    public static final int END_YELLOW_END = 86;
    public static final int END_GREEN_END = 96;

    public static final int JUMP_SMALL = 4;
    public static final int JUMP_BIG = 12;

    public static final int JUMP_BLUE_START = 16;
    public static final int JUMP_RED_START = 29;
    public static final int JUMP_YELLOW_START = 42;
    public static final int JUMP_GREEN_START = 3;


    private int color;
    private int currentIndex;
    private int currentIndexInEnd;
    private float mXScale = 0;
    private float mYScale = 0;
    private int mNum;
    private boolean isFinish = false;
    private boolean isNormalEnd = false;
    private List<BeanCell> mBeanCells;

    int x = 3;

    public float getXScale() {
        return mXScale;
    }

    public void setXScale(float XScale) {
        mXScale = XScale;
    }

    public boolean isNormalEnd() {
        return isNormalEnd;
    }

    public void setNormalEnd(boolean normalEnd) {
        isNormalEnd = normalEnd;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public float getYScale() {
        return mYScale;
    }

    public void setYScale(float YScale) {
        mYScale = YScale;
    }

    public int getCurrentIndexInEnd() {

        return currentIndexInEnd;
    }

    public void setCurrentIndexInEnd(int currentIndexInEnd) {
        this.currentIndexInEnd = currentIndexInEnd;
    }


    public int getNum() {
        return mNum;
    }

    public void setNum(int num) {
        mNum = num;
    }

    private Button btn;

    public BeanRole(int color, int currentIndex) {
        this.color = color;
        this.currentIndex = currentIndex;
        mBeanCells = BeanBoard.getAllBeanCell();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public Button getBtn() {
        return btn;
    }

    public static List<BeanRole> getRoleList() {

        List<BeanRole> list = new ArrayList<>();
        BeanRole blue = new BeanRole(BeanCell.COLOR_BLUE, START_BLUE_NORMAL);
        BeanRole red = new BeanRole(BeanCell.COLOR_RED, START_RED_NORMAL);
        BeanRole yello = new BeanRole(BeanCell.COLOR_YELLOW, START_YELLOW_NORMAL);
        BeanRole green = new BeanRole(BeanCell.COLOR_GREEN, START_GREEN_NORMAL);
        list.add(blue);
        list.add(red);
        list.add(yello);
        list.add(green);
        return list;
    }

    public void clickBtn() {
        btn.performClick();
    }

    public boolean isFinish(int color) {
        boolean result = false;


        return result;
    }

    /**
     * 正常的外围跑道
     */
    public void normalMove() {
        if (color == BeanCell.COLOR_BLUE) {
            blueNormalMove();
        } else if (color == BeanCell.COLOR_RED) {
            redNormalMove();
        } else if (color == BeanCell.COLOR_YELLOW) {
            yellowNormalMove();
        } else {
            greenNormalMove();
        }

    }

    /**
     * 结束跑道
     */

    public void endMove() {
        if (color == BeanCell.COLOR_BLUE) {
            blueEndMove();
        } else if (color == BeanCell.COLOR_RED) {
            redEndMove();
        } else if (color == BeanCell.COLOR_YELLOW) {
            yellowEndMove();
        } else {
            greenEndMove();
        }
    }


    /**
     * 蓝色正常移动
     */
    private void blueNormalMove() {
        int temp = currentIndex + mNum;
        if (temp >= END_BLUE_NORMAL) {
            isNormalEnd = true;
            temp = temp - END_BLUE_NORMAL + START_BLUE_END;
        } else {
            BeanCell cell = mBeanCells.get(temp);
            if (cell.getColor() == color) {
                if (temp == JUMP_BLUE_START) {
                    temp += JUMP_BIG;
                } else {
                    temp += JUMP_SMALL;
                }
                if (temp >= END_BLUE_NORMAL) {
                    isNormalEnd = true;
                    temp = temp - END_BLUE_NORMAL + START_BLUE_END;
                }
            }
        }
        move(temp);
        currentIndex = temp;
    }

    /**
     * 蓝色最后移动
     */
    private void blueEndMove() {
        int temp = currentIndexInEnd + mNum;
        if (temp == END_BLUE_END) {
            isFinish = true;
            return;
        } else if (temp < END_BLUE_END) {
            currentIndexInEnd = temp;
        } else if (temp > END_BLUE_END) {
            currentIndexInEnd = END_BLUE_END - (temp % END_BLUE_END);
        }
        move(temp);
        currentIndexInEnd = temp;
    }

    /**
     * 红色正常移动
     */
    private void redNormalMove() {
        int temp = currentIndex + mNum;
        if (temp >= END_RED_NOMAL && temp <= START_RED_NORMAL + 1 && currentIndex <= START_RED_NORMAL - 1) {
            isNormalEnd = true;
            temp = temp - END_RED_NOMAL + START_RED_END;
            currentIndexInEnd = temp;
            move(temp);

        } else {
            temp = temp % 52;
            BeanCell cell = mBeanCells.get(temp);
            if (cell.getColor() == color) {
                if (temp == JUMP_RED_START) {
                    temp += JUMP_BIG;
                } else {
                    temp += JUMP_SMALL;
                }
                if (temp >= END_RED_NOMAL && temp <= START_RED_NORMAL + 1 && currentIndex <= START_RED_NORMAL - 1) {
                    isNormalEnd = true;
                    temp = temp - END_RED_NOMAL + START_RED_END;
                    currentIndexInEnd = temp;
                    move(temp);
                } else {
                    temp = temp % 52;
                    move(temp);
                    currentIndex = temp;
                }
            } else {
                move(temp);
                currentIndex = temp;
            }

        }

    }

    /**
     * 红色结束时的移动
     */


    private void redEndMove() {
        int temp = currentIndexInEnd + mNum;
        if (temp == END_RED_END) {
            isFinish = true;
            return;
        } else if (temp < END_RED_END) {
            currentIndexInEnd = temp;
        } else if (temp > END_RED_END) {
            currentIndexInEnd = END_RED_END - (temp % END_RED_END);
        }
        move(temp);
        currentIndexInEnd = temp;
    }


    private void greenNormalMove() {
        int temp = currentIndex + mNum;
        if (temp >= END_GREEN_NORMAL && temp <= START_GREEN_NORMAL + 1 && currentIndex <= START_GREEN_NORMAL - 1) {
            isNormalEnd = true;
            temp = temp - END_GREEN_NORMAL + START_GREEN_END;
            currentIndexInEnd = temp;
            move(temp);
        } else {
            temp = temp % 52;
            BeanCell cell = mBeanCells.get(temp);
            if (cell.getColor() == color) {
                if (temp == JUMP_GREEN_START) {
                    temp += JUMP_BIG;
                } else {
                    temp += JUMP_SMALL;
                }
                if (temp >= END_GREEN_NORMAL && temp <= START_GREEN_NORMAL + 1 && currentIndex <= START_GREEN_NORMAL - 1) {
                    isFinish = true;
                    temp = temp - END_GREEN_NORMAL + START_GREEN_END;
                    currentIndexInEnd = temp;
                    move(temp);
                } else {
                    temp = temp % 52;
                    move(temp);
                    currentIndex = temp;
                }
            } else {
                move(temp);
                currentIndex = temp;
            }
        }

    }


    private void greenEndMove() {
        int temp = currentIndexInEnd + mNum;
        if (temp == END_GREEN_END) {
            isFinish = true;
            return;
        } else if (temp < END_GREEN_END) {
            currentIndexInEnd = temp;
        } else if (temp > END_BLUE_END) {
            currentIndexInEnd = END_GREEN_END - (temp % END_GREEN_END);
        }
        move(temp);
        currentIndexInEnd = temp;
    }

    private void yellowNormalMove() {
        int temp = currentIndex + mNum;
        if (temp >= END_YELLOW_NORMAL && temp <= START_YELLOW_NORMAL + 1 && currentIndex <= START_YELLOW_NORMAL - 1) {
            isNormalEnd = true;
            temp = temp - END_YELLOW_NORMAL + START_YELLOW_END;
            currentIndexInEnd = temp;
            move(temp);
        } else {
            temp = temp % 52;
            BeanCell cell = mBeanCells.get(temp);
            if (cell.getColor() == color) {
                if (temp == JUMP_YELLOW_START) {
                    temp += JUMP_BIG;
                } else {
                    temp += JUMP_SMALL;
                }
                if (temp >= END_YELLOW_NORMAL && temp <= START_YELLOW_NORMAL + 1 && currentIndex <= START_YELLOW_NORMAL - 1) {
                    isNormalEnd = true;
                    temp = temp - END_YELLOW_NORMAL + START_YELLOW_END;
                    currentIndexInEnd = temp;
                    move(temp);
                } else {
                    temp = temp % 52;
                    move(temp);
                    currentIndex = temp;
                }
            } else {
                move(temp);
                currentIndex = temp;
            }
        }
    }

    private void yellowEndMove() {
        int temp = currentIndexInEnd + mNum;
        if (temp == END_YELLOW_END) {
            isFinish = true;
            return;
        } else if (temp < END_YELLOW_END) {
            currentIndexInEnd = temp;
        } else if (temp > END_YELLOW_NORMAL) {
            currentIndexInEnd = END_YELLOW_END - (temp % END_YELLOW_NORMAL);
        }
        move(temp);
        currentIndexInEnd = temp;
    }


    private void move(int temp) {

        Log.d("test", "move方法中的temp:" + temp);
        BeanCell cell = mBeanCells.get(temp);
        float destX = (cell.getX() - x) * mXScale;
        float destY = (cell.getY() - 2 * x) * mYScale;
        btn.setX(destX);
        btn.setY(destY);
    }
}
