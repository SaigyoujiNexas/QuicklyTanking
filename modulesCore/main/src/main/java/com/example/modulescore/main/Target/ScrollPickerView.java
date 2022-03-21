package com.example.modulescore.main.Target;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modulescore.main.R;
import com.example.modulescore.main.Util.ScreenUtil;

public class ScrollPickerView extends RecyclerView {
//
//    private int mInitialY;
//    private Runnable mSmoothScrollTask;
//    //自定义控件要用于xml中必须实现包含有AttributeSet类型入参的构造方法
//    //将参数少的构造方法委托给了参数多的构造方法
//    public ScrollPickerView(@NonNull Context context) {
//        this(context,null);
//    }
//
//    public ScrollPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs,0);
//    }
//
//    public ScrollPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initTask();
//    }
//
//    //在ScrollPickerView滚动结束后调整item视图的位置，使得被选中的item视图刚好位于两条分割线中。
//    private void initTask() {
////为了完成被选中item视图位置调整的功能，因为我们要保证使被选中的视图刚好停在两条分割线的中间。
//        mSmoothScrollTask = new Runnable() {
//            @Override
//            public void run() {
//                int newY = getScrollYDistance();
////mInitialY就是在ScrollPickerView滚动结束后通过getScrollYDistance获取的值，而newY也是通过getScrollYDistance获取的值，只不过是在mSmoothScrollTask刚开始
//// 执行的时候获取的，这个比较是为了处理在mSmoothScrollTask刚要执行的时候用户又突然滑动的状况，
////直接结束当前任务，然后再触发一次mSmoothScrollTask任务即可。
//                if (mInitialY != newY) {
//                    mInitialY = getScrollYDistance();
//                    postDelayed(mSmoothScrollTask, 30);
//                } else if (mItemHeight > 0) {
//                    final int offset = mInitialY % mItemHeight;//离选中区域中心的偏移量
////获取被选中item视图偏离两条分割线中间的偏移量offset，如果offset==0，表明刚好落在两条分割线中间，则无需调整。
//                    if (offset == 0) {
//                        return;
//                    }
////如果offset >= mItemHeight / 2，则表示此时被选中的item视图（称之为itemA）滚动到了两条分割线中间点的下方，
//// 其趋势是向下滚动，所以我们就继续使其向下滚动，滚动距离为mItemHeight - offset，
//// 刚好使得itemA上面的item视图滚动到两条分割线中间，作为被选中item视图。
//                    if (offset >= mItemHeight / 2) {//滚动区域超过了item高度的1/2，调整position的值
//                        Log.d("SmoothScrollTask1", String.valueOf(mItemHeight - offset));
//                        smoothScrollBy(0, mItemHeight - offset);
////如果offset < mItemHeight / 2，则表示此时被选中的item视图（称之为itemA）滚动到了两条分割线的上半部分，
////其趋势是无法再继续向下滚动，而是有回弹的迹象，所以此时我们只需要回退offset个距离即可，
//// 这样就等于将itemA的下一个item视图滚动到了两条分割线中间。
//                    } else if (offset < mItemHeight / 2) {
//                        Log.d("SmoothScrollTask2", String.valueOf(- offset));
//                        smoothScrollBy(0, -offset);
//                    }
//                }
//            }
//        };
//    }
//
//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        initPaint();
//    }
//    Paint mBgPaint;
//    //画笔颜色的设置
//    private void initPaint() {
//        if (mBgPaint == null) {
//            mBgPaint = new Paint();
//            mBgPaint.setColor(getLineColor());
//            mBgPaint.setStrokeWidth(ScreenUtil.dpToPx(1f));
//        }
//    }
//    private int mItemWidth;
//
//    private int mItemHeight;
//
//    private int mFirstLineY;
//
//    private int mSecondLineY;
//
//    private boolean mFirstAmend;
//    @Override
//    //因为ScrollPickerView本身是个容器，这个容器会包含若干个item视图，
//    //所以如果我们想要保证容器大小合适，就必须自内而外的发起measure，
//    //也就是根据item视图来决定ScrollPickerView本身的宽高。
//    protected void onMeasure(int widthSpec, int heightSpec) {
////ScrollPickerView的宽高要由item视图来决定，而不能根据外界的设置决定，
//// 因为这样容易影响滚动选择器的整体效果，所以这里我们首先设置了MeasureSpec，
//// 将widthSpec设置成UNSPECIFIED，表示其宽度是不受限制的，而将heightSpec设置成AT_MOST
//// (可以理解为对应于wrap_content)表示会根item视图高度完成滚动选择器的高度测量。
//// 上面所说的宽度不受限制，实际上并不是任意的，因为代码后面会根据item的宽度完成测量。
//
//        widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//        heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
////调用了 super.onMeasure(widthSpec, heightSpec);因为原来父view传给我们的MeasureSpec并不是这样的。
//        super.onMeasure(widthSpec, heightSpec);
//
//        measureSize();
////最后我们根据测量到的item视图高度和宽度，完成测量设置
////getVisibleItemNumber()表示item视图的可见数目，同样由IPickerViewOperation提供。
//        setMeasuredDimension(mItemWidth, mItemHeight * getVisibleItemNumber());
//
//        initPaint();
//    }
//
//    private void measureSize() {
////核心思想就是获取item视图的高度和宽度，并完成两条分割线的Y坐标测绘，
//        if (getChildCount() > 0) {
//            if (mItemHeight == 0) {
//                mItemHeight = getChildAt(0).getMeasuredHeight();
//            }
//            if (mItemWidth == 0) {
//                mItemWidth = getChildAt(0).getMeasuredWidth();
//            }
////这里分割线Y坐标的测量方法需要结合被选中item视图的偏移量来完成，即第一条线的Y坐标就是
//// item视图的高度乘以被选中item视图的高度，而第二条只需要在增加一个itemHeight高度即可。
//            if (mFirstLineY == 0 || mSecondLineY == 0) {
//                mFirstLineY = mItemHeight * getItemSelectedOffset();
//                mSecondLineY = mItemHeight * (getItemSelectedOffset() + 1);
//            }
//        }
//    }
//
//    private int getVisibleItemNumber() {
//        IPickerViewOperation operation = (IPickerViewOperation) getAdapter();
//        if (operation != null) {
//            return operation.getVisibleItemNumber();
//        }
//        return 3;
//    }
//
//    private int getItemSelectedOffset() {
//        IPickerViewOperation operation = (IPickerViewOperation) getAdapter();
//        if (operation != null) {
//            return operation.getSelectedItemOffset();
//        }
//        return 1;
//    }
//    @Override
//    //首先我们完成了两条分割线的绘制，这也是复写onDraw方法的初衷，
//    // 因为容器相关的可以由android框架帮我们完成绘制，但是背后的两条分割线却只能我们自己来完成绘制。这个绘制很简单，就是根据onMeasure测量出来的两条分割线的Y坐标，完成绘制。
//    public void onDraw(Canvas c) {
//        super.onDraw(c);
//        doDraw(c);
//        //第一次onDraw的时候，我们进行了一次修正，这个修正就是根据用户设置的被选中item视图的偏移量进行的，
//        // 其目的就是滚动到用户选中的item视图位置。
//        if (!mFirstAmend) {
//            mFirstAmend = true;
//            ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(getItemSelectedOffset(), 0);
//        }
//    }
//
//    public void doDraw(Canvas canvas) {
//        if (mItemHeight > 0) {
//            int screenX = getWidth();
//            int startX = screenX / 2 - mItemWidth / 2 - ScreenUtil.dpToPx(5);
//            int stopX = mItemWidth + startX + ScreenUtil.dpToPx(5);
//
//            canvas.drawLine(startX, mFirstLineY, stopX, mFirstLineY, mBgPaint);
//            canvas.drawLine(startX, mSecondLineY, stopX, mSecondLineY, mBgPaint);
//        }
//    }
//    //获取外部设置的分割线颜色，正是通过上文中的IPickerViewOperation完成的，IPickerViewOperation接口的设计理念请参考上篇文章，getLineColor方法代码如下所示：
//    private int getLineColor() {
//        IPickerViewOperation operation = (IPickerViewOperation) getAdapter();
//        if (operation != null && operation.getLineColor() != 0) {
//            return operation.getLineColor();
//        }
//        return getResources().getColor(R.color.colorPrimary);
//    }
//    @Override
//    //从代码的角度来看滚动到两条分割线之间的item视图和其他item视图没有任何区别，
//    // 因为从代码的角度还无法标识该item视图是否在分割线中间，
//    // 即当前分割线和item视图是独立存在的。比如我要将被选中的item视图字体变成红色，就还无法做到。
//    public void onScrolled(int dx, int dy) {
//        super.onScrolled(dx, dy);
//        freshItemView();
//    }
//
//    private void freshItemView() {
////我们遍历了ScrollPickerView中可见的item视图，
//// 然后判断哪条item视图位于两条分割线之内。这里判断item视图是否在两条分割线之间的方法很简单，
//// 就是通过当前item视图在其父视图中的Y坐标是否在两条分割线之内进行判断的。
//        for (int i = 0; i < getChildCount(); i++) {
//            float itemViewY = getChildAt(i).getTop() + mItemHeight / 2;
//            updateView(getChildAt(i), mFirstLineY < itemViewY && itemViewY < mSecondLineY);
//        }
//    }
//    //将选中的视图及其被选中的状态传递了出去，实际上是通过adapter（IPickerViewOperation）来进行传递的
//    private void updateView(View itemView, boolean isSelected) {
//        IPickerViewOperation operation = (IPickerViewOperation) getAdapter();
//        if (operation != null) {
//            operation.updateView(itemView, isSelected);
//        }
//    }
//
//    @Override
////当ScrollPickerView滚动停止时，发现item视图会落在任意的位置
//// ，比如可能落在分割线上，也可能落在分割线内等等
////，即解决滚动结束后被选中的item视图必须要位于两条分割线的正中间，
//// 因此我们要在用户手指离开屏幕的时候进行调整，这就需要监听ACTION_UP事件，
//    public boolean onTouchEvent(MotionEvent e) {
//        if (e.getAction() == MotionEvent.ACTION_UP) {
//            processItemOffset();
//        }
//        return super.onTouchEvent(e);
//    }
//
//    //其调用了一个方法getScrollYDistance和启动了一个任务mSmoothScrollTask，
//    private void processItemOffset() {
//        mInitialY = getScrollYDistance();
//        postDelayed(mSmoothScrollTask, 30);
//        //mSmoothScrollTask任务就是我们在ScrollPickerView构造方法中进行初始化的任务
//    }
//
//    private int getScrollYDistance() {
//        LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
//        if (layoutManager == null) {//从代码可知这里adapter只能使用LinearLayoutManager作为布局管理者。
//            return 0;
//        }
//        int position = layoutManager.findFirstVisibleItemPosition();
////根据容器中第一条可见的item视图来算出ScrollPickerView在Y轴滚动的距离，
//        View firstVisibleChildView = layoutManager.findViewByPosition(position);
//        if (firstVisibleChildView == null) {
//            return 0;
//        }
//        int itemHeight = firstVisibleChildView.getHeight();
//        //getTop()是View顶部在y轴坐标
//        Log.d("getScrollYDistance",(position) * itemHeight - firstVisibleChildView.getTop()+","+
//                (position) * itemHeight +","+firstVisibleChildView.getTop()+","+position+","+itemHeight);
//        //从第0个到第一个能看见的高+第一个能看见的到父容器的高
//        return (position) * itemHeight - firstVisibleChildView.getTop();
//    }

    private Runnable mSmoothScrollTask;
    private Paint mBgPaint;
    private int mItemHeight;
    private int mItemWidth;
    private int mInitialY;
    private int mFirstLineY;
    private int mSecondLineY;
    private boolean mFirstAmend;
    //自定义控件要用于xml中必须实现包含有AttributeSet类型入参的构造方法
    //将参数少的构造方法委托给了参数多的构造方法
    public ScrollPickerView(@NonNull Context context) {
        this(context, null);
    }

    public ScrollPickerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollPickerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTask();
    }

    public void doDraw(Canvas canvas) {
        if (mItemHeight > 0) {
            int screenX = getWidth();
            int startX = screenX / 2 - mItemWidth / 2 - ScreenUtil.dpToPx(5);
            int stopX = mItemWidth + startX + ScreenUtil.dpToPx(5);

            canvas.drawLine(startX, mFirstLineY, stopX, mFirstLineY, mBgPaint);
            canvas.drawLine(startX, mSecondLineY, stopX, mSecondLineY, mBgPaint);
        }
    }

    @Override
    //首先我们完成了两条分割线的绘制，这也是复写onDraw方法的初衷，
    // 因为容器相关的可以由android框架帮我们完成绘制，但是背后的两条分割线却只能我们自己来完成绘制。这个绘制很简单，就是根据onMeasure测量出来的两条分割线的Y坐标，完成绘制。
    public void onDraw(Canvas c) {
        super.onDraw(c);
        doDraw(c);
        //第一次onDraw的时候，我们进行了一次修正，这个修正就是根据用户设置的被选中item视图的偏移量进行的，
        // 其目的就是滚动到用户选中的item视图位置。
        if (!mFirstAmend) {
            mFirstAmend = true;
            ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(getItemSelectedOffset(), 0);
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        String TAG = "ScrollViewScrolledTAG";
        Log.d(TAG,"");
        freshItemView();
    }

    //画笔颜色的设置
    private void initPaint() {
        if (mBgPaint == null) {
            mBgPaint = new Paint();
            mBgPaint.setColor(getLineColor());
            mBgPaint.setStrokeWidth(ScreenUtil.dpToPx(1f));
        }
    }

    private int getScrollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
        if (layoutManager == null) {//从代码可知这里adapter只能使用LinearLayoutManager作为布局管理者。
            return 0;
        }
        int position = layoutManager.findFirstVisibleItemPosition();
//根据容器中第一条可见的item视图来算出ScrollPickerView在Y轴滚动的距离，
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        if (firstVisibleChildView == null) {
            return 0;
        }
        int itemHeight = firstVisibleChildView.getHeight();
        //getTop()是View顶部在y轴坐标
        Log.d("getScrollYDistance",(position) * itemHeight - firstVisibleChildView.getTop()+","+
                (position) * itemHeight +","+firstVisibleChildView.getTop()+","+position+","+itemHeight);
        //从第0个到第一个能看见的高+第一个能看见的到父容器的高
        return (position) * itemHeight - firstVisibleChildView.getTop();
    }

        @Override
//当ScrollPickerView滚动停止时，发现item视图会落在任意的位置
// ，比如可能落在分割线上，也可能落在分割线内等等
//，即解决滚动结束后被选中的item视图必须要位于两条分割线的正中间，
// 因此我们要在用户手指离开屏幕的时候进行调整，这就需要监听ACTION_UP事件，
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            processItemOffset();
        }
        return super.onTouchEvent(e);
    }

    //其调用了一个方法getScrollYDistance和启动了一个任务mSmoothScrollTask，
    private void processItemOffset() {
        mInitialY = getScrollYDistance();
        postDelayed(mSmoothScrollTask, 30);
        //mSmoothScrollTask任务就是我们在ScrollPickerView构造方法中进行初始化的任务
    }

    @Override
    //因为ScrollPickerView本身是个容器，这个容器会包含若干个item视图，
    //所以如果我们想要保证容器大小合适，就必须自内而外的发起measure，
    //也就是根据item视图来决定ScrollPickerView本身的宽高。
    protected void onMeasure(int widthSpec, int heightSpec) {
//ScrollPickerView的宽高要由item视图来决定，而不能根据外界的设置决定，
// 因为这样容易影响滚动选择器的整体效果，所以这里我们首先设置了MeasureSpec，
// 将widthSpec设置成UNSPECIFIED，表示其宽度是不受限制的，而将heightSpec设置成AT_MOST
// (可以理解为对应于wrap_content)表示会根item视图高度完成滚动选择器的高度测量。
// 上面所说的宽度不受限制，实际上并不是任意的，因为代码后面会根据item的宽度完成测量。

        widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//调用了 super.onMeasure(widthSpec, heightSpec);因为原来父view传给我们的MeasureSpec并不是这样的。
        super.onMeasure(widthSpec, heightSpec);

        measureSize();
//最后我们根据测量到的item视图高度和宽度，完成测量设置
//getVisibleItemNumber()表示item视图的可见数目，同样由IPickerViewOperation提供。
        setMeasuredDimension(mItemWidth, mItemHeight * getVisibleItemNumber());

        initPaint();
    }
    private void measureSize() {
//核心思想就是获取item视图的高度和宽度，并完成两条分割线的Y坐标测绘，
        if (getChildCount() > 0) {
            if (mItemHeight == 0) {
                mItemHeight = getChildAt(0).getMeasuredHeight();
                Log.d("measureSize_height", String.valueOf(mItemHeight));
            }
            if (mItemWidth == 0) {
                mItemWidth = getChildAt(0).getMeasuredWidth();
            }
//这里分割线Y坐标的测量方法需要结合被选中item视图的偏移量来完成，即第一条线的Y坐标就是
// item视图的高度乘以被选中item视图的高度，而第二条只需要在增加一个itemHeight高度即可。
            if (mFirstLineY == 0 || mSecondLineY == 0) {
                mFirstLineY = mItemHeight * getItemSelectedOffset();
                mSecondLineY = mItemHeight * (getItemSelectedOffset() + 1);
            }
        }
    }

        //在ScrollPickerView滚动结束后调整item视图的位置，使得被选中的item视图刚好位于两条分割线中。
    private void initTask() {
//为了完成被选中item视图位置调整的功能，因为我们要保证使被选中的视图刚好停在两条分割线的中间。
        mSmoothScrollTask = new Runnable() {
            @Override
            public void run() {
                int newY = getScrollYDistance();
//mInitialY就是在ScrollPickerView滚动结束后通过getScrollYDistance获取的值，而newY也是通过getScrollYDistance获取的值，只不过是在mSmoothScrollTask刚开始
// 执行的时候获取的，这个比较是为了处理在mSmoothScrollTask刚要执行的时候用户又突然滑动的状况，
//直接结束当前任务，然后再触发一次mSmoothScrollTask任务即可。
                if (mInitialY != newY) {
                    mInitialY = getScrollYDistance();
                    postDelayed(mSmoothScrollTask, 30);
                } else if (mItemHeight > 0) {
                    final int offset = mInitialY % mItemHeight;//离选中区域中心的偏移量
//获取被选中item视图偏离两条分割线中间的偏移量offset，如果offset==0，表明刚好落在两条分割线中间，则无需调整。
                    if (offset == 0) {
                        return;
                    }
//如果offset >= mItemHeight / 2，则表示此时被选中的item视图（称之为itemA）滚动到了两条分割线中间点的下方，
// 其趋势是向下滚动，所以我们就继续使其向下滚动，滚动距离为mItemHeight - offset，
// 刚好使得itemA上面的item视图滚动到两条分割线中间，作为被选中item视图。
                    if (offset >= mItemHeight / 2) {//滚动区域超过了item高度的1/2，调整position的值
                        Log.d("SmoothScrollTask1", String.valueOf(mItemHeight - offset));
                        smoothScrollBy(0, mItemHeight - offset);
//如果offset < mItemHeight / 2，则表示此时被选中的item视图（称之为itemA）滚动到了两条分割线的上半部分，
//其趋势是无法再继续向下滚动，而是有回弹的迹象，所以此时我们只需要回退offset个距离即可，
// 这样就等于将itemA的下一个item视图滚动到了两条分割线中间。
                    } else if (offset < mItemHeight / 2) {
                        Log.d("SmoothScrollTask2", String.valueOf(- offset));
                        smoothScrollBy(0, -offset);
                    }
                }
            }
        };
    }


    private int getVisibleItemNumber() {
        IPickerViewOperation operation = (IPickerViewOperation) getAdapter();
        if (operation != null) {
            return operation.getVisibleItemNumber();
        }
        return 3;
    }

    public int getItemSelectedOffset() {
        IPickerViewOperation operation = (IPickerViewOperation) getAdapter();
        if (operation != null) {
            return operation.getSelectedItemOffset();
        }
        return 1;
    }

    //获取外部设置的分割线颜色，正是通过上文中的IPickerViewOperation完成的，IPickerViewOperation接口的设计理念请参考上篇文章，getLineColor方法代码如下所示：
    private int getLineColor() {
        IPickerViewOperation operation = (IPickerViewOperation) getAdapter();
        if (operation != null && operation.getLineColor() != 0) {
            return operation.getLineColor();
        }
        return getResources().getColor(R.color.white);
    }

    //将选中的视图及其被选中的状态传递出去给Adapter的updateView执行，实际上是通过adapter（IPickerViewOperation）来进行传递的
    private void updateView(View itemView, boolean isSelected) {
        IPickerViewOperation operation = (IPickerViewOperation) getAdapter();
        if (operation != null) {
            operation.updateView(itemView, isSelected);
        }
    }

    private void freshItemView() {
//我们遍历了ScrollPickerView中可见的item视图，
//然后判断哪条item视图位于两条分割线之内。这里判断item视图是否在两条分割线之间的方法很简单，
//就是通过当前item视图在其父视图中的Y坐标是否在两条分割线之内进行判断的。
        for (int i = 0; i < getChildCount(); i++) {
            float itemViewY = getChildAt(i).getTop() + mItemHeight / 2;
            updateView(getChildAt(i), mFirstLineY < itemViewY && itemViewY < mSecondLineY);
        }
    }

    private void MyFreshItemView() {
//我们遍历了ScrollPickerView中可见的item视图，
//然后判断哪条item视图位于两条分割线之内。这里判断item视图是否在两条分割线之间的方法很简单，
//就是通过当前item视图在其父视图中的Y坐标是否在两条分割线之内进行判断的。
        for (int i = 0; i < getChildCount(); i++) {
            float itemViewY = getChildAt(i).getTop() + mItemHeight / 2;
            updateView(getChildAt(i), mFirstLineY < itemViewY && itemViewY < mSecondLineY);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initPaint();
    }

    public int getmItemHeight() {
        measureSize();
        return getChildAt(0).getMeasuredHeight();
    }

}
