package com.example.module_b_wan.model;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;

import java.util.HashMap;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class ModelWanImpl implements IModelWan {


    @Override
    public void createConversation(AVIMClient client, String name, final OnLoadConversationListener listener) {
        HashMap<String, Object> attr = new HashMap<>();
        attr.put("type", "planechess");
        client.createConversation(null, name, attr, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (null == e) {
                    listener.onSucess(avimConversation);
                }
            }
        });
    }

//    @Override
//    public void getClient(String name, final OnLoadClientListener listener) {
//        AVIMClient tom = AVIMClient.getInstance(name);
//        tom.open(new AVIMClientCallback() {
//
//            @Override
//            public void done(AVIMClient client, AVIMException e) {
//                if (e == null) {
//                    listener.onSucess(client);
//                }
//            }
//        });
//    }

}
