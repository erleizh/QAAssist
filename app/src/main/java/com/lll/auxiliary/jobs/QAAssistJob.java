package com.lll.auxiliary.jobs;

import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.lll.auxiliary.service.QAAssistService;

/**
 * Created by lll on 2018/1/13.
 * 答题辅助
 */
public interface QAAssistJob {

    void onCreate();

    void onKeyEvent(KeyEvent event);

    void onConnected();

    String getTargetPackageName();

    boolean isEnable();

    void onEvent(QAAssistService service, AccessibilityEvent event);

    void onDestroy();

    void onInterrupt();
}
