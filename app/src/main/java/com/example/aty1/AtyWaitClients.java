package com.example.aty1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * @author XQF
 * @created 2017/6/5
 */
public class AtyWaitClients extends AppCompatActivity {

    public static void startAtyWaitClients(Context context, Class<?> cls, String roomName, String playerSum, String roleColor) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("roomname", roomName);
        intent.putExtra("playersum", playerSum);
        intent.putExtra("rolecolor", roleColor);
        context.startActivity(intent);
    }

}
