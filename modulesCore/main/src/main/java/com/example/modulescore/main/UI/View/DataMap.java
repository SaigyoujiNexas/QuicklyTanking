package com.example.modulescore.main.UI.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.TextureMapView;

public class DataMap extends TextureMapView {

    public DataMap(Context context) {
        super(context);
    }

    public DataMap(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DataMap(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public DataMap(Context context, AMapOptions aMapOptions) {
        super(context, aMapOptions);
    }

    int mLastX = 0;
    int mLastY = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        final String TAG = "onInterceptTouchEvent_Map";
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //接到ACTION_DOWN开始取消父容器的事件拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) < 65) {
                    Log.d(TAG, "direction<50");
                } else {
                    Log.d(TAG, "direction>=50");
                    getParent().requestDisallowInterceptTouchEvent(false); //取消父容器的拦截。
                }
        }
        mLastX = x;
        mLastY = y;
        return super.onInterceptTouchEvent(ev);
    }
}

