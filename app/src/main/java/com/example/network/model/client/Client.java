package com.example.network.model.client;

import android.util.Log;

import com.example.network.model.QueueMsg;
import com.example.network.model.MsgNet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Timer on 2016/4/13.
 */

/**
 * 客户端通信类，负责与服务器的通信，和通过服务器与其他客户端通信
 */
public class Client implements Serializable {

    public static String TAG = "client";

    private InetAddress addr;       //服务器地址
    private int port;               //服务器端口
    private Socket socket = null;
    private ThreadClientRead mThreadClientRead = null;
    private PrintWriter out = null;
    QueueMsg msg = new QueueMsg();


//    -----------------------------------------------------------------------------------------------------------------------

    private static Client sClient;

    private Client(InetAddress inet, int p) throws IOException {


        Log.e("test", "CLient的构造方法");
        this.addr = inet;
        this.port = p;
        this.socket = new Socket(this.addr, this.port);
        try {
            init();
        } catch (IOException e) {
            //打开输入输出流失败
            mThreadClientRead = null;
            this.out = null;
            e.printStackTrace();
        }
    }

    public static Client newInstance(InetAddress inetAddress, int port) throws IOException {
        if (sClient == null) {
            sClient = new Client(inetAddress, port);
        }
        return sClient;
    }


    //    -----------------------------------------------------------------------------------------------------------------------


    /**
     * 初始化Client并新建一个线程监听服务器发送来的数据
     *
     * @param s 与服务器连接的socket
     * @throws IOException
     */
    Client(Socket s) throws IOException {
        this.socket = s;
        this.addr = s.getInetAddress();
        this.port = s.getPort();
        try {
            init();
        } catch (IOException e) {
            //打开输入输出流失败
            mThreadClientRead = null;
            this.out = null;
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        mThreadClientRead = new ThreadClientRead(this);
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter
                (socket.getOutputStream())), true);
    }

    /**
     * 发送数据到服务器（注意，数据不会发送到任何客户端上）
     *
     * @param data 发送的数据
     * @SocketException 发送失败
     */
    public void sendToServer(MsgNet data) throws SocketException {

        Log.d(TAG, "向服务器传送的内容" + data.toString());
        if (out.checkError()) {
            throw new SocketException();
        }
        out.println(String.valueOf((char) data.getFrom()) + data.getData());
        if (out.checkError()) {
            throw new SocketException();
        }
    }

    /**
     * 发送数据到所有客户端和服务器
     *
     * @param data 发送的数据
     * @SocketException 发送失败
     */
    public void sendToAll(MsgNet data) throws SocketException {
        if (out.checkError()) {
            throw new SocketException();
        }
        out.println(String.valueOf((char) (data.getFrom() | 0x80)) + data.getData());
        if (out.checkError()) {
            throw new SocketException();
        }
    }

    /**
     * 关闭与服务器的链接
     */
    public void close() {
        out = null;
        if (mThreadClientRead != null) {
            mThreadClientRead.interrupt();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            //关闭连接失败，或者连接已经关闭
            e.printStackTrace();
        } finally {
            socket = null;
        }
    }

    /**
     * 获得该客户端消息队列中的数据x
     *
     * @return
     */
    public MsgNet getData() throws InterruptedException {
        return msg.getData();
    }

    public Socket getSocket() {
        return socket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddr() {
        return addr;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }


}
