package com.example.huangbin.network;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Huangbin on 2016/4/8.
 * 实现1对多的广播
 */
public class BroascastGroupHelper extends BroadCastBaseHelper {

    /**
     * 组播的ip，端口，地址对象
     */
    private static final  String GOUP_ADDR="224.0.0.1";
    private int mPort;
    private static InetAddress mGroupInetAddress;

    /**
     * 接收的数据包以及缓存
     */
    protected final static int MAX_BUF_SIZE=1000;
    protected byte[] buf=new byte[MAX_BUF_SIZE];
    private DatagramPacket mRecvData;


    /**
     * 发送或者接收数据的socket对象
     */
    private MulticastSocket mMulticastSocket;


    /**
     * 主机加入组播地址以及离开的监听对象
     */
    private OnAddressJoinGroupLisener mJoinGroupLisener = null;
    private OnAddressLeaveGroupLisener mLeaveGroupLisener = null;


    /**
     * @param port
     * @param timeToLive 数据包可传递的路由跳闸数，
     *                   0是本机，1是局域网，255是全部
     */
    public  BroascastGroupHelper(int port,int timeToLive){
        mPort=port;
        try {
            mGroupInetAddress=InetAddress.getByName(GOUP_ADDR);
            mMulticastSocket=new MulticastSocket(port);
            mMulticastSocket.setTimeToLive(timeToLive);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            if(mErrorListner!=null) mErrorListner.handleError(e);
        } catch (IOException e) {
            e.printStackTrace();
            if(mErrorListner!=null) mErrorListner.handleError(e);
        }
        mRecvData=new DatagramPacket(buf,buf.length);
    }
    public  BroascastGroupHelper(int port){
        this(port,1);
    }
    public BroascastGroupHelper(){
        this(30000,1);
    }

    /**
     * 主机加入组播
     * @return
     */
    public boolean joinGroup(){
        try {
            if(mMulticastSocket==null) Log.e("doit","mMulticastSocket null");
            mMulticastSocket.joinGroup(mGroupInetAddress);
        } catch (IOException e) {
            e.printStackTrace();
            if(mErrorListner!=null) mErrorListner.handleError(e);
            return  false;
        }
        if(mJoinGroupLisener!=null){
            mJoinGroupLisener.onJoin(mGroupInetAddress);
        }
        return true;
    }

    public void setLoopback(boolean enble){
        try {
            this.mMulticastSocket.setLoopbackMode(enble);
        } catch (SocketException e) {
            e.printStackTrace();
            if(mErrorListner!=null) mErrorListner.handleError(e);
        }
    }
    /**
     * 主机离开组播
     * @return
     */
    public boolean leaveGroup(){
        try {
            mMulticastSocket.leaveGroup(mGroupInetAddress);
        } catch (IOException e) {
            e.printStackTrace();
            if(mErrorListner!=null) mErrorListner.handleError(e);
            return  false;
        }
        if(mLeaveGroupLisener!=null){
            mLeaveGroupLisener.onLeave(mGroupInetAddress);
        }
        return true;
    }


    /**
     * 组播信息
     * @param msg 发送的信息
     * @return 成功与否
     */
    public boolean sendMsg(String msg){
        long start=System.currentTimeMillis();
        byte[] buf=msg.getBytes();
        DatagramPacket data=new DatagramPacket(buf,0,buf.length,mGroupInetAddress,mPort);
        try {
            mMulticastSocket.send(data);
        } catch (IOException e) {
            e.printStackTrace();
            if(mErrorListner!=null) mErrorListner.handleError(e);
            return false;
        }
        return true;
    }

    /**
     * 接收数据
     * @return 成功与否
     */
    public boolean receiveMsg(){

        try {
            mMulticastSocket.receive(mRecvData);
        } catch (IOException e) {
            e.printStackTrace();
            if(mErrorListner!=null) mErrorListner.handleError(e);
            return false;
        }
        BroadCastBaseMsg msg=new BroadCastBaseMsg();
        msg.ip=mRecvData.getAddress().getHostName();
        msg.port=mRecvData.getPort();
        msg.msg=mRecvData.getData();
        if(this.mListener==null) Log.e("doit","mListener null");
        if(this.mListener!=null){
            this.mListener.onReceive(msg);
        }
        return  true;
    }

    /**
     * 销毁socket
     */
    @Override
    public void destory() {
        if(mMulticastSocket!=null){
            mMulticastSocket.close();
        }
    }


    public void setJoinGroupLisener(OnAddressJoinGroupLisener lisener) {
        this.mJoinGroupLisener = lisener;
    }

    public void setLeaveGroupLisener(OnAddressLeaveGroupLisener lisener) {
        this.mLeaveGroupLisener = lisener;
    }

    public interface OnAddressJoinGroupLisener{
        void onJoin(InetAddress address);
    }
    public interface OnAddressLeaveGroupLisener{
        void  onLeave(InetAddress address);
    }
}
