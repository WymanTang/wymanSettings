package com.example.gokeandroidlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.gokeandroidlibrary.R;

/**
 * Created by wyman on 2017/5/5.
 */

public class CirclePoint extends View{
    int circlePointNum = 0;
    float circlePointStrokeWidth = 0;
    int circlePointColor;
    int choose = -1;
    private Paint paint = new Paint();
    RectF oval1 = new RectF(0,0,0,0);

    public CirclePoint(Context context) {
        super(context);
    }

    public CirclePoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CirclePoint);
        circlePointNum = array.getInteger(R.styleable.CirclePoint_CirclePointNum,3);
        circlePointStrokeWidth = array.getFloat(R.styleable.CirclePoint_CirclePointStrokeWidth,5);
        circlePointColor = array.getColor(R.styleable.CirclePoint_CirclePointColor,Color.BLACK);
        array.recycle();
    }

    public CirclePoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.CirclePoint);
        circlePointNum = array.getInteger(R.styleable.CirclePoint_CirclePointNum,3);
        circlePointStrokeWidth = array.getFloat(R.styleable.CirclePoint_CirclePointStrokeWidth,5);
        circlePointColor = array.getColor(R.styleable.CirclePoint_CirclePointColor,Color.BLACK);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultWidth = 100;
        int defaultHeight = 10;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

        if((heightMeasureMode == MeasureSpec.AT_MOST)&&(widthMeasureMode == MeasureSpec.AT_MOST)){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(heightMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSize,defaultHeight);
        }else if(widthMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultWidth,heightMeasureSize);
        }else {
            setMeasuredDimension(widthMeasureSize,heightMeasureSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float singleWidthByHeight;
        float diameter;
        float singleWidthByWidth;
        float centerOffset = 0;
        float pointLeft;
        float pointRight;
        float pointBottom;
        float pointTop;
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        singleWidthByWidth = width / (circlePointNum * 2 - 1); //原则上默认要有一个直径的间距
        if(singleWidthByWidth < (circlePointStrokeWidth*2)){
            circlePointStrokeWidth = singleWidthByWidth / 2;
        }

        if(height < (circlePointStrokeWidth*2)){
            circlePointStrokeWidth = height/2;
            singleWidthByHeight = height;
        }else {
            singleWidthByHeight = height;
        }
        //当用户设定画线宽度大于组件测量宽高时，强制缩小半径


        if(singleWidthByWidth <= singleWidthByHeight){
            diameter = singleWidthByWidth;
        }else {
            diameter = singleWidthByHeight;
            centerOffset = (width - diameter*(circlePointNum * 2 - 1))/2;
        }

        for(int i = 0;i < circlePointNum;i++){
            paint.setColor(circlePointColor);
            paint.setAntiAlias(true);

            paint.setStrokeWidth(circlePointStrokeWidth);



            if(i != choose) {
                paint.setStyle(Paint.Style.STROKE);
                pointLeft = centerOffset + diameter*i*2 + circlePointStrokeWidth/2;
                pointRight = pointLeft + diameter - circlePointStrokeWidth;
                pointBottom = 0 + circlePointStrokeWidth/2;
                pointTop = diameter - circlePointStrokeWidth/2;
            }else {
                pointLeft = centerOffset + diameter*i*2;
                pointRight = pointLeft + diameter;
                pointBottom = 0;
                pointTop = diameter;
            }

            oval1.set(pointLeft,pointTop,pointRight,pointBottom);
            canvas.drawArc(oval1,0,360,true,paint);
            paint.reset();
        }
    }

    public void setChoose(int choose){
        this.choose = choose;
        invalidate();
    }
}
