package com.example.module_b_wan.presenter;

import com.avos.avoscloud.im.v2.AVIMClient;

/**
 * Created by XQF on 2017/6/12.
 */
public interface IPresenterWan {
    void createConversationByNameAndType(AVIMClient client, String name);

//    void getClientByName(String name);
}
