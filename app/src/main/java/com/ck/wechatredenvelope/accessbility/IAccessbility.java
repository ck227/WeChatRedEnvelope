package com.ck.wechatredenvelope.accessbility;

import android.view.accessibility.AccessibilityEvent;

import com.ck.wechatredenvelope.service.ListenWeChatService;

/**
 * 辅助接口类
 *
 * @author ck
 * @date 2019/3/8
 */
public interface IAccessbility {

    String getTargetPackageName();

    void onCreateTask(ListenWeChatService service);

    void onReceiveTask(AccessibilityEvent event);

    void onStopTask();
}
