package com.example.gokeandroidlibrary.myclass;

/**
 * Created by wyman on 2017/8/3.
 */

public class IconAndAnotherIcon implements DisplayableItem {
    public int iconImageId;
    public int smallIconImageId;

    public IconAndAnotherIcon(){

    }

    public IconAndAnotherIcon(int iconImageId, int smallIconImageId){
        this.iconImageId = iconImageId;
        this.smallIconImageId = smallIconImageId;
    }

    public int getIconImageId() {
        return iconImageId;
    }

    public void setIconImageId(int iconImageId) {
        this.iconImageId = iconImageId;
    }

    public int getSmallIconImageId() {
        return this.smallIconImageId;
    }

    public void setSmallIconImageId(int smallIconImageId) {
        this.smallIconImageId = smallIconImageId;
    }
}
