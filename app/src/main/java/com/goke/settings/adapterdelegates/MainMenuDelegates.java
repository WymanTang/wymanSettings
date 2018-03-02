package com.goke.settings.adapterdelegates;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gokeandroidlibrary.adapterdelegates.AdapterDelegate;
import com.example.gokeandroidlibrary.kjframe.SupportActivity;
import com.example.gokeandroidlibrary.myclass.DisplayableItem;
import com.example.gokeandroidlibrary.myclass.IconAnd2Text;
import com.goke.settings.activity.DisplaySetupActivity;
import com.goke.settings.R;
import com.goke.settings.displayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by wyman on 2017/5/11.
 */
public class MainMenuDelegates extends AdapterDelegate<List<DisplayableItem>> {
    private LayoutInflater layoutInflater;
    private static List<DisplayableItem> mLists = new ArrayList<>();
    private SupportActivity activity;
    private int wholeWidth;
    private int wholeHeight;

    public MainMenuDelegates(SupportActivity activity) {
        this.activity = activity;
        layoutInflater = activity.getLayoutInflater();
    }

    @Override public boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        mLists = items;

        return items.get(position) instanceof IconAnd2Text;
    }

    @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        if(parent.getWidth() == 0){
            wholeWidth = parent.getLayoutParams().width;
        }else {
            wholeWidth = parent.getWidth();
        }

        if(parent.getHeight() == 0){
            wholeHeight = parent.getLayoutParams().height;
        }else {
            wholeHeight = parent.getHeight();
        }

        final MainMenuViewHolder viewHolder = new MainMenuViewHolder(layoutInflater.inflate(R.layout.menu_main_rec_item, parent, false));
        /*viewHolder.linearLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                }
                else {

                }
            }
        });
         */

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position;
                position = viewHolder.getAdapterPosition();
                IconAnd2Text iconAnd2Text = (IconAnd2Text) mLists.get(position);
                if(iconAnd2Text.getTextToShow().equals("图像设置")){
                    activity.showActivity(activity, DisplaySetupActivity.class);
                }
                if(iconAnd2Text.getTextToShow().equals("网络设置")){
                    activity.showActivity(activity, displayActivity.class);
                }
                if(iconAnd2Text.getTextToShow().equals("历史记录")){
                    //activity.showActivity(activity, PlayerHistoryActivity.class);
                }
                if(iconAnd2Text.getTextToShow().equals("设置")){
                    //activity.showActivity(activity, SettingActivity.class);
                }

            }
        });

        return viewHolder;
    }

    @Override public void onBindViewHolder(@NonNull List<DisplayableItem> items, int position,
                                           @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        MainMenuViewHolder vh = (MainMenuViewHolder) holder;
        IconAnd2Text iconAnd2Text = (IconAnd2Text) mLists.get(position);
        vh.itemLayout.getLayoutParams().width = wholeWidth/4;

        vh.icon.getLayoutParams().width = wholeWidth/16;
        vh.icon.getLayoutParams().height = wholeWidth/16;
        vh.icon.setImageResource(iconAnd2Text.getIconImageId());
        vh.name.setTextSize(TypedValue.COMPLEX_UNIT_PX,wholeHeight/10);
        vh.name.setText(iconAnd2Text.getTextToShow());
        //vh.subcribe.setTextSize(TypedValue.COMPLEX_UNIT_PX,wholeHeight/24);
        vh.subcribe.setText(iconAnd2Text.getText2ToShow());
        //vh.subcribe.setAlpha(0.5f);
    }

    private static class MainMenuViewHolder extends RecyclerView.ViewHolder {
        private PercentRelativeLayout itemLayout;
        private ImageView icon;
        private TextView name;
        private TextView subcribe;

        private MainMenuViewHolder(View itemView) {
            super(itemView);
            itemLayout = (PercentRelativeLayout)itemView.findViewById(R.id.MenuMainRecItemLayout);
            icon = (ImageView)itemView.findViewById(R.id.MenuMainRecItemIcon);
            name = (TextView)itemView.findViewById(R.id.MenuMainRecItemName);
            subcribe = (TextView)itemView.findViewById(R.id.MenuMainRecItemSub);
        }
    }
}
