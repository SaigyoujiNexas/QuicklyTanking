package com.xupt.modulescore.main.UI.View;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.modulescore.main.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProgressButton extends FloatingActionButton {

    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画圆环c3c3c3的画笔
    private Paint mRingC3Paint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 圆环底颜色
    private int mRingC3Color;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 大圆环半径
    private float mBigRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 总进度
    private int mTotalProgress = 300;
    // 当前进度
    private int mProgress;
    //方块边长
    private float mSquare_side_length;
    //中心方块画笔
    private Paint mCenterPaint;
    //中心图案颜色
    private int mCenterColor;
    private boolean isFinish;
    public ProgressButton(@NonNull Context context) {
        this(context,null);
    }

    public ProgressButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initVariable();
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startAnimationProgress(300);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mProgress >= 300) {
                            if (!isFinish) {
                                mProgressButtonFinishCallback.onFinish();
                                return false;
                            }
                        }
                        if (mProgress != 300) {
                            if (mProgress < 300) {
                                stopAnimationProgress(mProgress);
                                mProgressButtonFinishCallback.onCancel();
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xCCCCCC);
        mRingC3Color = typeArray.getColor(R.styleable.TasksCompletedView_ringCColor, 0xCCCCCC);
        mSquare_side_length = typeArray.getDimension(R.styleable.TasksCompletedView_squaresidelength,8);
        mCenterColor = typeArray.getColor(R.styleable.TasksCompletedView_centerColor,0xFFFFFFFF);
        mRingRadius = mRadius + mStrokeWidth;
        mBigRingRadius = mRadius + mStrokeWidth * 2;
        Log.d("initAttrs","mRadius"+mRadius+"mRingRadius"+
                mRingRadius+"mStrokeWidth"+mStrokeWidth+"mBigRingRadius"+mBigRingRadius);
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);//消除锯齿
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);//消除锯齿
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mRingC3Paint = new Paint();
        mRingC3Paint.setAntiAlias(true);//消除锯齿
        mRingC3Paint.setColor(mRingC3Color);
        mRingC3Paint.setStyle(Paint.Style.STROKE);
        mRingC3Paint.setStrokeWidth(mStrokeWidth - 2);

        mCenterPaint = new Paint();
        mCenterPaint.setAntiAlias(true);//消除锯齿
        mCenterPaint.setColor(mCenterColor);
        mCenterPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;
        canvas.drawCircle(mXCenter, mYCenter, mBigRingRadius, mCirclePaint);
        RectF rectF = new RectF();
        rectF.left = (mXCenter - mSquare_side_length/2);
        rectF.right = (mXCenter + mSquare_side_length/2);
        rectF.top = (mYCenter + mSquare_side_length/2);
        rectF.bottom = (mYCenter - mSquare_side_length/2);
        canvas.drawRect(rectF,mCenterPaint);
        if(mProgress == 300){
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius  + mXCenter;
            oval.bottom = mRingRadius + mYCenter;
            canvas.drawArc(oval, -90, 360, false, mCirclePaint); //

        }else if (mProgress > 0) {

            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius  + mXCenter;
            oval.bottom = mRingRadius + mYCenter;

            canvas.drawArc(oval, -90,  360, false, mRingC3Paint);

            canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint); //

        }

    }



    private ProgressButtonFinishCallback mProgressButtonFinishCallback;

    public void setListener(ProgressButtonFinishCallback progressButtonFinishCallback){
        mProgressButtonFinishCallback = progressButtonFinishCallback;
    }

    public interface ProgressButtonFinishCallback{
        void onFinish();
        void onCancel();
    }

    private ValueAnimator startAnimator;
    private ValueAnimator stopAnimator;

    //按压开始
    private void startAnimationProgress(int progress) {
        isFinish = false;
        if(null!=stopAnimator){
            if(stopAnimator.isRunning()){
                stopAnimator.cancel();
            }
        }
        startAnimator = ValueAnimator.ofInt(0, progress);
        startAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
                if(mProgress >= 300){
                    if(!isFinish){
                        isFinish = true;
                        mProgressButtonFinishCallback.onFinish();
                        Log.v("startAnimationProgress", mProgress + "");
                    }
                }
            }
        });
        startAnimator.setInterpolator(new OvershootInterpolator());
        startAnimator.setDuration(3000);
        startAnimator.start();
    }

    //按压结束
    private void stopAnimationProgress(int progress) {
        if(null!=startAnimator) {
            if (startAnimator.isRunning()) {
                startAnimator.cancel();
            }
        }
        stopAnimator = ValueAnimator.ofInt(progress, 0);
        stopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        stopAnimator.setInterpolator(new OvershootInterpolator());
        stopAnimator.setDuration(3000);
        stopAnimator.start();
    }
}
