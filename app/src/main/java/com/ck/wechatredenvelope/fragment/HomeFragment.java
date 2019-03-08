package com.ck.wechatredenvelope.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 首页
 *
 * @author ck
 * @date 2019/3/8
 */
public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
