package com.goke.settings.preference;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;

import com.goke.settings.R;
import com.hisilicon.android.hidisplaymanager.HiDisplayManager;

public class DisplayListPreference10Bit extends ListPreference {
    private String TAG = "DisplayListPreference10Bit";
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private int mClickedDialogEntryIndex;
    private HiDisplayManager mDisplayManager;

    public DisplayListPreference10Bit(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDisplayManager = new HiDisplayManager();

        if (mDisplayManager.getDisplayDeviceType() == 1) { //tv case: 0 is av, 1 is hdmi
            mEntries = context.getResources()
                        .getStringArray(R.array.set_color_space_and_deep_color);
            mEntryValues = context.getResources()
                        .getStringArray(R.array.set_color_space_and_deep_color_value);
            setEntries(mEntries);
            setEntryValues(mEntryValues);
        }
        else
        {
            Log.e(TAG, "Not HDMI Output, Don't support ColorSpace DeepColor");
        }

    }

    public DisplayListPreference10Bit(Context context) {
        this(context, null);
    }

    private int getColorSpaceIndex() {
        return findIndexOfValue(String.valueOf(mDisplayManager.getColorSpaceMode()));
    }

    private int getDeepColorIndex() {
        return findIndexOfValue(String.valueOf(mDisplayManager.getDeepColorMode()));
    }

    @Override
    protected void onPrepareDialogBuilder(final Builder builder) {
        if (mEntries == null || mEntryValues == null) {
            throw new IllegalStateException(
                    "ListPreference requires an entries array and an entryValues array.");
        }
        int ColorSpace = getColorSpaceIndex();
        int DeepColor = getDeepColorIndex();
       // mClickedDialogEntryIndex = DisplaySettingsSTB.getColorSpaceValueIndex(ColorSpace, DeepColor);
        builder.setSingleChoiceItems(mEntries, mClickedDialogEntryIndex,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mClickedDialogEntryIndex = which;

                        /*
                         * Clicking on an item simulates the positive button
                         * click, and dismisses the dialog.
                         */
                        DisplayListPreference10Bit.this.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
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
