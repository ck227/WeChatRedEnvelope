package com.ck.wechatredenvelope.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.ck.wechatredenvelope.accessbility.IAccessbility;
import com.ck.wechatredenvelope.accessbility.WechatAccessbility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 监听的服务类
 *
 * @author ck
 * @date 2019/3/8
 */
public class ListenWeChatService extends AccessibilityService {

    private String TAG = getClass().getName();
    private List<IAccessbility> mAccessbilityTasks;
    private HashMap<String, IAccessbility> mAccessbilityTaskMap;

    @Override
    public void onCreate() {
        super.onCreate();
        mAccessbilityTasks = new ArrayList<>();
        mAccessbilityTaskMap = new HashMap<>(1);
        try {
            IAccessbility task = WechatAccessbility.class.newInstance();
            task.onCreateTask(this);
            mAccessbilityTasks.add(task);
            mAccessbilityTaskMap.put(task.getTargetPackageName(), task);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.e(TAG, "onAccessibilityEvent");
        String packageName = String.valueOf(accessibilityEvent.getPackageName());
        for (IAccessbility task : mAccessbilityTasks) {
            if (packageName.equals(task.getTargetPackageName())) {
                task.onReceiveTask(accessibilityEvent);
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt");
    }
}
