package com.example.module_b_lan.network.model.sever;

import android.util.Log;

import com.example.module_b_lan.network.model.QueueMsg;
import com.example.module_b_lan.network.model.MsgNet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


/**
 * Created by Timer on 2016/4/17.
 * <p/>
 * 管理接收到的信息的队列
 * <p/>
 * 管理接收到的信息的队列
 * <p/>
 * 管理接收到的信息的队列
 * <p/>
 * 管理接收到的信息的队列
 * <p/>
 * 管理接收到的信息的队列
 * <p/>
 * 管理接收到的信息的队列
 * <p/>
 * 管理接收到的信息的队列
 */

/**
 * 管理接收到的信息的队列
 */


/**
 * 在手机上的服务器通信类，存储了一个房间里四个玩家（电脑也算玩家的话）的网络连接
 * 其中index为0是表示房主，网络连接的数据为空
 * 如果index为2的玩家是电脑玩家，则网络连接数据为空
 */
public class ServerInTele implements Serializable {

    public static final String TAG = "test";
    protected int PORT = 1188;
    protected int MAXPLAYER = 4;         //玩家数
    protected ServerSocket server = null;
    protected Integer n = 0;
    protected Socket clientsocket[] = null;
    protected PrintWriter out[] = null;
    protected ThreadServerRead thread[] = null;
    protected Thread accptthread = null;
    QueueMsg msg = null;

    protected void init(int maxplayer) {
        MAXPLAYER = maxplayer;
        clientsocket = new Socket[maxplayer];
        out = new PrintWriter[maxplayer];
        thread = new ThreadServerRead[maxplayer];
        msg = new QueueMsg();
        for (int i = 0; i < MAXPLAYER; i++) {
            clientsocket[i] = null;
        }
        out[0] = null;
        thread[0] = null;
        n = 1;
    }
//
//    public ServerInTele(ServerSocket server, int maxplaer) {
//        this.server = server;
//        init(maxplaer);
//    }

    /**
     * 会新建ServerSocket
     *
     * @throws IOException
     */
    public ServerInTele(int maxplaer) throws IOException {
        server = new ServerSocket(PORT);
        init(maxplaer);
    }


//    public ServerInTele(int maxplaer, int PORT) throws IOException {
//        this.PORT = PORT;
//        server = new ServerSocket(PORT);
//        init(maxplaer);
//    }


//    -----------------------------------------------------------------------------------------------------------------------

    /*
    简单使用单例测试是不是可行
     */
    private ServerInTele(int maxplaer, int PORT) throws IOException {
        Log.e("test", "ServerInTele的构造方法");
        this.PORT = PORT;
        server = new ServerSocket(PORT);
        init(maxplaer);
    }

    private static ServerInTele sServerInTele = null;

    public static ServerInTele newInstance(int max, int port) throws IOException {
        if (sServerInTele == null) {
            sServerInTele = new ServerInTele(max, port);
        }
        return sServerInTele;
    }


//    -----------------------------------------------------------------------------------------------------------------------


    /**
     * 新建一个线程监听客户端发来的连接请求，每当有连接建立，会新建一个线程监听发送来的数据
     * 并将一条from为0x60，data为n的数据加入消息队列
     */
    public void accept() {
        accptthread = new Thread() {
            @Override
            public void run() {
                try {
                    while (n < MAXPLAYER) {
                        if (this.isInterrupted()) {
                            throw new InterruptedException("accept thread has been interrupter");
                        }
                        synchronized (n) {
                            clientsocket[n] = server.accept();
                            out[n] = new PrintWriter(new BufferedWriter(new OutputStreamWriter
                                    (clientsocket[n].getOutputStream())), true);
                            thread[n] = new ThreadServerRead(ServerInTele.this, clientsocket[n]);
                            //有客户端连接了，表示是第一次连接，于是自己捏造一个msg，，。
                            MsgNet m = new MsgNet(n.toString(), (byte) 0x60);
                            msg.addData(m);

                            Log.d(TAG, "有客户连接了");
                            n++;
                        }
                    }

                    // TODO: 2017/6/3 服务器接收其他不是第一次连接的消息
                } catch (IOException e) {
                    if (e instanceof SocketException) {
                        //Socket is not bound or Socket output is shutdown orSocket input is shutdown
                        if ("Socket output is shutdown".equals(e.getMessage())) {
                            close(n--);
                        } else if ("Socket input is shutdown".equals(e.getMessage())) {
                            close(n--);
                        } else {
                            //socket未连接
                        }
                    }
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
        };
        accptthread.start();
    }

    /**
     * 添加一个电脑玩家，它的网络连接信息为空
     *
     * @return true：成功
     * false：失败
     */
    public boolean addAi() {
        synchronized (n) {
            if (n < MAXPLAYER) {
                clientsocket[n] = null;
                out[n] = null;
                thread[n] = null;
                n++;
                if (n == MAXPLAYER) {
                    accptthread.interrupt();
                }
                return true;
            }
            return false;
        }
    }

    /**
     * 向指定index的玩家发送数据
     *
     * @param index 指定玩家索引
     * @param data  发送的数据
     */
    public void send(int index, MsgNet data) {//?
        if (index != 0) {
            if (clientsocket[index] != null) {
                out[index].println(String.valueOf((char) data.getFrom()) + data.getData());
            }
        }
    }

    /**
     * 给所有玩家发送数据
     *
     * @param data 发送的数据
     */
    public void sendToAll(MsgNet data) {
        for (int i = 1; i < n; i++) {
            if (clientsocket[i] != null) {
                out[i].println(String.valueOf((char) data.getFrom()) + data.getData());
            }
        }
    }

    /**
     * 获得队列中接受到的数据
     *
     * @return
     */
    public MsgNet getData() throws InterruptedException {
        return msg.getData();
    }

    /**
     * 关闭指定玩家的网络连接
     *
     * @param index 指定玩家的索引
     */
    public void close(int index) {
        out[index] = null;
        if (thread[index] != null) {
            thread[index].interrupt();
        }
        try {
            if (clientsocket[index] != null) {
                clientsocket[index].close();
            }
        } catch (IOException e) {
            //关闭连接失败，或者连接已经关闭
            e.printStackTrace();
        } finally {
            clientsocket[index] = null;
        }
    }

    /**
     * 关闭所有玩家的网络连接
     *
     * @throws IOException
     */
    public void closeAll() {
        for (int index = 1; index < n; index++) {
            out[index] = null;
            if (thread[index] != null) {
                thread[index].interrupt();
            }
            try {
                if (clientsocket[index] != null) {
                    clientsocket[index].close();
                }
            } catch (IOException e) {
                //关闭连接失败，或者连接已经关闭
                e.printStackTrace();
            } finally {
                clientsocket[index] = null;
            }
        }
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            //关闭客户端失败，或者客户端已经关闭
            e.printStackTrace();
        } finally {
            server = null;
        }
    }
}
