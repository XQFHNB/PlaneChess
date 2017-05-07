package com.example.huangbin.network;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import java.util.List;

/**
 * Created by Huangbin on 2016/4/7.
 */
public class WifiAdmin {
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private List<WifiConfiguration> mWifiConfigurations;
    private List<ScanResult> mScanResults;
    private Context mContext;
    private WifiAdmin(){}
    public WifiAdmin(Context context){
        mContext=context;
        mWifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo=mWifiManager.getConnectionInfo();
    }

    public List<ScanResult> getScanResults() {
        return mScanResults;
    }

    public List<WifiConfiguration> getWifiConfigurations() {

        return mWifiConfigurations;
    }

    public boolean checkWifiPermission(String permission){
        PackageManager packageManager=mContext.getPackageManager();
        return
                PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(
                        permission,mContext.getPackageName());
    }
    public int getWifiState(){
        return mWifiManager.getWifiState();
    }
    public boolean openWifi(){
        if(!mWifiManager.isWifiEnabled())
           return  mWifiManager.setWifiEnabled(true);
        return true;
    }
    public boolean closeWifi(){
        if(mWifiManager.isWifiEnabled()){
          return   mWifiManager.setWifiEnabled(false);
        }
        return true;
    }
    public WifiLock createWifiLock(String name){
        return  mWifiManager.createWifiLock(name);
    }
    public  void requireWifiLock(WifiLock wifiLock){
        if(!wifiLock.isHeld()){
            wifiLock.acquire();
        }
    }
    public void releaseWifiLock(WifiLock wifiLock){
        if(wifiLock.isHeld()){
            wifiLock.release();
        }
    }

    public boolean addNetwork(WifiConfiguration wifiConfiguration) {
        int wifiConfigurationID = mWifiManager.addNetwork(wifiConfiguration);
        return mWifiManager.enableNetwork(wifiConfigurationID, true);
    }
    public boolean connectWithConfigutation(int index){
        if(mWifiConfigurations.size()>index){
            return mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId,true);
        }
        return false;
    }
    public boolean disconnectWithId(int index){
        return mWifiManager.disableNetwork(index)&&mWifiManager.disconnect();
    }

    public boolean startScan(){
        boolean state =mWifiManager.startScan();
        mScanResults=mWifiManager.getScanResults();
        mWifiConfigurations=mWifiManager.getConfiguredNetworks();
        return state;
    }

    public WifiInfo getWifiInfo(){
        return mWifiInfo;
    }
    public String getMacAddress() {
        return (mWifiInfo == null) ? null : mWifiInfo.getMacAddress();
    }
    public String getBSSID() {
        return (mWifiInfo == null) ? null : mWifiInfo.getBSSID();
    }

    /**
     *
     * @return ipv4格式的点分ip
     */
    public String getIPAddress() {
        int ip= (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
        return  (ip & 0xFF)+ "." + ((ip >> 8 ) & 0xFF) + "." + ((ip >> 16 ) & 0xFF) +"."+((ip >> 24 ) & 0xFF );
    }
}
