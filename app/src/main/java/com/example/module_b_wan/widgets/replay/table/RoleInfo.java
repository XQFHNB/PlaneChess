package com.example.module_b_wan.widgets.replay.table;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author XQF
 * @created 2017/6/17
 */
@Table(name = "roleinfo")
public class RoleInfo extends Model {
    @Column(name = "names")
    private String mNames;
    @Column(name = "colors")
    private String mColors;

    public String getNames() {
        return mNames;
    }

    public void setNames(String names) {
        mNames = names;
    }

    public String getColors() {
        return mColors;
    }

    public void setColors(String colors) {
        mColors = colors;
    }

    @Override
    public String toString() {
        return "names: " + mNames + "\ncolors: " + getColors();
    }
}
