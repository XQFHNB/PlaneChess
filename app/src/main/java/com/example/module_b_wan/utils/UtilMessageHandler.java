package com.example.module_b_wan.utils;

import android.content.Context;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.example.module_b_wan.other.event.EventImTypeMessage;
import com.example.module_b_wan.other.manager.ManagerAVImClient;

import de.greenrobot.event.EventBus;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class UtilMessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

    private Context context;

    public UtilMessageHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {

        String clientID = "";
        try {
            clientID = ManagerAVImClient.getInstance().getClientId();
            if (client.getClientId().equals(clientID)) {

                // 过滤掉自己发的消息
                if (!message.getFrom().equals(clientID)) {

                    //接收到了message,把message传给Chatfragment进行显示
                    sendEvent(message, conversation);
                }
            } else {
                client.close(null);
            }
        } catch (IllegalStateException e) {
            client.close(null);
        }
    }

    /**
     * 因为没有 db，所以暂时先把消息广播出去，由接收方自己处理
     * 稍后应该加入 db
     *
     * @param message
     * @param conversation
     */
    private void sendEvent(AVIMTypedMessage message, AVIMConversation conversation) {
        EventImTypeMessage event = new EventImTypeMessage();
        event.message = message;
        event.conversation = conversation;
        EventBus.getDefault().post(event);
    }

//    private void sendNotification(AVIMTypedMessage message, AVIMConversation conversation) {
//        String notificationContent = message instanceof AVIMTextMessage ?
//                ((AVIMTextMessage) message).getText() : context.getString(R.string.unspport_message_type);
//
//        //包装一个notification用的intent,这个intent就是发送一个广播
//        Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
//        intent.putExtra(Constants.CONVERSATION_ID, conversation.getConversationId());
//        intent.putExtra(Constants.MEMBER_ID, message.getFrom());
//        NotificationUtils.showNotification(context, "", notificationContent, null, intent);
//    }
}
