package com.goke.settings.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.graphics.Rect;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gokeandroidlibrary.kjframe.SupportActivity;
import com.example.gokeandroidlibrary.kjframe.ui.BindView;
import com.example.gokeandroidlibrary.myclass.DisplayableItem;
import com.example.gokeandroidlibrary.myclass.IconAnd2Text;
import com.goke.settings.R;
import com.goke.settings.adapter.MainAdapter;

import java.util.ArrayList;

public class MainActivity extends SupportActivity {
    ArrayList<DisplayableItem> menuList = new ArrayList<>();

    @BindView(id = R.id.ActivityMainMenuFocus)
    ImageView mainMenuFocus;

    @BindView(id = R.id.MainActivityTitleIcon)
    ImageView mainActivityTitleIcon;

    @BindView(id = R.id.MainActivityTitleText)
    TextView mainActivityTitleText;

    @BindView(id = R.id.MainActivityRec)
    RecyclerView mainActivityRec;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        super.initData();
        menuList.add(new IconAnd2Text(R.drawable.ic_videosetting,"图像设置",""));
        menuList.add(new IconAnd2Text(R.drawable.ic_netsetting,"网络设置",""));
        menuList.add(new IconAnd2Text(R.drawable.ic_audiosetting,"声音设置",""));
        menuList.add(new IconAnd2Text(R.drawable.ic_systeminfo,"系统信息",""));
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mainActivityTitleIcon.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels * 5 / 100;
        mainActivityTitleIcon.getLayoutParams().height = getResources().getDisplayMetrics().widthPixels * 5 / 100;

        StringBuilder deser = new StringBuilder();
        new AlertDialog.Builder(this).setMessage(deser);
        mainMenuFocus.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels * 90 / 100 / 4 - 22;
        mainMenuFocus.getLayoutParams().height = getResources().getDisplayMetrics().heightPixels * 37 / 100 - 22;
        int textSize = getResources().getDisplayMetrics().widthPixels * 4 / 100;
        mainActivityTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        MainAdapter mainAdapter = new MainAdapter(this,menuList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        mainActivityRec.setAdapter(mainAdapter);
        mainActivityRec.setLayoutManager(gridLayoutManager);
        mainActivityRec.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mainActivityRec.getChildAt(0).requestFocus();
                mainActivityRec.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mainActivityRec.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams)mainMenuFocus.getLayoutParams();
                Rect viewRect = new Rect();
                newFocus.getGlobalVisibleRect(viewRect);
                if(oldFocus == null){
                    //first layout no animation
                    layoutParams.leftMargin = viewRect.left + 11;
                    layoutParams.topMargin = viewRect.top + 11;
                    mainMenuFocus.requestLayout();
                }else {
                    Rect viewRectOld = new Rect();
                    oldFocus.getGlobalVisibleRect(viewRectOld);
                    FocusViewWrapper focusViewWrapper = new FocusViewWrapper(mainMenuFocus);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(
                            ObjectAnimator.ofInt(focusViewWrapper,"TopMargin",viewRectOld.top + 11 ,viewRect.top + 11),
                            ObjectAnimator.ofInt(focusViewWrapper,"LeftMargin",viewRectOld.left + 11,viewRect.left + 11)
                    );
                    animatorSet.setDuration(300).start();
                }
                //mainMenuFocus.layout(viewRect.left,viewRect.top,viewRect.right,viewRect.bottom);
            }
        });
    }

    private class FocusViewWrapper {
        ImageView target;

        private FocusViewWrapper(View v) {
            if(v instanceof ImageView){
                target = (ImageView)v;
            }
        }

        public int getTopMargin(){
            PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams)target.getLayoutParams();
            return layoutParams.topMargin;
        }

        public void setTopMargin(int setParams){
            PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams)target.getLayoutParams();
            layoutParams.topMargin = setParams;
            target.requestLayout();
        }

        public int getLeftMargin(){
            PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams)target.getLayoutParams();
            return layoutParams.leftMargin;
        }

        public void setLeftMargin(int setParams){
            PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams)target.getLayoutParams();
            layoutParams.leftMargin = setParams;
            target.requestLayout();
        }
    }
}
