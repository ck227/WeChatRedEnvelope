package com.ck.wechatredenvelope.accessbility;

import android.view.accessibility.AccessibilityEvent;

/**
 * Demo Class
 *
 * @author ck
 * @date 2019/3/8
 */
public class WechatAccessbility extends BaseAccessbility{

    @Override
    public String getTargetPackageName() {
        return null;
    }

    @Override
    public void onReceiveTask(AccessibilityEvent event) {

    }

    @Override
    public void onStopTask() {

    }
}
