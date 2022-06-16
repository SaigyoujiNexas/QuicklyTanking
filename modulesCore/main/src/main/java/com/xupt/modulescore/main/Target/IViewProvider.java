package com.xupt.modulescore.main.Target;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//要滚动选择器支持各种各样自定义item视图，保证自定义的item视图能适配ScrollPickerView，
//又要保证自定义的item视图逻辑可以在滚动选择器这个框架下工作,
//最后将自定义的视图加载到ScrollPickerView中。

public interface IViewProvider<T> {//视图提供接口，用于提供自定义的item视图

    @LayoutRes//应该传入一个Layout的资源
    int resLayout();//获取布局文件,类似于R.layout.xxx

    //对应于adapter中的onBindView
    void onBindView(@NonNull View view, @Nullable T itemData);
    //当ScrollPickerView滚动的时候通知视图进行更新
    void updateView(@NonNull View itemView, boolean isSelected);
}
//setAdapter的时候就会引起视图的重新绘制，因此我们直接将adapter以及定制化数据构造完成后再进行setAdapter。
// 这里显然是build设计模式使用的绝佳场景，所以我们为ScrollPickerAdapter提供一个构造器，
// 与此同时将ScrollPickerAdapter的构造方法标识为私有方法，
// 避免外部再通过ScrollPickerAdapter进行数据设置。
