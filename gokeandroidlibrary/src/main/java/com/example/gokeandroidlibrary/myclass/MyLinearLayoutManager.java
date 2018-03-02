package com.example.gokeandroidlibrary.myclass;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 在垂直布局情况下，将焦点的右移丢失强行转化为向下的自定义线程manager
 * Created by wyman on 2017/5/24.
 */

public class MyLinearLayoutManager extends LinearLayoutManager {

    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public View onFocusSearchFailed(View focused, int focusDirection, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(focused instanceof SearchView.SearchAutoComplete){
            focusDirection = View.FOCUS_DOWN;
        }
        return super.onFocusSearchFailed(focused, focusDirection, recycler, state);
    }
}
