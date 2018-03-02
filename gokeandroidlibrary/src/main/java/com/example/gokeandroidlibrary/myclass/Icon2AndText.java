package com.example.gokeandroidlibrary.myclass;

/**
 * Created by wyman on 2017/8/3.
 */

public class Icon2AndText implements DisplayableItem {
    public int iconImageId;
    public int backGroundId;
    public String textToShow;

    public Icon2AndText(){

    }

    public Icon2AndText(int backGroundId, int iconImageId, String textToShow){
        this.iconImageId = iconImageId;
        this.textToShow = textToShow;
        this.backGroundId = backGroundId;
    }
    public int getBackGroundId(){
        return backGroundId;
    }

    public void setBackGroundId(int backGroundId){
        this.backGroundId = backGroundId;
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
}
