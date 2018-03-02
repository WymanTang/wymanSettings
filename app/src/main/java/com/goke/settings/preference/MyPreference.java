package com.goke.settings.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goke.settings.R;

/**
 * Created by wyman on 2018/3/2.
 */

public class MyPreference extends Preference {

    public MyPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        //return super.onCreateView(parent);
        return LayoutInflater.from(getContext()).inflate(R.layout.activity_display,parent, false);
    }
}
