package com.goke.settings;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gokeandroidlibrary.kjframe.SupportActivity;

public class Main2Activity extends SupportActivity {
    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main2);
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
        transaction.replace(R.id.settingFragment,new SettingFragment());
        transaction.commit();
    }
}
