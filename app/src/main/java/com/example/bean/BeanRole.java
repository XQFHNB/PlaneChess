package com.example.bean;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XQF
 * @created 2017/6/4
 */
public class BeanRole {

    private List<BeanPlane> mBeanPlanes;

    private int mColor;
    private boolean isFinish = true;

    private boolean isAllPlanesInBase = true;

    private Button mBtnDice;

    public Button getBtnDice() {
        return mBtnDice;
    }

    public void setBtnDice(Button btnDice) {
        mBtnDice = btnDice;
    }

    public BeanRole(int color) {
        mColor = color;
        mBeanPlanes = new ArrayList<>();
    }


    public List<BeanPlane> getAllPlanes() {
        return mBeanPlanes;
    }

    public void setAllPlanes(List<BeanPlane> list) {
        mBeanPlanes = list;
    }


    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    /**
     * 获取在路上的飞机
     *
     * @return
     */
    public List<BeanPlane> getBeanPlanesOnTheRoad() {
        List<BeanPlane> result = new ArrayList<>();
        for (int i = 0; i < mBeanPlanes.size(); i++) {
            BeanPlane plane = mBeanPlanes.get(i);
            if (plane.getStatus() == BeanPlane.STATUS_ON_ROAD) {
                result.add(plane);
            }
        }
        return result;
    }

    /**
     * 获取在点数为target下的所有可点击飞机，（其实就是在终点的不能点，其他都可以）
     *
     * @return
     */
    public List<BeanPlane> getBeanPlanesInBase() {
        List<BeanPlane> result = new ArrayList<>();
        for (int i = 0; i < mBeanPlanes.size(); i++) {
            BeanPlane plane = mBeanPlanes.get(i);
            if (plane.getStatus() == BeanPlane.STATUS_IN_BASE) {
                result.add(plane);
            }
        }
        return result;
    }

    /**
     * 判断是不是该角色的所有飞机均在基地
     *
     * @return
     */
    public boolean isAllPlanesInBase() {
        for (int i = 0; i < mBeanPlanes.size(); i++) {
            BeanPlane plane = mBeanPlanes.get(i);
            if (plane.getStatus() != BeanPlane.STATUS_IN_BASE) {
                isAllPlanesInBase = false;
                break;
            }
        }
        return isAllPlanesInBase;
    }


    /**
     * 判断是不是所有飞机都在终点
     *
     * @return
     */
    public boolean isAllPlanesInEnd() {
        for (int i = 0; i < mBeanPlanes.size(); i++) {
            BeanPlane plane = mBeanPlanes.get(i);
            if (plane.getStatus() != BeanPlane.STATUS_IN_END) {
                isFinish = false;
                break;
            }
        }
        return isFinish;
    }


    /**
     * 只能点击路上的飞机
     */
    public void movePlaneRoad() {
        List<BeanPlane> beanPlanesOnRoad = getBeanPlanesOnTheRoad();
        for (int i = 0; i < beanPlanesOnRoad.size(); i++) {
            Button btn = beanPlanesOnRoad.get(i).getBtn();
            btn.setClickable(true);
        }
    }

    /**
     * 点数为target的时候能点击路上的和基地的
     */
    public void movePlaneRoadAndBase() {
        List<BeanPlane> beanPlanesOnRoad = getBeanPlanesOnTheRoad();
        List<BeanPlane> beanPlanesInBase = getBeanPlanesInBase();
        beanPlanesInBase.addAll(beanPlanesOnRoad);
        for (int i = 0; i <  beanPlanesInBase.size(); i++) {
            Button btn =  beanPlanesInBase.get(i).getBtn();
            btn.setClickable(true);
        }
    }
}
