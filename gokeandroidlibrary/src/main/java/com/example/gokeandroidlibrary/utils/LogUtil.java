package com.example.gokeandroidlibrary.utils;

import android.util.Log;

/**
 * Log统一管理类
 */
public class LogUtil {

    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 是否需要打印bug，可以在application的onCreate函数里面初始化
    public static boolean isDebug = true;
    private static final String TAG = "LogUtil";
    private static int printLimit = 3072;

    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (isDebug){
            if(msg == null || msg.length() == 0){
                return;
            }
            if(msg.length() < printLimit){
                Log.i(TAG,msg);
            }else {
                while (msg.length() > printLimit){
                    String logContent = msg.substring(0,printLimit);
                    msg = msg.replace(logContent,"");
                    Log.i(TAG,logContent);
                }
                Log.i(TAG,msg);
            }
        }
    }

    public static void d(String msg)
    {
        if (isDebug){
            if(msg == null || msg.length() == 0){
                return;
            }
            if(msg.length() < printLimit){
                Log.d(TAG,msg);
            }else {
                while (msg.length() > printLimit){
                    String logContent = msg.substring(0,printLimit);
                    msg = msg.replace(logContent,"");
                    Log.d(TAG,logContent);
                }
                Log.d(TAG,msg);
            }
        }
    }

    public static void e(String msg)
    {
        if (isDebug){
            if(msg == null || msg.length() == 0){
                return;
            }
            if(msg.length() < printLimit){
                Log.e(TAG,msg);
            }else {
                while (msg.length() > printLimit){
                    String logContent = msg.substring(0,printLimit);
                    msg = msg.replace(logContent,"");
                    Log.e(TAG,logContent);
                }
                Log.e(TAG,msg);
            }
        }
    }

    public static void v(String msg)
    {
        if (isDebug){
            if(msg == null || msg.length() == 0){
                return;
            }
            if(msg.length() < printLimit){
                Log.v(TAG,msg);
            }else {
                while (msg.length() > printLimit){
                    String logContent = msg.substring(0,printLimit);
                    msg = msg.replace(logContent,"");
                    Log.v(TAG,logContent);
                }
                Log.v(TAG,msg);
            }
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg)
    {
        if (isDebug){
            if(msg == null || msg.length() == 0){
                return;
            }
            if(msg.length() < printLimit){
                Log.i(tag,msg);
            }else {
                while (msg.length() > printLimit){
                    String logContent = msg.substring(0,printLimit);
                    msg = msg.replace(logContent,"");
                    Log.i(tag,logContent);
                }
                Log.i(tag,msg);
            }
        }
    }

    public static void d(String tag, String msg)
    {
        if (isDebug){
            if(msg == null || msg.length() == 0){
                return;
            }
            if(msg.length() < printLimit){
                Log.d(tag,msg);
            }else {
                while (msg.length() > printLimit){
                    String logContent = msg.substring(0,printLimit);
                    msg = msg.replace(logContent,"");
                    Log.d(tag,logContent);
                }
                Log.d(tag,msg);
            }
        }
    }

    public static void e(String tag, String msg)
    {
        if (isDebug){
            if(msg == null || msg.length() == 0){
                return;
            }
            if(msg.length() < printLimit){
                Log.e(tag,msg);
            }else {
                while (msg.length() > printLimit){
                    String logContent = msg.substring(0,printLimit);
                    msg = msg.replace(logContent,"");
                    Log.e(tag,logContent);
                }
                Log.e(tag,msg);
            }
        }
    }

    public static void v(String tag, String msg)
    {
        if (isDebug){
            if(msg == null || msg.length() == 0){
                return;
            }
            if(msg.length() < printLimit){
                Log.v(tag,msg);
            }else {
                while (msg.length() > printLimit){
                    String logContent = msg.substring(0,printLimit);
                    msg = msg.replace(logContent,"");
                    Log.v(tag,logContent);
                }
                Log.v(tag,msg);
            }
        }
    }
}

