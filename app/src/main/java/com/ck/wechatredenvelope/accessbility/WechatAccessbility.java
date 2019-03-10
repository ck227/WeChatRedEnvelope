package com.ck.wechatredenvelope.accessbility;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.ck.wechatredenvelope.service.ListenWeChatService;
import com.ck.wechatredenvelope.util.AccessibilityHelper;

import java.util.List;

/**
 * Demo Class
 *
 * @author ck
 * @date 2019/3/8
 */
public class WechatAccessbility extends BaseAccessbility {

    private static final String TAG = "ccckkk";
    public static final String WECHAT_PACKAGENAME = "com.tencent.mm";
    private static final String HONGBAO_TEXT_KEY = "[微信红包]";
    private static final String BUTTON_CLASS_NAME = "android.widget.Button";

    private static final int WINDOW_NONE = 0;
    private static final int WINDOW_REDENVELOPE_RECEIVEUI = 1;
    private static final int WINDOW_REDENVELOPE_DETAIL = 2;
    private static final int WINDOW_LAUNCHER = 3;
    private static final int WINDOW_OTHER = -1;

    private int mCurrentWindow = WINDOW_NONE;

    private boolean isReceivingRedEnvelope = false;
    private Handler mHandler = null;

    @Override
    public void onCreateTask(ListenWeChatService service) {
        super.onCreateTask(service);
    }

    @Override
    public String getTargetPackageName() {
        return WECHAT_PACKAGENAME;
    }

    @Override
    public void onReceiveTask(AccessibilityEvent event) {
        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {

        } else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            openRedEnvelop(event);
        } else if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            //不在聊天界面或聊天列表，不处理
            if (mCurrentWindow != WINDOW_LAUNCHER) {
                return;
            }
            if (isReceivingRedEnvelope) {
                handleChatListHongBao();
            }
        }
    }

    private void openRedEnvelop(AccessibilityEvent event) {
        if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyNotHookReceiveUI".equals(event.getClassName())) {
            mCurrentWindow = WINDOW_REDENVELOPE_RECEIVEUI;
            handleLuckyMoneyReceive();
        } else if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI".equals(event.getClassName())) {
            //红包结果页面
            mCurrentWindow = WINDOW_REDENVELOPE_DETAIL;
        } else if ("com.tencent.mm.ui.chatting.ChattingUI".equals(event.getClassName())) {
            mCurrentWindow = WINDOW_LAUNCHER;
            //在聊天界面,去点中红包
            handleChatListHongBao();
        } else {
            mCurrentWindow = WINDOW_OTHER;
        }
    }

    /**
     * 点击红包
     */
    private void handleLuckyMoneyReceive() {
        AccessibilityNodeInfo nodeInfo = getService().getRootInActiveWindow();
        if (nodeInfo == null) {
            Log.w(TAG, "rootWindow为空");
            return;
        }
        AccessibilityNodeInfo targetNode = null;

        String buttonId = "com.tencent.mm:id/b43";
        targetNode = AccessibilityHelper.findNodeInfosById(nodeInfo, buttonId);
        if (targetNode == null) {
            //分别对应固定金额的红包 拼手气红包
            AccessibilityNodeInfo textNode = AccessibilityHelper.findNodeInfosByTexts(nodeInfo, "发了一个红包", "给你发了一个红包", "发了一个红包，金额随机");

            if (textNode != null) {
                for (int i = 0; i < textNode.getChildCount(); i++) {
                    AccessibilityNodeInfo node = textNode.getChild(i);
                    if (BUTTON_CLASS_NAME.equals(node.getClassName())) {
                        targetNode = node;
                        break;
                    }
                }
            }
        }
        //通过组件查找
        if (targetNode == null) {
            targetNode = AccessibilityHelper.findNodeInfosByClassName(nodeInfo, BUTTON_CLASS_NAME);
        }
        if (targetNode != null) {
            final AccessibilityNodeInfo n = targetNode;
            long sDelayTime = 500;
            if (sDelayTime != 0) {
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AccessibilityHelper.performClick(n);
                    }
                }, sDelayTime);
            } else {
                AccessibilityHelper.performClick(n);
            }
        }
    }

    /**
     * 拆红包
     */
    private void handleChatListHongBao() {
        AccessibilityNodeInfo nodeInfo = getService().getRootInActiveWindow();
        if(nodeInfo == null) {
            Log.e(TAG, "rootWindow为空");
            return;
        }
//        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("领取红包");
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aou");
        if(list != null && list.isEmpty()) {
            // 从消息列表查找红包
            AccessibilityNodeInfo node = AccessibilityHelper.findNodeInfosByText(nodeInfo, "[微信红包]");
            if(node != null) {
                isReceivingRedEnvelope = true;
                AccessibilityHelper.performClick(nodeInfo);
            }
        } else if(list != null) {
            if (isReceivingRedEnvelope){
                //领最新的红包
                AccessibilityNodeInfo node = list.get(list.size() - 1);
                AccessibilityHelper.performClick(node);
                isReceivingRedEnvelope = false;
            }
        }
    }


    @Override
    public void onStopTask() {

    }

    private Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }
}
