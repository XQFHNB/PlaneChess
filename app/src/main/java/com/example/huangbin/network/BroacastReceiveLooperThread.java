package com.example.huangbin.network;

/**
 * Created by yifeihappy on 16-4-22.
 */
public class BroacastReceiveLooperThread extends Thread {
    private Object mLock = new Object();
    private  BroascastGroupHelper broascastGroupHelper;
    boolean stopListen;

    public BroacastReceiveLooperThread(BroascastGroupHelper broascastGroupHelper) {
        this.broascastGroupHelper = broascastGroupHelper;
        stopListen = false;
    }
    public void stopThread() {
        synchronized (mLock) {
            stopListen = true;
        }
    }
    @Override
    public void run() {
        while(true)
        {
            synchronized (mLock)
            {
                if(stopListen) break;
            }
            broascastGroupHelper.receiveMsg();
        }
    }
}
