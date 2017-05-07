package com.example.junyi.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.nio.channels.ClosedByInterruptException;

/**
 * Created by Timer on 2016/4/13.
 */

/**
 * 客户端监听接收数据的线程
 */
public class ClientReadThread extends Thread {
    private Client s;           //客户端的引用
    private BufferedReader in = null;   //连接的input流

    ClientReadThread(Client cc) throws IOException {
        this.s = cc;
        in = new BufferedReader(new InputStreamReader(cc.getSocket().getInputStream()));
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (this.isInterrupted()) {
                    throw new InterruptedException("accept thread has been interrupter");
                }
                String str = in.readLine();
                if(str == null)
                    throw new SocketException("data is null");
                byte ch = (byte) str.charAt(0);
                String info = str.substring(1);
                NetMsg m = new NetMsg(info, (byte) (ch&0x03));
                synchronized (s.msg) {
                    s.msg.addData(m);
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
