package com.example.gokeandroidlibrary.myclass;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;


/**
 * 自定义的text view，根据测得的组件宽度，自动计算文字大小以达到左右填满效果
 * 当高度不足以支撑文字大小时，取最大值即高度为文字大小
 * Created by wyman on 2017/5/10.
 */

public class MyTextView extends AppCompatTextView {
    int width;
    int number;
    float maxHeight;
    float wantSize;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 先计算了size之后再调用super.onDraw接口，顺序不能改变，否则会出现花屏跳字。
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        width = this.getWidth();
        maxHeight = this.getHeight();
        number = this.getText().length();
        wantSize = width / number;
        if(wantSize > maxHeight){
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX,maxHeight);
           // Log.d("Text View Set","Now value is"+Float.toString(maxHeight)+this.getText());
        }else {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX,wantSize);
            //Log.d("Text View Set","Now value is"+Float.toString(wantSize)+this.getText());
        }
        super.onDraw(canvas);
    }
}
