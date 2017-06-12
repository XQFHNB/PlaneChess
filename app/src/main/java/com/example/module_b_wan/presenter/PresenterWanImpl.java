package com.example.module_b_wan.presenter;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.example.module_b_wan.model.IModelWan;
import com.example.module_b_wan.model.ModelWanImpl;
import com.example.module_b_wan.model.OnLoadConversationListener;
import com.example.module_b_wan.view.IViewWan;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class PresenterWanImpl implements IPresenterWan {

    private IModelWan mIModelWan;
    private IViewWan mIViewWan;


    public PresenterWanImpl(IViewWan iViewWan) {
        mIModelWan = new ModelWanImpl();
        mIViewWan = iViewWan;
    }


    @Override
    public void createConversationByNameAndType(AVIMClient client, String name) {

        mIModelWan.createConversation(client, name, new OnLoadConversationListener() {

            @Override
            public void onSucess(AVIMConversation conversation) {
                mIViewWan.getConversation(conversation);
            }
        });
    }

//    @Override
//    public void getClientByName(String name) {
//        mIModelWan.getClient(name, new OnLoadClientListener() {
//            @Override
//            public void onSucess(AVIMClient client) {
//                mIViewWan.getClient(client);
//            }
//        });
//    }
}
