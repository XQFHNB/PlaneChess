package com.example.module_b_wan.widgets.creator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.example.module_b_wan.other.event.EventEnterRoom;
import com.example.module_b_wan.other.manager.ManagerAVImClient;
import com.example.module_b_wan.utils.DataText;
import com.example.module_b_wan.utils.HandlerEnterConversationEvent;
import com.example.module_b_wan.widgets.AtyBase;
import com.example.yifeihappy.planechess.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class AtyWaitClients extends AtyBase {
    public static final String KEY_CONVERSATIONID = "conversationId";
    public static final String KEY_ROOMNAME = "roomname";
    public static final String KEY_ROLECOLOR = "rolecolor";


    public static final String TAG_BEGIN = "begin";
    public static final String TAG_FIRST = "first";

    public static void startAtyWaitClients(Context context, Class<?> cls, String conversationId, String room, String roleColor) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(KEY_CONVERSATIONID, conversationId);
        intent.putExtra(KEY_ROOMNAME, room);
        intent.putExtra(KEY_ROLECOLOR, roleColor);
        context.startActivity(intent);
    }


    @BindView(R.id.btn_aty_wait_clients)
    protected Button mButtonBack;

    @BindView(R.id.btnBegin)
    protected Button mButtonBegin;

    @BindView(R.id.room_name)
    protected TextView mTextViewRoomName;

    @BindView(R.id.player1)
    protected LinearLayout mLinearLayoutPlayer1;
    @BindView(R.id.player2)
    protected LinearLayout mLinearLayoutPlayer2;
    @BindView(R.id.player3)
    protected LinearLayout mLinearLayoutPlayer3;
    @BindView(R.id.player4)
    protected LinearLayout mLinearLayoutPlayer4;

    private LinearLayout[] mPlayersLinear = null;

    private AVIMConversation mAVIMConversation;

    private String mRoomName;
    private String mRoleColor;

    private AVIMClient mAVIMClient;


    private String mConversationId;

    String mRolesNames;
    String mRolesColors;

    private List<String> mPlayersNames;
    private int[] mPlayerColors = new int[]{R.color.blue, R.color.red, R.color.yellow, R.color.green};

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.wait_clients);
        mButtonBegin.setClickable(false);
        mPlayersLinear = new LinearLayout[]{
                mLinearLayoutPlayer1,
                mLinearLayoutPlayer2,
                mLinearLayoutPlayer3,
                mLinearLayoutPlayer4
        };
        mConversationId = getIntent().getStringExtra(KEY_CONVERSATIONID);
        mRoomName = getIntent().getStringExtra(KEY_ROOMNAME);
        mRoleColor = getIntent().getStringExtra(KEY_ROLECOLOR);

        mTextViewRoomName.setText(mRoomName);
        mPlayersNames = new ArrayList<>();
        mPlayersNames.add(mRoomName);
        mPlayersLinear[0].setBackgroundResource(mPlayerColors[0]);

        mAVIMClient = ManagerAVImClient.getInstance().getClient();
        getConversation();
        sentFirstMessage();


        //设置消息通知回调
        AVIMMessageManager.setConversationEventHandler(new HandlerEnterConversationEvent());
    }


    @OnClick(R.id.btnBegin)
    public void onBtnBeginClick() {
        final List<String> names = new ArrayList<>();
        final List<String> colors = new ArrayList<>();
        final int limit = 4;
        mAVIMConversation.queryMessages(limit, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (null == e) {
                    for (int i = 0; i < list.size(); i++) {
                        AVIMMessage message = list.get(i);
                        DataText text = DataText.getDataTextFromString(message.getContent());
                        if (text.getTag().equals(TAG_FIRST)) {
                            names.add(text.getName());
                            colors.add(text.getColor());
                        }
                    }
                }
            }
        });

        StringBuffer sbNames = new StringBuffer();
        StringBuffer sbColors = new StringBuffer();
        for (int i = 0; i < names.size(); i++) {
            if (i != names.size() - 1) {
                sbNames.append(names.get(i) + "#");
                sbColors.append(colors.get(i) + "#");
            } else {
                sbNames.append(names.get(i));
                sbColors.append(colors.get(i));
            }
        }
        mRolesNames = sbNames.toString();
        mRolesColors = sbColors.toString();

        sendBeginMessage();
        AtyGameCreator.startAtyGameCreator(this, AtyGameCreator.class, mConversationId, mRolesNames, mRolesColors, mRoomName, mRoleColor);
        finish();

    }

    //-------------------------------------------------------------------查询得到Conversation
    private void getConversation() {
        mAVIMConversation = mAVIMClient.getConversation(mConversationId);
    }

    //---------------------------------------------------------------------------发送第一条消息
    private void sentFirstMessage() {

        AVIMMessage message = new AVIMMessage();
        DataText text = new DataText();
        text.setTag(TAG_FIRST);
        text.setName(mRoomName);
        text.setColor(mRoleColor);
        message.setContent(DataText.getDataTextContentString(text));
        mAVIMConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (null == e) {
                    toast("发送第一条消息成功");
                }
            }
        });
    }

    private void sendBeginMessage() {
        AVIMMessage message = new AVIMMessage();
        DataText text = new DataText();
        text.setTag(TAG_BEGIN);
        text.setName(mRolesNames);
        text.setColor(mRolesColors);
        message.setContent(DataText.getDataTextContentString(text));
        mAVIMConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (null == e) {
                    toast("发送开始消息成功");
                }
            }
        });
    }


    /**
     * 接收有人加入房间的消息，这个只是通知房主就可以了
     *
     * @param eventEnterRoom
     */

    public void onEvent(EventEnterRoom eventEnterRoom) {
        if (null != eventEnterRoom && mPlayersNames.size() < 4) {
            String playerName = eventEnterRoom.getName();
            mPlayersNames.add(playerName);
            int size = mPlayersNames.size();
            mPlayersLinear[size - 1].setBackgroundResource(mPlayerColors[size - 1]);
        }
        if (null != eventEnterRoom && mPlayersNames.size() == 4) {
            mButtonBegin.setClickable(true);
            toast("点击开始游戏");
        }

    }
}

