package com.example.gokeandroidlibrary.myclass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * 自定义文字的跑马灯效果
 * Created by wyman on 2017/10/24.
 */

public class MarqueeTextView extends AppCompatTextView implements Runnable {
    private int currentScrollX; // 当前滚动的位置
    private int viewWidth; //组件宽度
    private int textWidth; //文字宽度
    private int scrollSpeed = 5;//默认滚动速度
    private int midWidth;//跑马灯中间间隔

    private boolean isStop = false;
    private boolean isMeasure = false;

    private char[] midValue;
    private StringBuilder marqueeStringBuilder;

    boolean textLengthEnough = false;

    public MarqueeTextView(Context context) {
        super(context);
        marqueeStringBuilder = new StringBuilder();
        midValue = new char[1];         //默认间隔1空格
        int i = 0;
        for(;i < midValue.length;i++){
            midValue[i] = ' ';
        }
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        marqueeStringBuilder = new StringBuilder();
        midValue = new char[1];
        int i = 0;
        for(;i < midValue.length;i++){
            midValue[i] = ' ';
        }
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        marqueeStringBuilder = new StringBuilder();
        midValue = new char[1];
        int i = 0;
        for(;i < midValue.length;i++){
            midValue[i] = ' ';
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = this.getWidth();
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasure) {
            getTextWidth();

            if((textWidth + midWidth) <= viewWidth){
                currentScrollX = -viewWidth;        //当字不够长时默认就以控件宽度进行首尾连接
            }else if(midWidth >= viewWidth){
                currentScrollX = -viewWidth;    //当设置的宽度超过控件宽度时默认就以控件宽度进行首尾连接
            }else{
                textLengthEnough = true;        //符合长文字跑马灯条件 重组文字串，添加空隙
                if((marqueeStringBuilder != null)&&(midValue != null)){
                    marqueeStringBuilder.delete(0,marqueeStringBuilder.length());
                    marqueeStringBuilder.append(midValue);
                    marqueeStringBuilder.append(this.getText());
                    marqueeStringBuilder.append(midValue);
                    marqueeStringBuilder.append(this.getText());
                    setText(marqueeStringBuilder.toString());
                    getTextWidth();
                }
                currentScrollX = -viewWidth + midWidth;
            }
            isMeasure = true;
        }
    }

    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        textWidth = (int) paint.measureText(str);
        midWidth = (int)this.getPaint().measureText(new String(midValue));
    }

    public void run() {

        currentScrollX += 1;
        scrollTo(currentScrollX, 0);

        if (isStop) {
            return;
        }
        if(textLengthEnough){
            if((getScrollX() + viewWidth) == textWidth){
                currentScrollX = (textWidth / 2) - viewWidth;
            }
        }else {
            if (getScrollX() >= textWidth) {
                currentScrollX = -(this.getWidth());// 当前出现的位置
            }
        }


        postDelayed(this, scrollSpeed);
    }

    // 开始滚动
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }

    // 停止滚动
    public void stopScroll() {
        isStop = true;
    }

    // 从头开始滚动
    public void startFromHead() {
        currentScrollX = -viewWidth + midWidth;
        startScroll();
    }

    //跑马灯速度控制
    public void setScrollSpeed(int speed){
        scrollSpeed = speed;
    }

    //宽度设置
    public void setMidWidth(int number){
        if(number <= 0){
            return;
        }
        midValue = new char[number];
        int i = 0;
        for(;i < midValue.length;i++){
            midValue[i] = ' ';
        }
    }
}