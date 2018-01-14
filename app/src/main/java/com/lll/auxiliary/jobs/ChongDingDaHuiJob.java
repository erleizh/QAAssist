package com.lll.auxiliary.jobs;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lll.auxiliary.SearchEngines;
import com.lll.auxiliary.bean.Topic;
import com.lll.auxiliary.service.QAAssistService;
import com.lll.auxiliary.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lll on 2018/1/13.
 * 冲顶大会
 *
 * @version 1.0 只有问题 ， 没有答案
 */
public class ChongDingDaHuiJob extends BaseQAAssistJob {
    private static final String PACKAGE_NAME = "com.chongdingdahui.app";

    @Override
    public String getTargetPackageName() {
        return PACKAGE_NAME;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEvent(QAAssistService service, AccessibilityEvent event) {
        super.onEvent(service, event);
        AccessibilityNodeInfo activeWindow = service.getRootInActiveWindow();
        if (activeWindow == null) return;
        List<String> question = getTextByViewId(activeWindow, "2131755202");//Q
        if (question == null) return;
        Topic topic = new Topic();
        for (String q : question) {
            Log.e(TAG, "question : " + q);
            topic.setQuestion(q);
        }

        List<AccessibilityNodeInfo> answer1 = getNodeInfoById(activeWindow, "2131755214");
        if(answer1 != null && !answer1.isEmpty()){
            for (AccessibilityNodeInfo info : answer1) {
                Log.e(TAG, "answer1NodeInfo : " + info);
            }
        }
        List<AccessibilityNodeInfo> answer2 = getNodeInfoById(activeWindow, "2131755215");
        if(answer1 != null && !answer1.isEmpty()){
            for (AccessibilityNodeInfo info : answer2) {
                Log.e(TAG, "answer1NodeInfo : " + info);
            }
        }
        List<AccessibilityNodeInfo> answer3 = getNodeInfoById(activeWindow, "2131755216");
        if(answer1 != null && !answer1.isEmpty()){
            for (AccessibilityNodeInfo info : answer3) {
                Log.e(TAG, "answer1NodeInfo : " + info);
            }
        }
//        HashMap<String, String> answer = getTextsByViewIds(activeWindow, "2131755214", "2131755215", "2131755216");//A
//        if (answer == null) return;
//        for (Map.Entry<String, String> entry : answer.entrySet()) {
//            Log.e(TAG, "answer : " + entry.getValue());
//            topic.addAnswer(entry.getValue());
//        }
        SearchEngines.search(service.getBaseContext(), topic);
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
            AccessibilityNodeInfo parent = info.getParent();
            if (parent != null && parent.getChildCount() == 5) {
                CharSequence className = parent.getClassName();
                if (className != null && TextUtils.equals("android.widget.RelativeLayout", className)) {
                    texts.add(info.getText().toString());
                }
            }
        }
        return texts;
    }
}
