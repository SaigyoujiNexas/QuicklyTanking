package com.example.modulescore.main;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//更多的是为自定义view provider提供思路。
//view provider必须要实现IViewProvider接口，
//唯一注意的是IViewProvider本身是泛型的，所以我们需要提供item视图对应的数据类型
//就是ScrollPickerAdapterHolder
public class DefaultItemViewProvider implements IViewProvider<String> {
    TextView target;

    public DefaultItemViewProvider(TextView target) {
        this.target = target;
    }

    @Override
    public int resLayout() {
        return R.layout.scroll_picker_default_item_layout;
    }

    @Override
//就是对应于adapter中的onBindView，在这里主要是进行视图初始化，并设置了textview的一些属性，
// 比如字体大小
    public void onBindView(@NonNull View view, @Nullable String text) {
//textview设置的字体大小应该是你期望的最大的字体大小，比如，如果想要被选中的item字体大小是18sp，
// 而未选中的item字体大小是16sp，那么这里应该设置最大的18sp；
        TextView tv = view.findViewById(R.id.tv_content);
        tv.setText(text);
//我们设置了view的tag（即 view.setTag(text);
//传入的是与item视图对应的数据，这么做是因为我们前面通过adapter对外暴露的监听接口，无论是onClick接口
// 还是onScroll接口，其回调数据都是item视图，并没有item对应的具体data数据，
//所以这里通过将item对应的数据设置为视图tag的方法，来进行数据传递，
//这样就可以通过getTag获取到对应的item数据了。
        view.setTag(text);
        tv.setTextSize(30);

    }

    //ScrollPickerView滚动时调用
    @Override
    public void updateView(@NonNull View itemView, boolean isSelected) {
        TextView tv = itemView.findViewById(R.id.tv_content);
        View line = itemView.findViewById(R.id.tv_line);
        tv.setTextSize(isSelected ? 30 : 30);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv.setTextColor(Color.parseColor(isSelected ? "#F5F5F5" : "#999999"));
        if(tv.getText().equals("")) {
            line.setVisibility(View.INVISIBLE);
        }else {
            Log.d("updateView",tv.getText()+",,");
            line.setVisibility(View.VISIBLE);
            if(isSelected)  target.setText(tv.getText());
        }
    }
}
