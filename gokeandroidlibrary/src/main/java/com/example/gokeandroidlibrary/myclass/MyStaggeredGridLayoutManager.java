package com.example.gokeandroidlibrary.myclass;

import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 自定义焦点寻找逻辑的布局管理器
 * Created by wyman on 2017/5/24.
 */

public class MyStaggeredGridLayoutManager extends StaggeredGridLayoutManager {


    RecyclerView recyclerView;

    public MyStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    public void setRecyclerViewPlus(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public View onFocusSearchFailed(View focused, int direction, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int mOrientation;
        int layoutTarget;
        int nowPosition;
        int newPageFirstPosition;
        int findFocusPosition;

        final Rect rectViewNow = new Rect();
        final Rect rectViewTarget = new Rect();
        View compareViewNow;
        View compareViewNext;

        View view = super.onFocusSearchFailed(focused, direction, recycler, state);
        if(view == null){
            return null;
        }

        nowPosition = getPosition(focused);
        mOrientation = getOrientation();
        switch (direction) {
            case View.FOCUS_UP:
                layoutTarget = mOrientation == VERTICAL ? 1
                        : 0;
                break;
            case View.FOCUS_DOWN:
                layoutTarget = mOrientation == VERTICAL ? -1
                        : 0;
                break;
            case View.FOCUS_LEFT:
                layoutTarget = mOrientation == HORIZONTAL ? 1
                        : 0;
                break;
            case View.FOCUS_RIGHT:
                layoutTarget = mOrientation == HORIZONTAL ? -1
                        : 0;
                break;
            default:
                layoutTarget = 0;
        }

        if(layoutTarget == 1){
            newPageFirstPosition = getPosition(getChildAt(0));
            if(nowPosition - newPageFirstPosition <= 1){
                view = findViewByPosition(newPageFirstPosition);
            }else {
                //仅仅添加了水平算法，竖直的待添加
                if(mOrientation == HORIZONTAL) {
                    for (findFocusPosition = nowPosition - 1; findFocusPosition >= newPageFirstPosition; findFocusPosition--) {
                        compareViewNow = findViewByPosition(nowPosition);
                        compareViewNext = findViewByPosition(findFocusPosition);
                        rectViewNow.set(0, 0, compareViewNow.getWidth(), compareViewNow.getHeight());
                        rectViewTarget.set(0, 0, compareViewNext.getWidth(), compareViewNext.getHeight());
                        recyclerView.offsetDescendantRectToMyCoords(compareViewNow, rectViewNow);
                        recyclerView.offsetDescendantRectToMyCoords(compareViewNext, rectViewTarget);
                        if ((rectViewNow.top == rectViewTarget.top) && (rectViewNow.bottom == rectViewTarget.bottom)) {
                            view = findViewByPosition(findFocusPosition);
                            break;
                        }
                    }
                }
            }
        }
        return view;
    }
}
