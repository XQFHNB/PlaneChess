package com.example.test.bean;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XQF
 * @created 2017/5/24
 */
public class BeanBoard {


    public static List<BeanCell> getAllBeanCell() {

        List<BeanCell> resultBeansCells = new ArrayList<>();
        int[] x = new int[]{
                398, 372, 346,
                294, 268, 242,
                216, 190, 164, 138, 112, 86, 60,

                86, 112, 138,
                138, 112, 86,
                60, 86, 112, 138, 164, 190, 216,

                242, 268, 294,
                346, 372, 398,
                424, 450, 476, 502, 528, 554, 580,

                554, 528, 502,
                502, 528, 554,
                580, 554, 528, 502, 476, 450, 424
        };
        int[] y = new int[]{
                302, 288, 274,
                274, 288, 302,
                316, 302, 288, 274, 260, 246, 232,

                218, 204, 190,
                162, 148, 134,
                120, 106, 92, 78, 64, 50, 36,

                50, 64, 78,
                78, 64, 50,
                36, 50, 64, 78, 92, 106, 120,

                134, 148, 162,
                190, 204, 218,
                232, 246, 260, 274, 288, 302, 316
        };

        int[] redX = new int[]{
                138, 164, 190, 216, 242, 268, 294
        };
        int[] redY = new int[]{
                274, 260, 246, 232, 218, 204, 190
        };
        int[] blueX = new int[]{
                502, 476, 450, 424, 398, 372, 346
        };
        int[] blueY = new int[]{
                274, 260, 246, 232, 218, 204, 190
        };
        int[] yellowX = new int[]{
                138, 164, 190, 216, 242, 268, 294
        };
        int[] yellowY = new int[]{
                78, 92, 106, 120, 134, 148, 162
        };
        int[] greenX = new int[]{
                502, 476, 450, 424, 398, 372, 346
        };
        int[] greenY = new int[]{
                78, 92, 106, 120, 134, 148, 162
        };


        int[] baseBlueX = new int[]{
                286, 320, 354, 320,
        };
        int[] baseBlueY = new int[]{
                324, 306, 324, 306
        };

        int[] baseRedX = new int[]{
                24, 58, 92, 58
        };
        int[] baseRedY = new int[]{
                180, 162, 180, 198
        };

        int[] baseYellowX = new int[]{
                286, 320, 354, 320,
        };
        int[] baseYellowY = new int[]{
                40, 22, 40, 58
        };
        int[] baseGreenX = new int[]{
                546, 580, 614, 580,
        };
        int[] baseGreenY = new int[]{
                180, 162, 180, 198
        };
        int i;
        for (i = 0; i < x.length; i++) {
            int color = i % 4;
            BeanCell beanCell = new BeanCell(x[i], y[i], color);
            resultBeansCells.add(beanCell);
        }
        /*
        常规外围位置
         */
        for (; i < 60; i++) {
            resultBeansCells.add(null);
        }
        /*
        结束跑道位置
         */
        for (; i < 70; i++) {
            if (i < 67) {
                int temp = i - 60;
                resultBeansCells.add(new BeanCell(blueX[temp], blueY[temp], BeanCell.COLOR_BLUE));
            } else {
                resultBeansCells.add(null);
            }
        }
        for (; i < 80; i++) {
            if (i < 77) {
                int temp = i - 70;
                resultBeansCells.add(new BeanCell(redX[temp], redY[temp], BeanCell.COLOR_RED));
            } else {
                resultBeansCells.add(null);
            }
        }
        for (; i < 90; i++) {
            if (i < 87) {
                int temp = i - 80;
                resultBeansCells.add(new BeanCell(yellowX[temp], yellowY[temp], BeanCell.COLOR_YELLOW));
            } else {
                resultBeansCells.add(null);
            }
        }
        for (; i < 100; i++) {
            if (i < 97) {
                int temp = i - 90;
                resultBeansCells.add(new BeanCell(greenX[temp], greenY[temp], BeanCell.COLOR_GREEN));
            } else {
                resultBeansCells.add(null);
            }
        }

        /*
        基地位置
         */
        for (; i < 116; i++) {
            if (i < 104) {
                int index = i - 100;
                resultBeansCells.add(new BeanCell(baseBlueX[index], baseBlueY[index], BeanCell.COLOR_BLUE));
            } else if (i < 108) {
                int index = i - 104;
                resultBeansCells.add(new BeanCell(baseRedX[index], baseRedY[index], BeanCell.COLOR_RED));
            } else if (i < 112) {
                int index = i - 108;
                resultBeansCells.add(new BeanCell(baseYellowX[index], baseYellowY[index], BeanCell.COLOR_YELLOW));
            } else  {
                int index = i - 112;
                resultBeansCells.add(new BeanCell(baseGreenX[index], baseGreenY[index], BeanCell.COLOR_GREEN));
            }
        }
        return resultBeansCells;
    }


    public static List<BeanRole> getRoleList() {
        List<BeanRole> list = new ArrayList<>();
        BeanRole blue = new BeanRole(BeanCell.COLOR_BLUE);
        BeanRole red = new BeanRole(BeanCell.COLOR_RED);
        BeanRole yello = new BeanRole(BeanCell.COLOR_YELLOW);
        BeanRole green = new BeanRole(BeanCell.COLOR_GREEN);
        list.add(blue);
        list.add(red);
        list.add(yello);
        list.add(green);
        return list;
    }

}
