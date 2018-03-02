package com.goke.settings.preference;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemProperties;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;

import com.goke.settings.R;
import com.hisilicon.android.hidisplaymanager.HiDisplayManager;

public class DisplayListPreference extends ListPreference {
    private String TAG = "DisplayListPreference";
    private int NUM_4K = 5; //2160P 24Hz, 2160P 25Hz, 2160P 30Hz, 2160P 50Hz & 2160P 60Hz
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private int mClickedDialogEntryIndex;
    private HiDisplayManager mDisplayManager;

    public DisplayListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDisplayManager = new HiDisplayManager();
        if (mDisplayManager.getDisplayDeviceType() <= 1) { //tv case: 0 is av, 1 is hdmi
            mEntries = context.getResources()
                        .getStringArray(R.array.set_video_tv_list);
            mEntryValues = context.getResources()
                        .getStringArray(R.array.set_video_tv_list_value);
            String productName = SystemProperties.get("ro.product.device");
            Log.d(TAG, "ro.product.device = " + productName);
            if (!productName.substring(0, 5).equals("Hi379")) {
                String[] tempEnum = new String[8];
                String[] tempValue = new String[8];
                int length = mEntryValues.length;
                for (int i = NUM_4K; i < length; i++) {
                    tempEnum[i-NUM_4K] = mEntries[i].toString();
                    tempValue[i-NUM_4K] = mEntryValues[i].toString();
                }
                mEntries = tempEnum;
                mEntryValues = tempValue;
            }
            setEntries(mEntries);
            setEntryValues(mEntryValues);
        } else {
            mEntries = context.getResources()
                        .getStringArray(R.array.set_video_pc_list);
            mEntryValues = context.getResources()
                        .getStringArray(R.array.set_video_pc_list_value);
            setEntries(mEntries);
            setEntryValues(mEntryValues);
        }
    }

    public DisplayListPreference(Context context) {
        this(context, null);
    }

    private int getValueIndex() {
        return findIndexOfValue(String.valueOf(mDisplayManager.getFmt()));
    }

    @Override
    protected void onPrepareDialogBuilder(final Builder builder) {
        if (mEntries == null || mEntryValues == null) {
            throw new IllegalStateException(
                    "ListPreference requires an entries array and an entryValues array.");
        }
        mClickedDialogEntryIndex = getValueIndex();
        builder.setSingleChoiceItems(mEntries, mClickedDialogEntryIndex,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mClickedDialogEntryIndex = which;

                        /*
                         * Clicking on an item simulates the positive button
                         * click, and dismisses the dialog.
                         */
                        DisplayListPreference.this.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        dialog.dismiss();
                    }
        });
        /*
         * The typical interaction for list-based dialogs is to have
         * click-on-an-item dismiss the dialog instead of the user having to
         * press 'Ok'.
         */
        builder.setPositiveButton(null, null);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult && (mClickedDialogEntryIndex >= 0) && (mEntryValues != null)) {
            String value = mEntryValues[mClickedDialogEntryIndex].toString();
            if (callChangeListener(value)) {
                setValue(value);
            }
        }
    }
}
