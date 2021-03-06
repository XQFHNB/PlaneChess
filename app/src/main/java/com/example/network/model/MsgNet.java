package com.example.network.model;

/**
 * 保存网络信息的类
 * data是数据信息
 * from表示从哪个玩家发来的信息
 */
public class MsgNet {
    String data;
    byte from;

    public MsgNet(String s, byte c) {
        this.data = s;
        this.from = c;
    }

    public String getData() {
        return data;
    }

    public byte getFrom() {

        return from;
    }

    @Override
    public String toString() {
        return "data: " + data + " from:" + from;
    }
}
