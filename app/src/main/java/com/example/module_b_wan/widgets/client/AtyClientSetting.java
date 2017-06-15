package com.example.module_b_wan.widgets.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.example.module_b_wan.other.event.EventImTypeMessage;
import com.example.module_b_wan.other.manager.ManagerAVImClient;
import com.example.module_b_wan.utils.DataText;
import com.example.module_b_wan.widgets.AtyBase;
import com.example.yifeihappy.planechess.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/15
 */
public class AtyClientSetting extends AtyBase {
    public static final String TAG_BEGIN = "begin";
    public static final String TAG_FIRST = "first";


    public static final String KEY_CONVERSATIONID = "conversationId";

    public static void startAtyClientSetting(Context context, Class<?> cls, String conversationId) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(KEY_CONVERSATIONID, conversationId);
        context.startActivity(intent);
    }

    @BindView(R.id.edtName)
    protected EditText mEditTextName;

    @BindView(R.id.radiogroupColor)
    protected RadioGroup mRadioGroupColor;


    @BindView(R.id.btnEnter)
    protected Button mButtonEnter;


    private String mConversationId;
    private String mRoleNames;
    private String mRoleColors;
    private String mRoleColor;
    private String mRoleName;


    private AVIMConversation mAVIMConversation;
    private AVIMClient mAVIMClient;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.user_setting);
        mConversationId = getIntent().getStringExtra(KEY_CONVERSATIONID);

    }


    @OnClick(R.id.btnEnter)
    public void onBtnEnterClick() {

        if (TextUtils.isEmpty(mEditTextName.getText())) {
            toast("请输入昵称");
            return;
        } else {
            mRoleName = mEditTextName.getText().toString().trim();
        }
        int radi;
        for (radi = 0; radi < mRadioGroupColor.getChildCount(); radi++) {
            RadioButton r = (RadioButton) mRadioGroupColor.getChildAt(radi);
            if (r.isChecked()) {
                mRoleColor = String.valueOf(radi);
                break;
            } else {
                r.setBackground(null);
            }

        }
        if (radi == mRadioGroupColor.getChildCount()) {
            toast("请输选择一种颜色");
            return;
        }
        ManagerAVImClient.getInstance().open(mRoleName, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    mAVIMClient = avimClient;
                }
            }
        });


        //这里要注意的是是不是要是人没有加入对话就能查询对话内容
        getConversation();


        if (isColorUesd()) {
            toast("该颜色已经被人家选了，请重新选择");
            return;
        } else {
            joinConversation();
            sentFirstMessage();
        }
        mButtonEnter.setClickable(false);
    }


    //-------------------------------------------------------------------查询得到Conversation
    private void getConversation() {
        mAVIMConversation = mAVIMClient.getConversation(mConversationId);
    }

    private boolean isColorUesd() {

        final int limit = 4;
        final List<String> colors = new ArrayList<>();
        mAVIMConversation.queryMessages(limit, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (null == e) {
                    for (int i = 0; i < list.size(); i++) {
                        AVIMMessage message = list.get(i);
                        DataText text = DataText.getDataTextFromString(message.getContent());
                        colors.add(text.getColor());
                    }
                }
            }
        });
        if (colors.contains(mRoleColor)) {
            return true;
        }
        return false;
    }

    //-----------------------------------------------------------------加入conversation
    private void joinConversation() {
        mAVIMConversation.join(new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (null == e) {
                    toast("加入对话成功！");
                }
            }
        });
    }

    //---------------------------------------------------------------------------发送第一条消息
    private void sentFirstMessage() {

        AVIMMessage message = new AVIMMessage();
        DataText text = new DataText();
        text.setTag(TAG_FIRST);
        text.setName(mRoleName);
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

    /**
     * 接收来自房主的开始消息
     *
     * @param eventImTypeMessage
     */

    public void onEvent(EventImTypeMessage eventImTypeMessage) {
        String messageContent = eventImTypeMessage.message.getContent();
        DataText text = DataText.getDataTextFromString(messageContent);
        if (text.getTag().equals(TAG_BEGIN)) {
            //跳转
            mRoleNames = text.getName();
            mRoleColors = text.getColor();

            AtyGameClient.startAtyGameClient(this, AtyGameClient.class, mConversationId, mRoleNames, mRoleColors, mRoleName, mRoleColor);
            finish();
        }
    }


}
