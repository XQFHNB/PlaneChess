package com.example.junyi.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Timer on 2016/4/13.
 */

/**
 * 客户端通信类，负责与服务器的通信，和通过服务器与其他客户端通信
 */
public class Client {
    private InetAddress addr;       //服务器地址
    private int port;               //服务器端口
    private Socket socket = null;
    private ClientReadThread thread = null;
    private PrintWriter out = null;
    MsgQueue msg = new MsgQueue();

    /**
     * 初始化Client并新建一个线程监听服务器发送来的数据
     * @param inet 服务器地址
     * @param p 服务器端口
     * @throws IOException
     */
    public Client(InetAddress inet, int p) throws IOException {
        this.addr = inet;
        this.port = p;
        this.socket = new Socket(this.addr, this.port);
        try {
            init();
        }catch (IOException e) {
            //打开输入输出流失败
            this.thread = null;
            this.out = null;
            e.printStackTrace();
        }
    }

    /**
     * 初始化Client并新建一个线程监听服务器发送来的数据
     * @param s 与服务器连接的socket
     * @throws IOException
     */
    Client(Socket s) throws IOException {
        this.socket = s;
        this.addr = s.getInetAddress();
        this.port = s.getPort();
        try {
            init();
        }catch (IOException e) {
            //打开输入输出流失败
            this.thread = null;
            this.out = null;
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        this.thread = new ClientReadThread(this);
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter
                (socket.getOutputStream())), true);
    }

    /**
     * 发送数据到服务器（注意，数据不会发送到任何客户端上）
     * @param data 发送的数据
     * @SocketException 发送失败
     */
    public void sendToServer(NetMsg data) throws SocketException {
        if(out.checkError()) {
            throw new SocketException();
        }
        out.println(String.valueOf((char) data.from) + data.data);
        if(out.checkError()) {
            throw new SocketException();
        }
    }

    /**
     * 发送数据到所有客户端和服务器
     * @param data 发送的数据
     * @SocketException 发送失败
     */
    public void sendToAll(NetMsg data) throws SocketException {
        if(out.checkError()) {
            throw new SocketException();
        }
        out.println(String.valueOf((char) (data.from|0x80)) + data.data);
        if(out.checkError()) {
            throw new SocketException();
        }
    }

    /**
     * 关闭与服务器的链接
     */
    public void close(){
        out = null;
        if(thread != null) {
            thread.interrupt();
        }
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            //关闭连接失败，或者连接已经关闭
            e.printStackTrace();
        }finally {
            socket = null;
        }
    }

    /**
     * 获得该客户端消息队列中的数据x
     * @return
     */
    public NetMsg getData() throws InterruptedException {
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
