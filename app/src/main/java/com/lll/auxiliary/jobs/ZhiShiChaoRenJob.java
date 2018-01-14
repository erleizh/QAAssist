package com.lll.auxiliary.jobs;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lll.auxiliary.SearchEngines;
import com.lll.auxiliary.bean.Topic;
import com.lll.auxiliary.service.QAAssistService;
import com.lll.auxiliary.utils.Log;

import java.util.List;

/**
 * Created by lll on 2018/1/13.
 * 芝士超人
 * @version 1.0 OK
 */
public class ZhiShiChaoRenJob extends BaseQAAssistJob  {

    private static final String PACKAGE_NAME = "com.inke.trivia";

    @Override
    public String getTargetPackageName() {
        return PACKAGE_NAME;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onEvent(QAAssistService service, AccessibilityEvent event) {
        super.onEvent(service, event);
        AccessibilityNodeInfo window = service.getRootInActiveWindow();
        if(window == null)return;
        List<String> question = getTextByViewId(window, "2131624297");//Q
        if (question == null || question.isEmpty()) return;
        Topic topic = new Topic();
        for (String q : question) {
            Log.e(TAG, "question : " + q);
            topic.setQuestion(q);
        }
        List<String> answer = getTextByViewId(window, "2131624288");//A
        if (answer == null) return;
        for (String a : answer) {
            Log.e(TAG, "answer : " + a);
            topic.addAnswer(a);
        }
        SearchEngines.search(service.getBaseContext(), topic);
    }
}
