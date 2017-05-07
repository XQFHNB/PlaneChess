package com.example.PPP;

/**
 * Created by trhyt on 2016/4/22.
 */
public class UpdateThread extends Thread{
    Drawview view;

    public UpdateThread(Drawview view)
    {
        this.view = view;
    }

    /*
    boolean flyend()   //判断是否已经走完这一步
    {
        if(view.starts != view.ends)return true;
        else return false;
    }
    */

    public void run()
    {
        while(!view.flyend())
        {
            view.postInvalidate();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
