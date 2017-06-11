package com.example.bean;

import android.widget.Button;

import java.util.List;

/**
 * @author XQF
 * @created 2017/6/11
 */
public class BeanRobot {


    /**
     * 假装摇色子
     *
     * @param button
     */
    public void robotClickRollBtn(Button button) {

        button.performClick();
    }

    /**
     * 假装点击飞机
     *
     * @param
     */
    public void robotClickPlane(BeanRole role) {

        List<BeanPlane> planes = role.getAllPlanes();
        for (int i = 0; i < planes.size(); i++) {
            if (planes.get(i).getStatus() != BeanPlane.STATUS_IN_END) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                planes.get(i).getBtn().performClick();
                break;
            }
        }

    }

}
