package com.goke.settings.facerecongnition;

import android.util.Log;
import android.widget.Toast;

import com.baidu.aip.face.AipFace;
import com.example.gokeandroidlibrary.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 人脸识别类
 * Created by wyman on 2017/12/11.
 */

public class AipFaceDemo {
    //设置APPID/AK/SK
    public static final String APP_ID = "10516576";
    public static final String API_KEY = "4PDvCakNh0oxn6PPBiLlIHCn";
    public static final String SECRET_KEY = "92a7TeWRG7sqH0PECRHZlRruLExZeEaP";

    public static final String REGISTER_TYPE_APPEND = "append";
    public static final String REGISTER_TYPE_REPLACE = "replace";

    private static AipFace client;

    public static void main() {
        // 初始化一个AipFace
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        //String path1 = "test1.jpg";
        //HashMap<String, Object> options2 = new HashMap<String, Object>(1);
        //options2.put("user_top_num", 1);
        //JSONObject response3 = client.identifyUser(Arrays.asList("group1", "group2"), path, options2);
    }

    public static void AipFaceDetect(String path){
        if(client == null){
            return;
        }
        //人脸检测代码段
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("max_face_num", "1");
        options.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities");
        JSONObject res = client.detect(path, options);

        //打印结果log代码段
        try {
            LogUtil.d("GokeAipFace", res.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceMatch(ArrayList<String> pathArray){
        if(client == null){
            return;
        }
        //人脸对比代码段

        HashMap<String, String> options1 = new HashMap<String, String>();
        options1.put("ext_fields","qualities");
        options1.put("image_liveness","faceliveness,faceliveness,faceliveness");
        options1.put("types","7,7,7");
        JSONObject response = client.match(pathArray, options1);

        //打印结果log代码段
        try {
            LogUtil.d("GokeAipFace", response.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceIdentify(String path,List<String> group){
        //人脸识别代码段
        HashMap<String, Object> options = new HashMap<String, Object>(1);
        options.put("user_top_num", 1);
        JSONObject identifyRes = client.identifyUser(group, path, options);
        try {
            LogUtil.d("GokeAipFace", identifyRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceVerify(String path,String uid,List<String> group){
        //人脸认证代码段
        HashMap<String, Object> options = new HashMap<String, Object>(1);
        options.put("top_num", 5);
        JSONObject verifyRes = client.verifyUser(uid, group, path, options);
        try {
            LogUtil.d("GokeAipFace", verifyRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceRegister(String path, String uid, String userInfo, List<String> group, String actionType){
        boolean checkSuccess = false;

        if(client == null){
            return;
        }
        //人脸注册及更新代码段,由type标识区别

        //首先进行人脸探测保证录入质量
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("max_face_num", "1");
        options.put("face_fields", "qualities");
        JSONObject detectRes = client.detect(path, options);
        if(detectRes != null){
            JSONObject qualities = null;
            try {
                qualities = detectRes.getJSONArray("result").getJSONObject(0).getJSONObject("qualities");
                if(qualities != null){
                    //五官被遮挡程度检测
                    double leftEyeQuality = qualities.getJSONObject("occlusion").getDouble("left_eye");
                    double rightEyeQuality = qualities.getJSONObject("occlusion").getDouble("right_eye");
                    double noseQuality = qualities.getJSONObject("occlusion").getDouble("nose");
                    double mouthQuality = qualities.getJSONObject("occlusion").getDouble("mouth");
                    double leftCheekQuality = qualities.getJSONObject("occlusion").getDouble("left_cheek");
                    double rightCheekQuality = qualities.getJSONObject("occlusion").getDouble("right_cheek");
                    double chinQuality = qualities.getJSONObject("occlusion").getDouble("chin");

                    //模糊度检测
                    double blur = qualities.getDouble("blur");
                    //光照检测
                    int illumination = qualities.getInt("illumination");
                    //人脸完整度检测
                    int completeness = qualities.getInt("completeness");

                    //姿态角度
                    //三维旋转之俯仰角度
                    double pitch = detectRes.getJSONArray("result").getJSONObject(0).getDouble("pitch");

                    //平面内旋转角
                    double roll = detectRes.getJSONArray("result").getJSONObject(0).getDouble("roll");

                    //三维旋转之左右旋转角
                    double yaw = detectRes.getJSONArray("result").getJSONObject(0).getDouble("yaw");

                    //人脸大小
                    int width = detectRes.getJSONArray("result").getJSONObject(0).getJSONObject("location").getInt("width");
                    int height = detectRes.getJSONArray("result").getJSONObject(0).getJSONObject("location").getInt("height");

                    int checkResult = (leftEyeQuality < 0.6 ? 1 : 0)&(rightEyeQuality < 0.6 ? 1 : 0)&(noseQuality < 0.7 ? 1 : 0)&
                    (mouthQuality < 0.7 ? 1 : 0)&(leftCheekQuality < 0.8 ? 1 : 0)&(rightCheekQuality < 0.8 ? 1 : 0)&(chinQuality < 0.6 ? 1 : 0)&
                            (blur < 0.7 ? 1 : 0)&(illumination > 40 ? 1 : 0)&(completeness == 1 ? 1 : 0)&(pitch < 20 ? 1 : 0)&
                    (roll < 20 ? 1 : 0)&(yaw < 20 ? 1 : 0)&(width > 100 ? 1 : 0)&(height > 100 ? 1 : 0);
                    if(checkResult == 1){
                        checkSuccess = true;
                    }
                }
            }catch (JSONException e){
                LogUtil.d("GokeAipFace","qualities no exist");
            }
        }

        if(checkSuccess){
            HashMap<String, String> optionsRegister = new HashMap<String, String>();
            optionsRegister.put("action_type",actionType);
            JSONObject registerRes = client.addUser(uid, userInfo, group, path, optionsRegister);
            //打印结果log代码段
            try {
                LogUtil.d("GokeAipFace", registerRes.toString(2));
            }catch (org.json.JSONException e){
                e.printStackTrace();
            }
        }else {
            LogUtil.d("GokeAipFace", "register failed!!!");
        }
    }

    public static void AipFaceDelete(String uid){
        // 从人脸库中彻底删除用户
        JSONObject deleteRes = client.deleteUser(uid);
        try {
            LogUtil.d("GokeAipFace", deleteRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceDelete(String uid,List<String> group){
        // 只从指定组中删除用户
        JSONObject deleteRes = client.deleteUser(uid, group);
        try {
            LogUtil.d("GokeAipFace", deleteRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceQueryInfo(String uid){
        // 用户信息查询
        JSONObject queryRes = client.getUser(uid);
        try {
            LogUtil.d("GokeAipFace", queryRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceQueryInfo(String uid,List<String> group){
        // 用户信息查询
        JSONObject queryRes = client.getUser(uid, group);
        try {
            LogUtil.d("GokeAipFace", queryRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceQueryGroupList(int start,int end){
        //组列表查询
        if(end > 1000){
            end = 1000;
        }
        HashMap<String, Object> options = new HashMap<>(2);
        options.put("start", start);
        options.put("num", end);
        JSONObject queryGroupListRes = client.getGroupList(options);
        try {
            LogUtil.d("GokeAipFace", queryGroupListRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceQueryGroupUser(String group,int start,int end){
        //组内用户列表查询
        if(end > 1000){
            end = 1000;
        }
        HashMap<String, Object> options = new HashMap<>(2);
        options.put("start", start);
        options.put("num", end);
        JSONObject queryGroupUserRes = client.getGroupUsers(group,options);
        try {
            LogUtil.d("GokeAipFace", queryGroupUserRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceCopyGroupUser(String group,List<String> targetGroup,String uid){
        //组间复制用户
        JSONObject copyGroupUserRes = client.addGroupUser(group, targetGroup, uid);
        try {
            LogUtil.d("GokeAipFace", copyGroupUserRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }

    public static void AipFaceDeleteGroupUser(List<String> targetGroup,String uid){
        //组间复制用户,用于删除多个组但是不是所有组的情况
        //所有组和单个组直接使用人脸删除接口
        JSONObject deleteGroupUserRes = client.deleteGroupUser(targetGroup, uid);
        try {
            LogUtil.d("GokeAipFace", deleteGroupUserRes.toString(2));
        }catch (org.json.JSONException e){
            e.printStackTrace();
        }
    }
}
