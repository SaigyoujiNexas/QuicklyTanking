package com.xupt.modulescore.main.Target;

import android.view.View;

//虽然可以在ScrollPickerView中获取到adapter进而获取到外部设置的定制数据，
//但显然获取这些数据的行为并不属于adapter，且用户也有可能自定义adapter，
// 因此我们需要抽象出一个接口来解耦adapter，
public interface IPickerViewOperation {
    int getSelectedItemOffset();//获取选中item的偏移量

    int getVisibleItemNumber();//获取可见item的数目

    int getLineColor();//获取分割线的颜色

    void updateView(View itemView, boolean isSelected);//滚动的过程中更新视图
}
