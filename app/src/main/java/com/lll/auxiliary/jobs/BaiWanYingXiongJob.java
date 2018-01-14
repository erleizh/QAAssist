package com.lll.auxiliary.jobs;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import com.lll.auxiliary.SearchEngines;
import com.lll.auxiliary.bean.Topic;
import com.lll.auxiliary.service.QAAssistService;
import com.lll.auxiliary.utils.Log;

import java.util.List;

/**
 * Created by lll on 2018/1/13.
 * 西瓜视频-百万英雄
 */
public class BaiWanYingXiongJob extends BaseQAAssistJob {

    private static final String PACKAGE_NAME = "com.ss.android.article.video";

    @Override
    public String getTargetPackageName() {
        return PACKAGE_NAME;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEvent(QAAssistService service, AccessibilityEvent event) {
        super.onEvent(service, event);
        AccessibilityNodeInfo window = service.getRootInActiveWindow();
        if (window != null) {
            recycle(window);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            List<AccessibilityWindowInfo> windows = service.getWindows();
            if (windows != null && !windows.isEmpty()) {
                for (AccessibilityWindowInfo windowInfo : windows) {
                    recycle(windowInfo.getRoot());
                }
            }
        }
        if(window == null)return;
        List<String> question = getTextByViewId(window, "2131755270");//Q
        if (question == null || question.isEmpty()) return;
        Topic topic = new Topic();
        for (String q : question) {
            Log.e(TAG, "question : " + q);
            topic.setQuestion(q);
        }
        List<String> answer = getTextByViewId(window, "2131755523");//A
        if (answer == null) return;
        for (String a : answer) {
            topic.addAnswer(a);
            Log.e(TAG, "answer : " + a);
        }
        SearchEngines.search(service.getBaseContext(), topic);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void recycle(AccessibilityNodeInfo info) {
        if (info == null) return;
        if (info.getChildCount() == 0) {
            Log.i(TAG, "Text：" + info.getText());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "AvailableExtraData：" + info.getAvailableExtraData());
            }
            Bundle extras = info.getExtras();
            if (extras != null) {
                Log.i(TAG, "extras：" + extras);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.i(TAG, "Text：" + info.getHintText());
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                AccessibilityNodeInfo child = info.getChild(i);
                if (child != null) {
                    recycle(info.getChild(i));
                }
            }
        }
    }
}
