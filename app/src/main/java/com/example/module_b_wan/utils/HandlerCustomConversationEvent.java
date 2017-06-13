package com.example.module_b_wan.utils;

import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;
import com.example.module_b_wan.other.event.EventEnterRoom;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @author XQF
 * @created 2017/6/13
 */
public class HandlerCustomConversationEvent extends AVIMConversationEventHandler {

    @Override
    public void onMemberLeft(AVIMClient client, AVIMConversation conversation, List<String> members,
                             String kickedBy) {
        // 有其他成员离开时，执行此处逻辑
    }

    @Override
    public void onMemberJoined(AVIMClient client, AVIMConversation conversation,
                               List<String> members, String invitedBy) {
        // 手机屏幕上会显示一小段文字：Tom 加入到 551260efe4b01608686c3e0f ；操作者为：Tom
        Toast.makeText(AVOSCloud.applicationContext,
                members + "加入到" + conversation.getConversationId() + "；操作者为： "
                        + invitedBy, Toast.LENGTH_SHORT).show();

        //发送有人加入的事件
        EventEnterRoom eventEnterRoom = new EventEnterRoom();
        eventEnterRoom.setName(members.get(0).toString());
        EventBus.getDefault().post(eventEnterRoom);
    }

    @Override
    public void onKicked(AVIMClient client, AVIMConversation conversation, String kickedBy) {
        // 当前 ClientId(Bob) 被踢出对话，执行此处逻辑
    }

    @Override
    public void onInvited(AVIMClient client, AVIMConversation conversation, String invitedBy) {
        // 当前 ClientId(Bob) 被邀请到对话，执行此处逻辑
    }
}