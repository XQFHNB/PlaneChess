package com.example.junyi.network;

/**
 * 保存网络信息的类
 * data是数据信息
 * from表示从哪个玩家发来的信息
 */
public class NetMsg {
    String data;
    byte from;
    public NetMsg(String s, byte c) {
        this.data = s;
        this.from = c;
    }

    public String getData() {
        return  data;
    }
    public byte getFrom() {
        return from;
    }
}
