package com.example.gokeandroidlibrary.myclass;

import com.example.gokeandroidlibrary.kjframe.database.annotate.Id;

/**
 * 用于文件管理展示的相关文件对象
 * Created by wyman on 2017/5/12.
 */

public class FileToShow implements DisplayableItem {

    public final static int FILE_TYPE_AUDIO = 1;
    public final static int FILE_TYPE_VIDEO = 2;
    public final static int FILE_TYPE_DOC = 3;
    public final static int FILE_TYPE_UNKNOWN_FILE = 4;

    @Id
    public int id;

    public String name;
    public String path;
    public int iconImageId;
    public int fileType;

    public FileToShow(){

    }

    public FileToShow(String name,String path,int iconImageId,int fileType){
        this.name = name;
        this.path = path;
        this.iconImageId = iconImageId;
        this.fileType = fileType;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getPath(){
        return path;
    }
    public void setPath(String path){
        this.path = path;
    }

    public int getFileType(){
        return fileType;
    }
    public void setFileType(int fileType){
        this.fileType = fileType;
    }

    public int getIconImageId(){
        return iconImageId;
    }

    public void setIconImageId(int iconImageId){
        this.iconImageId = iconImageId;
    }
}
