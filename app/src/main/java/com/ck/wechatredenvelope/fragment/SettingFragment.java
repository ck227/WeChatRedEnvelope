package com.ck.wechatredenvelope.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 设置
 *
 * @author ck
 * @date 2019/3/8
 */
public class SettingFragment extends Fragment {

    public static SettingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
