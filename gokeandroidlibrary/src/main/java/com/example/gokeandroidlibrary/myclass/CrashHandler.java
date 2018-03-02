package com.example.gokeandroidlibrary.myclass;

import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wyman on 2017/5/23.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context context;
    private final static String PATH = Environment.getExternalStorageDirectory().getPath()+"/CrashLog/";
    private final static String FILE_NAME = "Crash";
    private final static String FILE_NAME_SUFFIX = ".trace";
    private CrashHandler(){
    }

    public static CrashHandler getInstance(){
        if(mInstance == null){
            mInstance = new CrashHandler();
        }
        return mInstance;
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            dumpExceptionToSDCard(e);
        }catch (IOException ex){
            e.printStackTrace();
        }
        //在此添加数据的存储或者上传！
        if(mDefaultCrashHandler != null){
            mDefaultCrashHandler.uncaughtException(t,e);
        }else {
            Process.killProcess(Process.myPid());
        }
    }

    public void init(Context context){
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        context = context.getApplicationContext();
    }

    private void dumpExceptionToSDCard(Throwable e)throws IOException{
        File dir = new File(PATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(PATH + FILE_NAME + FILE_NAME_SUFFIX);
        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            e.printStackTrace(printWriter);
            printWriter.close();
        }catch (Exception ex){
            Log.e("something","dump crash failed!");
        }
    }
}
