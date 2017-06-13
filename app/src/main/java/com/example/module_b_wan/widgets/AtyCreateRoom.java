package com.example.module_b_wan.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.module_b_wan.other.manager.ManagerAVImClient;
import com.example.module_b_wan.presenter.IPresenterWan;
import com.example.module_b_wan.presenter.PresenterWanImpl;
import com.example.module_b_wan.view.IViewWan;
import com.example.yifeihappy.planechess.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class AtyCreateRoom extends AtyBase implements IViewWan {

    @BindView(R.id.radiogroupColorWan)
    protected RadioGroup mRadioGroup;

    @BindView(R.id.edt_aty_create_room_name_wan)
    protected EditText mEditTextName;

    @BindView(R.id.btn_createroom_wan)
    protected Button mButtonCreateRoom;


    private String mRoomName;
    private String mPlaysSum;
    private String mRoleColor;

    private AVIMClient mAVIMClient;
    private IPresenterWan mIPresenterWan;
    private AVIMConversation mAVIMConversation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_create_room_wan);
        mIPresenterWan = new PresenterWanImpl(this);
        mRoomName = new String();
        mPlaysSum = new String();
        mRoleColor = new String();
    }

    @OnClick(R.id.btn_createroom_wan)
    public void onBtnCreateRoomClick() {
        if (TextUtils.isEmpty(mEditTextName.getText())) {
            toast("请输入房间名称");
            return;
        } else {
            mRoomName += mEditTextName.getText().toString().trim();
        }
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton r = (RadioButton) mRadioGroup.getChildAt(i);
            if (r.isChecked()) {
                mRoleColor = String.valueOf(i);
                break;
            }
        }
        //先根据名字创建一个client
//        mIPresenterWan.getClientByName(mRoomName);

        ManagerAVImClient.getInstance().open(mRoomName, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                mAVIMClient = avimClient;
            }
        });

        //创建对话
        if (null != mAVIMClient) {
            mIPresenterWan.createConversationByNameAndType(mAVIMClient, mRoomName);
        }
        String conversationId = mAVIMConversation.getConversationId();
        AtyWaitClients.startAtyWaitClients(this, AtyWaitClients.class, conversationId,mRoomName);
    }


    @Override
    public void getConversation(AVIMConversation conversation) {
        mAVIMConversation = conversation;
    }

//    @Override
//    public void getClient(AVIMClient client) {
//        mAVIMClient = client;
//    }
}
