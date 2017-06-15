package com.example.module_b_wan.widgets.creator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.module_b_wan.widgets.AtyBase;
import com.example.yifeihappy.planechess.R;

/**
 * @author XQF
 * @created 2017/6/15
 */
public class AtyGameCreator extends AtyBase {


    public static final String KEY_NAMES = "names";
    public static final String KEY_COLORS = "colors";
    public static final String KEY_ROLENAME = "rolename";
    public static final String KEY_ROLECOLOR = "rolecolor";
    public static final String KEY_CONVERSATIONID = "conversationID";


    public static void startAtyGameCreator(Context context, Class<?> cls, String conversationId, String names, String colors, String roleName, String roleColor) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(KEY_CONVERSATIONID, conversationId);
        intent.putExtra(KEY_NAMES, names);
        intent.putExtra(KEY_COLORS, colors);
        intent.putExtra(KEY_ROLENAME, roleName);
        intent.putExtra(KEY_ROLECOLOR, roleColor);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.aty_game_sever);


    }
}
