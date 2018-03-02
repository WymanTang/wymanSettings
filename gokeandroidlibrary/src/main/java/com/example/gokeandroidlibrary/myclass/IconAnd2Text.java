package com.example.gokeandroidlibrary.myclass;

/**
 * Created by wyman on 2017/8/3.
 */

public class IconAnd2Text implements DisplayableItem {
    public int iconImageId;
    public String textToShow;
    public String text2ToShow;

    public IconAnd2Text(){

    }

    public IconAnd2Text(int iconImageId, String textToShow, String text2ToShow){
        this.iconImageId = iconImageId;
        this.textToShow = textToShow;
        this.text2ToShow = text2ToShow;
    }

    public int getIconImageId() {
        return iconImageId;
    }

    public void setIconImageId(int iconImageId) {
        this.iconImageId = iconImageId;
    }

    public String getTextToShow() {
        return textToShow;
    }

    public void setTextToShow(String textToShow) {
        this.textToShow = textToShow;
    }

    public String getText2ToShow() {
        return text2ToShow;
    }

    public void setText2ToShow(String text2ToShow) {
        this.text2ToShow = text2ToShow;
    }
}
