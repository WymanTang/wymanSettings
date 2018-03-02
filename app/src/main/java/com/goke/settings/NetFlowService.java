package com.goke.settings;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioRecord;
import android.net.TrafficStats;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.percent.PercentRelativeLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NetFlowService extends Service {
    boolean windowState = false;
    private NetFlowServiceBinder netFlowServiceBinder = new NetFlowServiceBinder();
    WindowManager mWindowManger;
    TextView uploadFlow = null;
    TextView downloadFlow = null;
    View windowView = null;
    Long txByteMemory = 0L;
    Long rxByteMemory = 0L;
    Long txByteNow = 0L;
    Long rxByteNow = 0L;

    private Handler timeHandler = new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            txByteNow = TrafficStats.getTotalTxBytes();
            float speedTx = (txByteNow - txByteMemory)/ 1024;
            txByteMemory = txByteNow;
            rxByteNow = TrafficStats.getTotalRxBytes();
            float speedRx = (rxByteNow - rxByteMemory)/ 1024;
            rxByteMemory = rxByteNow;
            uploadFlow.setText(String.format("%1$.1f KB/S",speedRx));
            downloadFlow.setText(String.format("%1$.1f KB/S",speedTx));
            timeHandler.postDelayed(this,1000);
        }
    };

    public NetFlowService() {

    }

    class NetFlowServiceBinder extends Binder{
        public void windowShow(){
            netWindowShow();
        }

        public void windowHide(){
            netWindowHide();
        }

        public boolean getWindowState(){
            return windowState;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        mWindowManger = (WindowManager)this.getSystemService(WINDOW_SERVICE);
        return netFlowServiceBinder;
    }

    private void netWindowShow(){
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        SurfaceView surfaceView = new SurfaceView(this);
        layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        mWindowManger.getDefaultDisplay().getMetrics(dm);
        //int screenWidth = dm.widthPixels;
        //int screenHeight = dm.heightPixels;

        //窗口高度
        layoutParams.width = (int)(220 / getResources().getDisplayMetrics().density);
        //窗口的宽度
        layoutParams.height = (int)(20 / getResources().getDisplayMetrics().density);
        //以屏幕左上角为原点，设置x、y初始值，相对于gravity
        layoutParams.x = 10;
        layoutParams.y = 10;
        //透明色
        layoutParams.format = PixelFormat.RGBA_8888;
        //设置为不占焦点
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        windowView = View.inflate(this,R.layout.net_flow_window,null);
        uploadFlow = (TextView) windowView.findViewById(R.id.upLoadFlowText);
        downloadFlow = (TextView) windowView.findViewById(R.id.downLoadFlowText);
        uploadFlow.setTextSize(TypedValue.COMPLEX_UNIT_DIP,layoutParams.height);
        downloadFlow.setTextSize(TypedValue.COMPLEX_UNIT_DIP,layoutParams.height);
        mWindowManger.addView(windowView,layoutParams);

        txByteMemory = TrafficStats.getTotalTxBytes();
        rxByteMemory = TrafficStats.getTotalRxBytes();
        timeHandler.post(runnable);
        windowState = true;
    }

    private void netWindowHide(){
        windowState = false;
    }

    @Override
    public void onDestroy() {
        netWindowHide();

        super.onDestroy();
    }
}
