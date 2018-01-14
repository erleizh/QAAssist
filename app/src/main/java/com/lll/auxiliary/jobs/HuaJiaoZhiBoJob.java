package com.lll.auxiliary.jobs;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lll.auxiliary.service.QAAssistService;
import com.lll.auxiliary.utils.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lll on 2018/1/13.
 * 花椒直播-百万赢家
 */
public class HuaJiaoZhiBoJob extends BaseQAAssistJob {
    private static final String PACKAGE_NAME = "com.huajiao";

    @Override
    public String getTargetPackageName() {
        return PACKAGE_NAME;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEvent(QAAssistService service, AccessibilityEvent event) {
        super.onEvent(service, event);
        AccessibilityNodeInfo window = service.getRootInActiveWindow();
        if (window == null) return;
        HashMap<String, String> ids = getTextsByViewIds(window, "");
        if (ids == null) return;
        for (Map.Entry<String, String> entry : ids.entrySet()) {
            Log.e(TAG, "id :" + entry.getKey() + "\t\t text :" + entry.getValue());
        }
    }
}
