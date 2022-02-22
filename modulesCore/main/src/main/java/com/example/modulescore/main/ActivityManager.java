package com.example.modulescore.main;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;

public class ActivityManager {
    private static ActivityManager instance;
    private static LinkedList<Activity> activityStack;//activity栈

    private ActivityManager() {
    }
    //懒汉模式（线程不安全）
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    //把一个activity压入栈中
    public static void pushOneActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new LinkedList<Activity>();
        }
        activityStack.addLast(actvity);
        Log.d("ActivityManager ", "size = " + activityStack.size());
    }
    //获取栈顶的activity，先进后出原则
    public static Activity getLastActivity() {
        return activityStack.getLast();
    }
    //移除一个activity
    public static void popOneActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
            }
        }
    }
    //退出所有activity
    public static void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null) break;
                popOneActivity(activity);
            }
        }
    }
}
