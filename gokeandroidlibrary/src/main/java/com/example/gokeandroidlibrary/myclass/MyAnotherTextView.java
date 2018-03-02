package com.example.gokeandroidlibrary.myclass;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by wyman on 2017/7/10.
 */

public class MyAnotherTextView extends AppCompatTextView {


    public MyAnotherTextView(Context context) {
        super(context);
    }

    public MyAnotherTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAnotherTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 先计算了size之后再调用super.onDraw接口，顺序不能改变，否则会出现花屏跳字。
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }
}
