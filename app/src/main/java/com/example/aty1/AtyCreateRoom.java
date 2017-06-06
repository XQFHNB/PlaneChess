package com.example.aty1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.yifeihappy.planechess.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author XQF
 * @created 2017/6/5
 */

public class AtyCreateRoom extends AppCompatActivity {


    public static void startAtyCreateRoom(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    @BindView(R.id.btn_createroom)
    protected Button btnCreateRoom;


    @BindView(R.id.radiogroupColor)
    protected RadioGroup mRadioGroup;

    @BindView(R.id.edt_aty_create_room_name)
    protected EditText mEditText;

    @BindView(R.id.btn_num_2)
    protected Button mButton2;
    @BindView(R.id.btn_num_3)
    protected Button mButton3;
    @BindView(R.id.btn_num_4)
    protected Button mButton4;


    private boolean mIsBtn2Visible = true;
    private boolean mIsBtn3Visible;
    private boolean mIsBtn4Visible;


    private String mRoomName;
    private String mPlaysSum;
    private String mRoleColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_create_room);
        ButterKnife.bind(this);
        mRoomName = new String();
        mPlaysSum = new String();
        mRoleColor = new String();
    }

    @OnClick(R.id.btn_createroom)
    public void onBtnCreateRoomClick() {
        if (TextUtils.isEmpty(mEditText.getText())) {
            toast("请输入房间名称");
            return;
        } else {
            mRoomName += mEditText.getText().toString().trim();
        }

        if (mIsBtn2Visible) {
            mPlaysSum += "2";
        } else if (mIsBtn3Visible) {
            mPlaysSum += "3";
        } else if (mIsBtn4Visible) {
            mPlaysSum += "4";
        }

        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton r = (RadioButton) mRadioGroup.getChildAt(i);
            if (r.isChecked()) {
                mRoleColor = String.valueOf(i);
                break;
            }
        }

        AtyWaitClients.startAtyWaitClients(this, AtyWaitClients.class, mRoomName, mPlaysSum, mRoleColor);
        finish();

    }

    @OnClick(R.id.btn_num_2)
    public void onBtnNum2Click() {
        mIsBtn2Visible = true;
        mIsBtn3Visible = false;
        mIsBtn4Visible = false;
        mButton2.setBackground(getResources().getDrawable(R.drawable.bg_select_circle));
        mButton3.setBackground(null);
        mButton4.setBackground(null);

    }

    @OnClick(R.id.btn_num_3)
    public void onBtnNum3Click() {
        mIsBtn2Visible = false;
        mIsBtn3Visible = true;
        mIsBtn4Visible = false;
        mButton2.setBackground(null);
        mButton3.setBackground(getResources().getDrawable(R.drawable.bg_select_circle));
        mButton4.setBackground(null);

    }

    @OnClick(R.id.btn_num_4)
    public void onBtnNum4Click() {
        mIsBtn2Visible = false;
        mIsBtn3Visible = false;
        mIsBtn4Visible = true;
        mButton2.setBackground(null);
        mButton3.setBackground(null);
        mButton4.setBackground(getResources().getDrawable(R.drawable.bg_select_circle));
    }


    public void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
