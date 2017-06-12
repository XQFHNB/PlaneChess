package com.example.module_b_wan.other.manager;

import android.text.TextUtils;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class ManagerAVImClient {

    private String clientId;
    private static ManagerAVImClient imClientManager;

    //持有的AVIMClient实例，这个是用来打开与服务器连接的关键
    private AVIMClient avimClient;


    //线程安全的懒汉单例
    public synchronized static ManagerAVImClient getInstance() {
        if (null == imClientManager) {
            imClientManager = new ManagerAVImClient();
        }
        return imClientManager;
    }

    private ManagerAVImClient() {
    }

    //这是获取管理对象的实例，应该算是stter方法
    public void open(String clientId, AVIMClientCallback callback) {
        this.clientId = clientId;
        avimClient = AVIMClient.getInstance(clientId);
        avimClient.open(callback);
    }


    //下面是两个外界交流项目
    public AVIMClient getClient() {
        return avimClient;
    }

    public String getClientId() {
        if (TextUtils.isEmpty(clientId)) {
            throw new IllegalStateException("Please call ManagerAVImClient.open first");
        }
        return clientId;
    }
}
