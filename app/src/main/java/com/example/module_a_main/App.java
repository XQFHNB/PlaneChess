package com.example.module_a_main;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.example.module_b_wan.utils.HandlerMessage;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "lxoHsk6Nv5YExQBFIEhPugYL-gzGzoHsz",
                "kkiIV2b1nUT1YJvQMAutldkD");


        // 必须在启动的时候注册 HandlerMessage
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 HandlerMessage 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new HandlerMessage(this));
    }
}
