package com.goke.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.gokeandroidlibrary.utils.LogUtil;
import com.goke.settings.preference.DisplayListPreference;
import com.hisilicon.android.hidisplaymanager.HiDisplayManager;
import com.hisilicon.android.hisysmanager.HiSysManager;

import java.util.Timer;
import java.util.TimerTask;

public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
    private DisplayListPreference formatList;//TV format list preference object.
    private HiDisplayManager display_manager;
    private Context mContext;
    private int oldFmt;//store old format when set new format,for restore.
    private Rect rect ;
    private AlertDialog alertDialog;
    private MyTask timetask = null;
    private MyHandler myHandler;
    private Timer mTimer = null;//Timer for createDialog.
    private static final int FORMAT_MSG = 0;
    public static int FMT_DELAY_TIME = 12000;//timeout in next 12 seconds.

    static final String CHIP_98CV100 = "type=HI_CHIP_TYPE_HI3798C;version=HI_CHIP_VERSION_V100";
    static final String CHIP_98CV200 = "type=HI_CHIP_TYPE_HI3798C;version=HI_CHIP_VERSION_V200";
    static final String CHIP_98CV200_A = "type=HI_CHIP_TYPE_HI3798C_A;version=HI_CHIP_VERSION_V200";
    static final String CHIP_98CV200_B = "type=HI_CHIP_TYPE_HI3798C_B;version=HI_CHIP_VERSION_V200";
    static final String CHIP_98MV100 = "type=HI_CHIP_TYPE_HI3798M;version=HI_CHIP_VERSION_V100";
    static final String CHIP_96MV100 = "type=HI_CHIP_TYPE_HI3796M;version=HI_CHIP_VERSION_V100";
    static final String CHIP_98MV200 = "type=HI_CHIP_TYPE_HI3798M;version=HI_CHIP_VERSION_V200";
    static final String CHIP_98MV200_A = "type=HI_CHIP_TYPE_HI3798M_A;version=HI_CHIP_VERSION_V200";

    // Chip Support 10 Bit Map
    static final String[] ChipSupport10BitMap = {CHIP_98CV200,CHIP_98CV200_A,CHIP_98CV200_B,CHIP_98MV200,CHIP_98MV200_A};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.display_setting);
        display_manager = new HiDisplayManager();
        formatList = (DisplayListPreference) findPreference(getString(R.string.tv));
        formatList.setOnPreferenceChangeListener(this);
        formatList.setValue(String.valueOf(display_manager.getFmt()));
        mContext = getActivity();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        try
        {
            if (key.equals(getString(R.string.tv)))
            {
                oldFmt = display_manager.getFmt();
                rect = display_manager.getGraphicOutRange();
                int newfmt = Integer.parseInt((String) newValue);
                int currentHDRType = display_manager.getHDRType();
                //if current output mode is hdr or dolby,and the kind of format we are going to set is interlace,then show a notsupport notice
                if(( currentHDRType== 1 || currentHDRType  == 2 ) &&(
                        newfmt == HiDisplayManager.ENC_FMT_1080i_60 //1080i 60Hz
                                || newfmt == HiDisplayManager.ENC_FMT_1080i_50 //1080i 50Hz
                                || newfmt == HiDisplayManager.ENC_FMT_PAL //PAL
                                || newfmt == HiDisplayManager.ENC_FMT_NTSC))//NTSC
                {
                    Toast.makeText(mContext, R.string.not_support_notice, Toast.LENGTH_LONG).show();
                } else {
                    if((!isChipSupport10Bit()) && (newfmt >HiDisplayManager.ENC_FMT_3840X2160_30)){
                        Toast.makeText(mContext, R.string.not_support_notice, Toast.LENGTH_LONG).show();
                    } else {
                        display_manager.setFmt(newfmt);
                        createDialog(newfmt);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isChipSupport10Bit()
    {
        boolean isSupport = false;
        String chipVersion = HiSysManager.getChipVersion();
        int i = 0;
        for (i = 0; i < ChipSupport10BitMap.length; i++)
        {
            if ( ChipSupport10BitMap[i].equals(chipVersion) )
            {
                LogUtil.i("Chip Support HDMI 10 Bit !");
                isSupport = true;
                break;
            }
        }
        if (i == ChipSupport10BitMap.length)
        {
            LogUtil.i("Chip UnSupport HDMI 10 Bit !");
        }
        return isSupport;
    }

    /**
     * createDialog will create a Dialog with two choice:Cancel or OK
     * If click OK,save format and dispose the Timer
     * If click Cancel,restore old format and dispose the Timer
     * Timer is used to Cancel as a default choice if Timer timeout.
     * */
    protected void createDialog(int value)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.exit_ask);
        builder.setTitle(R.string.info);

        /**
         * Click OK will do setPositiveButton.
         * */
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                display_manager.saveParam();
                display_manager.setOptimalFormatEnable(0);
                if(mTimer != null)
                {
                    if(timetask!=null)
                    {
                        timetask.cancel();
                    }
                }
            }
        });

        /**
         * Click Cancle will do setNegativeButton.
         * */
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                resetDefaultValue();
                dialog.dismiss();
                if(mTimer != null)
                {
                    if(timetask!=null)
                    {
                        timetask.cancel();
                    }
                }
                formatList.setValue(String.valueOf(oldFmt));
            }
        });

    /*
     * Now create Dialog in fact.
     */
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).requestFocus();

        timetask= new MyTask(oldFmt);
        mTimer.schedule(timetask, FMT_DELAY_TIME);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK){
                    resetDefaultValue();
                    dialog.dismiss();
                    if(mTimer != null)
                    {
                        if(timetask!=null)
                        {
                            timetask.cancel();
                        }
                    }
                    formatList.setValue(String.valueOf(oldFmt));
                }
                return false;
            }
        });
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            public void onCancel(DialogInterface dialog)
            {
                resetDefaultValue();
                if(mTimer != null)
                {
                    if(timetask!=null)
                    {
                        timetask.cancel();
                    }
                }
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
    }

    /*
     * Restore old format.
     */
    private void resetDefaultValue()
    {
        display_manager.setFmt(oldFmt);
        if(null != rect){
            display_manager.setGraphicOutRange(rect.left,rect.top,rect.right,rect.bottom);
        }
    }

    /*
     * Restore old format and set old value if timeout.
     */
    class MyTask extends TimerTask
    {
        int fmt = 0;
        public MyTask(int fmt)
        {
            this.fmt = fmt;
        }
        public void run()
        {
            resetDefaultValue();
            Message msg = Message.obtain();
            msg.what = FORMAT_MSG;
            myHandler.sendMessage(msg);
        }
    }

    class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            if (msg.what == FORMAT_MSG)
            {
                LogUtil.d("Recev FORMAT_MSG....");
                formatList.setValue(String.valueOf(oldFmt));
            }

            if(alertDialog!=null){
                alertDialog.dismiss();
            }
        }
    }
}
