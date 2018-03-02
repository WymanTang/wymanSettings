package com.goke.settings.activity;

import android.app.FragmentManager;

import com.example.gokeandroidlibrary.kjframe.SupportActivity;
import com.goke.settings.R;
import com.goke.settings.fragment.DisplaySetupFragment;

public class DisplaySetupActivity extends SupportActivity {
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_display_setup);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.settingFragment,new DisplaySetupFragment());
        transaction.commit();
    }
}
