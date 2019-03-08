package com.ck.wechatredenvelope.accessbility;

import com.ck.wechatredenvelope.service.ListenWeChatService;

/**
 * Demo Class
 *
 * @author ck
 * @date 2019/3/8
 */
public abstract class BaseAccessbility implements IAccessbility{

    private ListenWeChatService service;

    @Override
    public void onCreateTask(ListenWeChatService service) {
        this.service = service;
    }

    public ListenWeChatService getService(){
        return service;
    }
}
