package com.example.huangbin.network;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 获取手机IP地址
 * Created by Huangbin on 2016/4/8.
 */
public class IPAdressHelper {

    /**
     * 必须在wifi连接的前提下使用且该应用有使用wifi的权限，否则返回null
     * @param context
     * @return ip
     */
    public static String getIPByWifi(Context context){
        WifiManager wifiManager= (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()){
            int ip= wifiManager.getConnectionInfo().getIpAddress();
//            Toast.makeText(context,ip,Toast.LENGTH_LONG).show();
            int mask=0xff;
            return (ip&mask)+"."
                    +((ip>>8)&mask)+"."
                    +((ip>>16)&mask)+"."
                    +((ip>>24)&mask);
        }
        return null;
    }

    /**
     * 必须在移动数据连接的前提下使用且该应用有使用移动数据的权限，否则返回null
     * @return ip
     */
    public static String getIPByMobileData() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAdresses = networkInterface.getInetAddresses();
                while (inetAdresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAdresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("getIPByMobileData:" + e.getMessage());

        }
        return null;
    }
}
