package com.lll.auxiliary.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.lll.auxiliary.SearchEngines;
import com.lll.auxiliary.bean.Topic;
import com.lll.auxiliary.jobs.BaiWanYingXiongJob;
import com.lll.auxiliary.jobs.ChongDingDaHuiJob;
import com.lll.auxiliary.jobs.HuaJiaoZhiBoJob;
import com.lll.auxiliary.jobs.QAAssistJob;
import com.lll.auxiliary.jobs.ZhiShiChaoRenJob;
import com.lll.auxiliary.utils.Log;

import java.util.ArrayList;
import java.util.List;

import static android.view.accessibility.AccessibilityEvent.eventTypeToString;


public class QAAssistService extends AccessibilityService {
    public static final String TAG = "QAAuxiliary";

    public static final ArrayList<Class<? extends QAAssistJob>> JOBS = new ArrayList<>();
    public final ArrayList<QAAssistJob> mQAAssistJobs = new ArrayList<>();
    private static QAAssistService sService = null;

    static {
        register(ChongDingDaHuiJob.class);
        register(ZhiShiChaoRenJob.class);
        register(BaiWanYingXiongJob.class);
        register(HuaJiaoZhiBoJob.class);
    }

    public static void register(Class<? extends QAAssistJob> job) {
        JOBS.add(job);
    }

    @SuppressWarnings("unused")
    public static void unregister(Class<? extends QAAssistJob> job) {
        JOBS.remove(job);
    }

    @Override

    public void onCreate() {
        super.onCreate();
        Toast.makeText(getBaseContext(), "onCreate", Toast.LENGTH_SHORT).show();
        for (Class<? extends QAAssistJob> job : JOBS) {
            QAAssistJob instance;
            try {
                instance = job.newInstance();
                mQAAssistJobs.add(instance);
                instance.onCreate();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i(TAG, "event:" + eventTypeToString(event.getEventType()));
        Log.i(TAG, "event:" + event.getPackageName());
        for (QAAssistJob qaAssistJob : mQAAssistJobs) {
            if (qaAssistJob.isEnable() && (TextUtils.equals(event.getPackageName(), qaAssistJob.getTargetPackageName())
                    || event.getPackageName() == null || TextUtils.equals(event.getPackageName(), "com.android.systemui"))) {
                try {
                    qaAssistJob.onEvent(sService, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 判断当前服务是否正在运行
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("unused")
    public static boolean isRunning() {
        if (sService == null) return false;
        AccessibilityManager accessibilityManager = (AccessibilityManager) sService.getSystemService(Context.ACCESSIBILITY_SERVICE);
        AccessibilityServiceInfo info = sService.getServiceInfo();
        if (info == null || accessibilityManager == null) return false;
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo i : list) {
            if (i.getId().equals(info.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {
        Log.i(TAG, "onInterrupt");
        for (QAAssistJob qaAssistJob : mQAAssistJobs) {
            qaAssistJob.onInterrupt();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (QAAssistJob qaAssistJob : mQAAssistJobs) {
            try {
                qaAssistJob.onDestroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mQAAssistJobs.clear();
        sService = null;
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        sService = this;
        Toast.makeText(getBaseContext(), "onServiceConnected", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onServiceConnected");
        for (QAAssistJob qaAssistJob : mQAAssistJobs) {
            qaAssistJob.onConnected();
        }
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SearchEngines.search(getBaseContext(), new Topic("世界上最好的语言是什么"));
            Toast.makeText(getBaseContext(), "搜索测试", Toast.LENGTH_SHORT).show();
        }, 3000);
    }

    @Override
    protected boolean onGesture(int gestureId) {
        Log.i(TAG, "onGesture");
        return super.onGesture(gestureId);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        for (QAAssistJob qaAssistJob : mQAAssistJobs) {
            qaAssistJob.onKeyEvent(event);
        }
        return super.onKeyEvent(event);
    }
}
