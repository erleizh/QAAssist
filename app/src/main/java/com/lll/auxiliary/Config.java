package com.lll.auxiliary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lll on 2018/1/14.
 * 配置
 */
public class Config {
    private static final Config ourInstance = new Config();


    private static final String AUTO_CLOSE_SEARCH = "auto_close_search";

    private boolean mAutoCloseSearch;


    public static Config getInstance() {
        return ourInstance;
    }

    private Config() {
        SharedPreferences config = App.getContext().getSharedPreferences("CONFIG", Context.MODE_PRIVATE);
        mAutoCloseSearch = config.getBoolean(AUTO_CLOSE_SEARCH, false);
    }
}
