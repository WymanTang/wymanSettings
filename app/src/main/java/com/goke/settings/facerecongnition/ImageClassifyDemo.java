package com.goke.settings.facerecongnition;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.example.gokeandroidlibrary.utils.LogUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 图像识别类
 * Created by wyman on 2017/12/14.
 */

public class ImageClassifyDemo {
    //设置APPID/AK/SK
    public static final String APP_ID = "10537610";
    public static final String API_KEY = "QhI6EpSmRdjSMw21QiMy4PBi";
    public static final String SECRET_KEY = "wFruUCLGNGOVSxfGQYpQKf58eY4xVy7K";

    private static AipImageClassify client;

    public static void main() {
        // 初始化一个AipFace
        client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
    }

    public static void ImageClassifyDishDetect(String path){
        //菜品识别
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "5");

        JSONObject res = client.dishDetect(path, options);

        try {
            LogUtil.d("GokeImageClassify", res.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void ImageClassifyCarDetect(String path){
        //车型识别
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "5");

        JSONObject res = client.carDetect(path, options);

        try {
            LogUtil.d("GokeImageClassify", res.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void ImageClassifyLogoDetect(String path){
        //logo识别
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "5");

        JSONObject res = client.logoSearch(path, options);

        try {
            LogUtil.d("GokeImageClassify", res.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void ImageClassifyAnimalDetect(String path){
        //动物识别
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "5");

        JSONObject res = client.animalDetect(path, options);

        try {
            LogUtil.d("GokeImageClassify", res.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void ImageClassifyPlantDetect(String path){
        //植物识别
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "5");

        JSONObject res = client.plantDetect(path, options);

        try {
            LogUtil.d("GokeImageClassify", res.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void ImageClassifyObjectDetect(String path){
        //主体识别
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("top_num", "5");

        JSONObject res = client.objectDetect(path, options);

        try {
            LogUtil.d("GokeImageClassify", res.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }
}
