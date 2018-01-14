package com.lll.auxiliary.jobs;

import android.os.Build;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lll.auxiliary.SearchEngines;
import com.lll.auxiliary.service.QAAssistService;
import com.lll.auxiliary.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.accessibility.AccessibilityEvent.eventTypeToString;

/**
 * Created by lll on 2018/1/13.
 * 答题辅助
 */
public abstract class BaseQAAssistJob implements QAAssistJob {

    String TAG = "BaseQAAssistJob";

    BaseQAAssistJob() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onCreate() {
        
    }

    @Override
    public void onKeyEvent(KeyEvent event) {

    }

    @Override
    public void onConnected() {
        SearchEngines.clearHistory();
    }

    @Override
    public abstract String getTargetPackageName();

    @Override
    public boolean isEnable() {
        return true;
    }

    @Override
    public void onEvent(QAAssistService service, AccessibilityEvent event) {
        Log.i(TAG, "event:" + eventTypeToString(event.getEventType()));
    }

    @Override
    public void onDestroy() {
        SearchEngines.clearHistory();
    }

    @Override
    public void onInterrupt() {

    }

    @SuppressWarnings("unused")
    HashMap<String, String> getTextsByViewIds(AccessibilityNodeInfo nodeInfo, String... ids) {
        if (ids == null) return null;
        HashMap<String, String> texts = new HashMap<>();
        for (String id : ids) {
            List<String> list = getTextByViewId(nodeInfo, id);
            if (list != null && !list.isEmpty()) {
                texts.put(id, list.get(0));
            } else {
                texts.put(id, null);
            }
        }
        return texts;
    }

    public List<AccessibilityNodeInfo> getNodeInfoById(AccessibilityNodeInfo nodeInfo, String id) {
        if (nodeInfo == null) return null;
        List<AccessibilityNodeInfo> infos = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            infos = nodeInfo.findAccessibilityNodeInfosByViewId(id);
        }
        return infos;
    }

    List<String> getTextByViewId(AccessibilityNodeInfo nodeInfo, String viewId) {
        if (nodeInfo == null) return null;
        List<AccessibilityNodeInfo> infos = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            infos = nodeInfo.findAccessibilityNodeInfosByViewId(viewId);
        }
        if (infos == null) return null;
        List<String> texts = new ArrayList<>();
        for (AccessibilityNodeInfo info : infos) {
            texts.add(info.getText().toString());
        }
        return texts;
    }
}
