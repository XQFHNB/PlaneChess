package com.example.huangbin.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 点对点的广播
 * Created by Huangbin on 2016/4/8.
 */
public class BroastcastP2PHelper extends BroadCastBaseHelper {
    /**
     * 接收或者发送数据的socket
     */
    protected DatagramSocket mDatagramSocket=null;

    /**
     * 接收数据缓存大小，缓存对象，以及数据包
     */
    protected final static int MAX_BUF_SIZE=1000;
    protected byte[] buf=new byte[MAX_BUF_SIZE];
    protected DatagramPacket mReceiveDatagram=new DatagramPacket(buf,buf.length);


    /**
     * 使用系统默认的地址和端口，一般用于客户端
     */
    public BroastcastP2PHelper(){
        try {
            mDatagramSocket=new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    /**
     * 一般用于服务器端
     * @param port
     */
    public BroastcastP2PHelper(int port){
        try {
            mDatagramSocket=new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送数据包
     * @param dest 目标地址
     * @param port 目标端口
     * @param msg 信息
     * @return 成功失败
     */
    public  boolean sendMsg(String dest,int port,String msg){
        InetAddress addr=null;
        try{
             addr=InetAddress.getByName(dest);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
        byte[] buf=msg.getBytes();
        DatagramPacket data=new DatagramPacket(buf,0,buf.length,addr,port);
        try {
            mDatagramSocket.send(data);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 接收信息
     * @return
     */
    public boolean receiveMsg(){

        try {
            mDatagramSocket.receive(mReceiveDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        BroadCastBaseMsg msg=new BroadCastBaseMsg();
        msg.ip=mReceiveDatagram.getAddress().getHostName();
        msg.port=mReceiveDatagram.getPort();
        msg.msg=mReceiveDatagram.getData();
        if(this.mListener!=null){
            this.mListener.onReceive(msg);
        }
        return  true;
    }

    /**
     * 销毁socket
     */
    public void destory(){
        if(mDatagramSocket!=null){
            mDatagramSocket.close();
        }
    }

}
