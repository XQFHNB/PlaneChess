package com.example.module_b_wan.model;

import com.avos.avoscloud.im.v2.AVIMClient;

/**
 * Created by XQF on 2017/6/12.
 */
public interface IModelWan {

    void createConversation(AVIMClient client, String name, OnLoadConversationListener listener);

//    void getClient(String name, OnLoadClientListener listener);

}
