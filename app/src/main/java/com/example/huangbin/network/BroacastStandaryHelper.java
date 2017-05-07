package com.example.huangbin.network;

/**
 * 该类用于广播，1对无限
 * Created by Huangbin on 2016/4/8.
 */
public class BroacastStandaryHelper extends BroastcastP2PHelper {

    /**
     * 广播地址
     */
    private final  static String ADDR_ANY="255.255.255.255" ;
    public  BroacastStandaryHelper(){
        super();
    }
    public  BroacastStandaryHelper(int port){
        super(port);
    }

    /**
     * 广播信息
     * @param port
     * @param msg
     * @return
     */
    public boolean broadcast(int port,String msg){
       return this.sendMsg(ADDR_ANY,port,msg);
    }
}
