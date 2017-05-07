package com.example.junyi.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.ClosedByInterruptException;

/**
 * Created by Timer on 2016/4/13.
 */

/**
 * 服务器监听接收数据的线程
 */
public class ServerReadThread extends Thread {
    private ServerInTele server = null;     //手机服务器的引用
    private Socket socket = null;           //对应的客户端的socket  //?
    private BufferedReader in = null;       //对应socket的input流

    ServerReadThread(ServerInTele ser, Socket s) throws IOException {
        this.server = ser;
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.start();
    }

    @Override
    public void run() {
        try {
            while(true) {
                if (this.isInterrupted()) {
                    throw new InterruptedException("accept thread has been interrupter");
                }
                String str = in.readLine();
                if(str == null)
                    throw new SocketException("data is null");
                byte ch = (byte) str.charAt(0);
                String info = str.substring(1);
                NetMsg m = new NetMsg(info, (byte) (ch&0x03));
                synchronized (server.msg) {
                    server.msg.addData(m);
                }
                if((ch&0x80) != 0x00) {  //    ?
                    server.sendToAll(m);
                }
            }
        } catch (ClosedByInterruptException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(e instanceof SocketException) {
                //读取数据出现错误，或者连接已关闭
            }
            e.printStackTrace();
        } catch (InterruptedException e) {
            this.interrupt();
        }
    }
}
