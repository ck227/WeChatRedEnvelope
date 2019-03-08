package com.ck.wechatredenvelope.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * 监听的服务类
 *
 * @author ck
 * @date 2019/3/8
 */
public class ListenWeChatService extends AccessibilityService {

    private String TAG = getClass().getName();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.e(TAG, "onAccessibilityEvent");
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "onInterrupt");
    }
}
