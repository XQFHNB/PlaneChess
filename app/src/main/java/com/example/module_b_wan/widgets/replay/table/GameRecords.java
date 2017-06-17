package com.example.module_b_wan.widgets.replay.table;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author XQF
 * @created 2017/6/16
 */


@Table(name = "records")
public class GameRecords extends Model {

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Column
    private String mContent;

    @Override
    public String toString() {
        return "数据：" + getId() + " " + mContent;
    }
}
