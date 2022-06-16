package com.xupt.modulescore.main.Target;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//支持任意数据类型，所以adapter就必须要泛型化。
public class ScrollPickerAdapter<T> extends RecyclerView.Adapter<ScrollPickerAdapter
        .ScrollPickerAdapterHolder> implements IPickerViewOperation {
    private List<T> mDataList;
    private Context mContext;
    private OnClickListener mOnItemClickListener;
    private OnScrollListener mOnScrollListener;
    private int mSelectedItemOffset;
    private int mVisibleItemNum = 3;
    private IViewProvider mViewProvider;
    private int mLineColor;
    private int maxItemH = 0;
    private int maxItemW = 0;
    private TextView topTarget;
    private ScrollPickerAdapter(Context context, TextView topTarget) {
        mContext = context;
        mDataList = new ArrayList<>();
        this.topTarget = topTarget;
    }

    @NonNull
    @Override
    public ScrollPickerAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//返回一个viewHolder，这里我们直接返回了ScrollPickerAdapterHolder
        if (mViewProvider == null) {
            mViewProvider = new ZPItemViewProvider(mContext,topTarget);
        }
        return new ScrollPickerAdapterHolder(LayoutInflater.from(mContext).inflate(mViewProvider.resLayout(), parent, false));
    }

    @Override
//onBindViewHolder本身的功能就是完成holder视图和数据的绑定
// 因为允许用户自定义item视图，直接委托给view provider进行实现。
    public void onBindViewHolder(@NonNull ScrollPickerAdapterHolder holder, int position) {
        mViewProvider.onBindView(holder.itemView, mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getSelectedItemOffset() {
        return mSelectedItemOffset;
    }

    @Override
    public int getVisibleItemNumber() {
        return mVisibleItemNum;
    }

    @Override
    public int getLineColor() {
        return mLineColor;
    }

    @Override
//在ScrollPickerView滚动的时候，及时通知外界。updateView包括两个入参：
// 一个是当前的item视图，一个是标识当前item视图是否在两条线中。
// 外界拿到这两个参数后可以根据自己的需求来定制化，比如将选中的item视图文字变大、变色等。
    public void updateView(View itemView, boolean isSelected) {
//调用用户提供的view provider的updateView，即通知对方视图要更新。
//我们提供了一个滚动监听，滚动监听的方法入口是onScrolled，该方法有一个参数currentItemView，表示当前被选中的item视图。
//在updateView方法中，调用了 adaptiveItemViewSize(itemView)方法，这个方法的目的是保证item视图的最小高度和宽度，
        mViewProvider.updateView(itemView, isSelected);
        adaptiveItemViewSize(itemView);
        if (isSelected && mOnScrollListener != null) {
            mOnScrollListener.onScrolled(itemView);
        }
//对于滚动选择器中的item视图，提供了一个默认的点击事件，这个事件只有在item视图被选中的时候才有。
// 其实用户完全可以在自己的view provider中，通过updateView来完成业务逻辑处理，
// 这里只是通过adapter对外暴露一个监听响应入口，能满足基本需要。
        itemView.setOnClickListener(isSelected ? new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onSelectedItemClicked(v);
                }
            }
        } : null);
    }

    //目的是保证item视图的最小高度和宽度
    private void adaptiveItemViewSize(View itemView) {
        int h = itemView.getHeight();
        if (h > maxItemH) {
            maxItemH = h;
        }

        int w = itemView.getWidth();
        if (w > maxItemW) {
            maxItemW = w;
        }

        itemView.setMinimumHeight(maxItemH);
        itemView.setMinimumWidth(maxItemW);
    }

    static class ScrollPickerAdapterHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ScrollPickerAdapterHolder(@NonNull View view) {
            super(view);
            itemView = view;
        }
    }

    public interface OnClickListener {
        void onSelectedItemClicked(View v);
    }

    public interface OnScrollListener {
        void onScrolled(View currentItemView);
    }

    public static class ScrollPickerAdapterBuilder<T> {
        private ScrollPickerAdapter mAdapter;

        public ScrollPickerAdapterBuilder(Context context,TextView toptarget) {
            mAdapter = new ScrollPickerAdapter<T>(context,toptarget);
        }

        public ScrollPickerAdapterBuilder<T> selectedItemOffset(int offset) {
            mAdapter.mSelectedItemOffset = offset;
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setDataList(List<T> list) {
            mAdapter.mDataList.clear();
            mAdapter.mDataList.addAll(list);
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setOnClickListener(OnClickListener listener) {
            mAdapter.mOnItemClickListener = listener;
            return this;
        }

        public ScrollPickerAdapterBuilder<T> visibleItemNumber(int num) {
            mAdapter.mVisibleItemNum = num;
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setItemViewProvider(IViewProvider viewProvider) {
            mAdapter.mViewProvider = viewProvider;
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setDivideLineColor(String colorString) {
            mAdapter.mLineColor = Color.parseColor(colorString);
            return this;
        }

        public ScrollPickerAdapterBuilder<T> setOnScrolledListener(OnScrollListener listener) {
            mAdapter.mOnScrollListener = listener;
            return this;
        }

        public ScrollPickerAdapter build() {
            adaptiveData(mAdapter.mDataList);
            mAdapter.notifyDataSetChanged();
            return mAdapter;
        }

        private void adaptiveData(List list) {
        //该方法的功能是用于数据填充，比如两条分割线偏移量为n个item视图，
        //那么我们就需要在其前面补充n个item视图，这样才能保证能有机会选中所有的item视图
            String TAG = "adaptiveData_tag";
            int visibleItemNum = mAdapter.mVisibleItemNum;
            int selectedItemOffset = mAdapter.mSelectedItemOffset;
            for (int i = 0; i < mAdapter.mSelectedItemOffset; i++) {
                list.add(0, null);//在滚动器前面增加数据，item数据值为空
            }
            Log.d(TAG,list.size()-selectedItemOffset+"");
            for (int i = 0; i < visibleItemNum - selectedItemOffset - 2; i++) {
                list.add(null);//在滚动器后面增加数据，item数据值为空
                Log.d(TAG,visibleItemNum - selectedItemOffset - 2+","+visibleItemNum+","+selectedItemOffset);
            }
        }
    }


}
