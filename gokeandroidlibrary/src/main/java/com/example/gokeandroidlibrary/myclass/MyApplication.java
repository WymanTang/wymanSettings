package com.example.gokeandroidlibrary.myclass;

import android.app.Application;

import com.example.gokeandroidlibrary.myclass.CrashHandler;

import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;

/**
 * 自定义Application，实现crashHandler的注入
 * Created by wyman on 2017/5/23.
 */

public class MyApplication extends Application implements ResourceClient {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    public static MyApplication getInstance(){
        return mInstance;
    }

    @Override
    public void notifyRelease(ResourceProxy proxy) {

    }

    @Override
    public void release(ResourceProxy proxy) {

    }

    @Override
    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        return false;
    }
}
