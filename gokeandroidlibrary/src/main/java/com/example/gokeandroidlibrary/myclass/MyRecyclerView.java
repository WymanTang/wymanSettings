package com.example.gokeandroidlibrary.myclass;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.View;

/**
 * launcher主滚动界面，利用recycler view的瀑布式布局
 * Created by Wyman on 2017/5/9.
 */

public class MyRecyclerView extends RecyclerView {

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 在搜索框点击搜索之后，如果用户点击右键，而右键再无相应View的话，焦点会丢失
     * 故将右键操作替换为向下操作
     * @param focused
     * @param direction
     * @return
     */
    @Override
    public View focusSearch(View focused, int direction) {
        if(focused instanceof SearchView.SearchAutoComplete){
            if(direction == View.FOCUS_RIGHT){
                direction = View.FOCUS_DOWN;
            }
        }
        return super.focusSearch(focused, direction);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }
}
