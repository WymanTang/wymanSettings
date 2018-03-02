package com.example.gokeandroidlibrary.myclass;

import android.content.pm.ApplicationInfo;

import java.io.Serializable;

/**
 * 记录应用程序流量的model
 * Created by tanglinggang on 2017/12/22.
 */

public class TrafficModel implements Serializable {
    private ApplicationInfo appInfo;

    private long download;
    private long upload;


    public long getDownload() {
        return download;
    }

    public void setDownload(long download) {
        this.download = download;
    }

    public long getUpload() {
        return upload;
    }

    public void setUpload(long upload) {
        this.upload = upload;
    }

    public ApplicationInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(ApplicationInfo appInfo) {
        this.appInfo = appInfo;
    }
}
