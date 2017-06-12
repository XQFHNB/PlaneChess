package com.example.module_b_lan.network.model;

import java.util.LinkedList;

/**
 * @author XQF
 * @created 2017/6/3
 */
public class QueueMsg {
    LinkedList<MsgNet> mq;

    public QueueMsg() {
        mq = new LinkedList<>();
    }

    /**
     * 判断队列是否为空
     *
     * @return true：为空
     * false：不为空
     */
    public boolean isempty() {
        return mq.isEmpty();
    }

    /**
     * 从队列中获取数据，当队列中没有信息时会阻塞，直到收到新的数据
     *
     * @return 返回队列中最先要处理的数据
     */
    public MsgNet getData() throws InterruptedException {
        synchronized (mq) {
            if (mq.isEmpty()) {
                try {
                    //如果mq为空，释放mq锁，并进入阻塞
                    mq.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new InterruptedException("msg is end");
                }
            }
            return mq.pollFirst();
        }
    }

    /**
     * 每当接收到数据时，把数据加入到队列
     *
     * @param data：要加入的数据
     */
    public void addData(MsgNet data) {
        synchronized (mq) {
            mq.addLast(data);
            mq.notify();
        }
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mq.size(); i++) {
            MsgNet msgNet = mq.get(i);
            sb.append(msgNet.toString() + "\n");
        }
        return sb.toString();

    }
}