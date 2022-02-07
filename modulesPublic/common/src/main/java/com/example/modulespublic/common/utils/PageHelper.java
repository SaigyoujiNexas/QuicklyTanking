package com.example.modulespublic.common.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PageHelper {
    public static Fragment addFragment(FragmentManager fm, Fragment fragment,
                                       int container){
        if (!fragment.isAdded()) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(container, fragment).commit();
        }
        return fragment;
    }

    public static Fragment showFragment(FragmentManager fm, Fragment from, Fragment to, int contentLayout) {
        if (from == null && to != null) {
            return addFragment(fm, to, contentLayout);
        }
        if (from == null ||
                to == null || from == to) return from;
        FragmentTransaction transaction = fm.beginTransaction();
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            transaction.hide(from).add(contentLayout, to).commit();
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        return to;
    }
}
