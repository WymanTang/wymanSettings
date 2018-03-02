package com.goke.settings;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gokeandroidlibrary.kjframe.SupportActivity;
import com.example.gokeandroidlibrary.kjframe.ui.BindView;
import com.example.gokeandroidlibrary.myclass.TrafficModel;
import com.example.gokeandroidlibrary.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class netSettingActivity extends SupportActivity {

    @BindView(id = R.id.MainActivityTitleIcon)
    ImageView mainActivityTitleIcon;

    @BindView(id = R.id.MainActivityTitleText)
    TextView mainActivityTitleText;

    @BindView(id = R.id.netFlowOpen)
    Button netFlowOpenButton;

    @BindView(id = R.id.netFlowClose)
    Button netFlowCloseButton;

    ConnectivityManager localConManager;

    private NetFlowService.NetFlowServiceBinder netFlowServiceBinder;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_net_setting);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mainActivityTitleIcon.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels * 5 / 100;
        mainActivityTitleIcon.getLayoutParams().height = getResources().getDisplayMetrics().widthPixels * 5 / 100;
        int textSize = getResources().getDisplayMetrics().widthPixels * 4 / 100;
        mainActivityTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            netFlowServiceBinder = (NetFlowService.NetFlowServiceBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    public void initData() {
        super.initData();
        localConManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        Intent bindIntent = new Intent(this,NetFlowService.class);
        bindService(bindIntent,serviceConnection,BIND_AUTO_CREATE);
        netFlowOpenButton.setOnClickListener(this);
        netFlowCloseButton.setOnClickListener(this);
        //NetworkInfo networks = localConManager.getActiveNetworkInfo();
        //trafficMonitor();
    }

    /**
     * 遍历有联网权限的应用程序的流量记录
     */
    private void trafficMonitor(){
        PackageManager pm = this.getPackageManager();
        List<PackageInfo> packinfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
        for (PackageInfo info : packinfos) {
            String[] premissions = info.requestedPermissions;
            if (premissions != null && premissions.length > 0) {
                for (String premission : premissions) {
                    if ("android.permission.INTERNET".equals(premission)) {
                        // System.out.println(info.packageName+"访问网络");
                        int uid = info.applicationInfo.uid;
                        long rx = TrafficStats.getUidRxBytes(uid);
                        long tx = TrafficStats.getUidTxBytes(uid);

                        TrafficModel appTrafficModel = new TrafficModel();
                        appTrafficModel.setAppInfo(info.applicationInfo);
                        appTrafficModel.setDownload(rx);
                        appTrafficModel.setUpload(tx);

                        /** 获取手机通过 2G/3G 接收的字节流量总数 */
                        TrafficStats.getMobileRxBytes();
                        /** 获取手机通过 2G/3G 接收的数据包总数 */
                        TrafficStats.getMobileRxPackets();
                        /** 获取手机通过 2G/3G 发出的字节流量总数 */
                        TrafficStats.getMobileTxBytes();
                        /** 获取手机通过 2G/3G 发出的数据包总数 */
                        TrafficStats.getMobileTxPackets();
                        /** 获取手机通过所有网络方式接收的字节流量总数(包括 wifi) */
                        TrafficStats.getTotalRxBytes();
                        /** 获取手机通过所有网络方式接收的数据包总数(包括 wifi) */
                        TrafficStats.getTotalRxPackets();
                        /** 获取手机通过所有网络方式发送的字节流量总数(包括 wifi) */
                        TrafficStats.getTotalTxBytes();
                        /** 获取手机通过所有网络方式发送的数据包总数(包括 wifi) */
                        TrafficStats.getTotalTxPackets();
                        /** 获取手机指定 UID 对应的应程序用通过所有网络方式接收的字节流量总数(包括 wifi) */
                        TrafficStats.getUidRxBytes(uid);
                        /** 获取手机指定 UID 对应的应用程序通过所有网络方式发送的字节流量总数(包括 wifi) */
                        TrafficStats.getUidTxBytes(uid);
                    }
                }
            }
        }
    }

    private void cleanBackgroud(){
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        List<ActivityManager.RunningServiceInfo> serviceInfos  = am.getRunningServices(100);

        long beforeMem = getAvailMemory();
        LogUtil.d("-----------before memory info : " + beforeMem);
        int count = 0;
        PackageManager pm = getPackageManager();

        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                LogUtil.d("process name : " + appProcessInfo.processName);
                //importance 该进程的重要程度 分为几个级别，数值越低就越重要。
                LogUtil.d("importance : " + appProcessInfo.importance);



                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                        String appName = null;
                        try {
                            appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(pkgList[j], 0));
                        } catch (PackageManager.NameNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        LogUtil.d("It will be killed, package name : " + pkgList[j]+" -- "+appName );
                        am.killBackgroundProcesses(pkgList[j]);
                        count++;
                    }
                }

            }
        }

        long afterMem = getAvailMemory();
        LogUtil.d("----------- after memory info : " + afterMem);
        Toast.makeText(this, "clear " + count + " process, "
                + (afterMem - beforeMem) + "M", Toast.LENGTH_LONG).show();
    }

    private long getAvailMemory() {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        //return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
        LogUtil.d("可用内存---->>>" + mi.availMem / (1024 * 1024));
        return mi.availMem / (1024 * 1024);
    }

    @Override
    public void widgetClick(View v) {
        if(v.equals(netFlowOpenButton)){
            netFlowServiceBinder.windowShow();
        }

        if(v.equals(netFlowCloseButton)){
            cleanBackgroud();
        }
        super.widgetClick(v);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if((event.getKeyCode() == KeyEvent.KEYCODE_0)&&(event.getAction() == 0)){

            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
