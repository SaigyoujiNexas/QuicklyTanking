package com.xupt.modulescore.main.Data;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.modulescore.main.R;

/**
 * create by libo
 * create on 2020/7/26
 * description 折线图View
 */
public class LineChartView extends View {
    /** 文字Paint */
    private Paint textPaint;
    /** 基准线Paint */
    private Paint linePaint;
    /** 折线paint */
    private Paint charLinePaint;
    /** Y轴每单元数量高度 */
    private float unitHeight;
    /** Y轴数据数组 */
    private int[] unitHeightNum = new int[] {0, 3, 6, 9, 12, 15, 18,21,24,27};
    private String[] stageStr = new String[] {"5月3", "5月4", "5月5", "5月6", "5月7", "5月8"};
    /** 横线左边距大小 */
    private float lineLeftPadding;
    /** X轴单元宽度 */
    private float unitWidth;
    /** 各个阶段数据数组 */
    private double[] stageNum = new double[] {1.2, 4.0, 8.2, 7.4, 6.0, 9.2};
    private Path linePath;//画折线
    final String TAG = "LineChartViewTAG";
    private int bottomLineMargin = 65;
    public LineChartView(Context context) {
        super(context);
        initPaint();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.green));
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);

        charLinePaint = new Paint();
        charLinePaint.setColor(getResources().getColor(R.color.purple_200));
        charLinePaint.setAntiAlias(true);
        charLinePaint.setStyle(Paint.Style.FILL);

        linePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawYText(canvas);
        drawXText(canvas);
        drawLinePath(canvas);
    }

    /**
     * 绘制Y轴文字及基准线
     */
    private void drawYText(Canvas canvas) {
        int top = getHeight() - bottomLineMargin;  //给底部文字留下高度.越小字越靠下

        unitHeight = (getHeight()-bottomLineMargin)/unitHeightNum.length-unitHeightNum.length/2;

        Rect rect = new Rect();//存文字范围
        String longText = unitHeightNum[unitHeightNum.length-1]+"公里";  //以最长文字对齐

        textPaint.getTextBounds(longText, 0, longText.length(), rect);//通过最长获得文字空隙

        for (int num : unitHeightNum) {
            canvas.drawText(num + "公里", 0, top, textPaint);  //画文字

            lineLeftPadding = rect.width() + 20;
            canvas.drawLine(lineLeftPadding, top, getWidth(), top, linePaint);  //画横线
            top -= unitHeight;
        }
    }

    /**
     * 绘制X轴文字与竖线
     */
    private void drawXText(Canvas canvas) {
        float left = lineLeftPadding;
        unitWidth = getWidth()/stageNum.length - 20;

        for (int i=0;i<stageNum.length;i++) {
            canvas.drawText(stageStr[i], left + unitWidth/4, getHeight(), textPaint);  //画文字

            canvas.drawLine(left, getHeight()-bottomLineMargin, left, bottomLineMargin, linePaint);

            left += unitWidth;
        }
    }

    /**
     * 绘制折线
     */
    private void drawLinePath(Canvas canvas) {
        float left = lineLeftPadding;
        float bottom = getHeight() - bottomLineMargin;

        for (int i=0;i<stageNum.length;i++) {
            float topX = left + unitWidth/2;
            float topY = (float) (bottom - stageNum[i]/3  * unitHeight );

            if (i == 0) {
                linePath.moveTo(topX, topY);//moveTo 不会进行绘制，只用于移动移动画笔。
            } else {
                linePath.lineTo(topX, topY);//lineTo 用于进行直线绘制。
            }
            //画笔样式 STROKE 描边 FILL 填充 FILL_AND_STROKE 描边加填充
            charLinePaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(topX, topY, 13, charLinePaint);  //绘制拐点小圆
            Log.d(TAG,"Y,"+topY);
            left += unitWidth;
        }

        charLinePaint.setStyle(Paint.Style.STROKE);
        charLinePaint.setStrokeWidth(4);
        canvas.drawPath(linePath, charLinePaint);
    }

    public void setUnitHeightNum(int[] unitHeightNum) {
        this.unitHeightNum = unitHeightNum;
    }

    public void setStageStr(String[] stageStr) {
        this.stageStr = stageStr;
    }

    public void setStageNum(double[] stageNum) {
        this.stageNum = stageNum;
    }
}
