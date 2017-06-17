package com.example.module_b_wan.widgets.replay;

import com.activeandroid.query.Select;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.example.module_b_wan.widgets.replay.table.GameRecords;
import com.example.module_b_wan.widgets.replay.table.RoleInfo;

import java.util.List;

/**
 * @author XQF
 * @created 2017/6/16
 */
public class DBHelper {

    public static void saveDataGameRecords(AVIMMessage message) {
        GameRecords records = new GameRecords();
        records.setContent(message.getContent());
        records.save();
    }

    public static List<GameRecords> queryDataGameRecords() {
        List<GameRecords> result = new Select().from(GameRecords.class).execute();
        return result;
    }

    public static void saveDataRoleInfos(String names, String colors) {
        //保存本次游戏所有用户的信息
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setNames(names);
        roleInfo.setColors(colors);
        roleInfo.save();
    }

    public static List<RoleInfo> queryDataRoleInfos() {
        List<RoleInfo> result = new Select().from(RoleInfo.class).execute();
        return result;
    }
}
