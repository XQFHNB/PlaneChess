package com.example.module_b_wan.widgets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * @author XQF
 * @created 2017/6/12
 */
public class AtyWaitClients extends AppCompatActivity {

    public static void startAtyWaitClients(Context context, Class<?> cls, String conversationId) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("conversationId", conversationId);
        context.startActivity(intent);
    }

}
