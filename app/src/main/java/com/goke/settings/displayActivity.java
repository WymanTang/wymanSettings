package com.goke.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.baidu.aip.face.AipFace;
import com.example.gokeandroidlibrary.kjframe.SupportActivity;
import com.goke.settings.facerecongnition.AipFaceDemo;
import com.goke.settings.facerecongnition.ImageClassifyDemo;

import java.util.ArrayList;
import java.util.Arrays;

public class displayActivity extends SupportActivity {

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_display);
        AipFaceDemo.main();
        ImageClassifyDemo.main();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean returnValue = false;

        switch (keyCode){
            case KeyEvent.KEYCODE_0:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String imagePath1 = "/mnt/sda/sda1/picture/logo1.jpg";
                        ImageClassifyDemo.ImageClassifyLogoDetect(imagePath1);
                    }
                }).start();
                returnValue = true;
                break;
            case KeyEvent.KEYCODE_1:
                /*人脸识别测试
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String imagePath1 = "/mnt/sda/sda1/picture/gutianle1.jpg";
                        String imagePath2 = "/mnt/sda/sda1/picture/gutianle2.jpg";
                        String imagePath3 = "/mnt/sda/sda1/picture/gutianle3.jpg";
                        ArrayList<String> pathArray = new ArrayList<String>();
                        pathArray.add(imagePath1);
                        pathArray.add(imagePath2);
                        pathArray.add(imagePath3);
                        AipFaceDemo.AipFaceMatch(pathArray);
                    }
                }).start();
                 */
                returnValue = true;
                break;
            case KeyEvent.KEYCODE_2:
                /*人脸识别测试
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String imagePath4 = "/mnt/sda/sda1/picture/zhangguorong1.jpg";
                        AipFaceDemo.AipFaceDetect(imagePath4);
                    }
                }).start();
                 */
                returnValue = true;
                break;
            case KeyEvent.KEYCODE_3:
                /*人脸识别测试
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String imagePath4 = "/mnt/sda/sda1/picture/gutianle2.jpg";
                        AipFaceDemo.AipFaceRegister(imagePath4,"gutianle_1","中国著名相貌平平", Arrays.asList("Tanglinggang"),"append");
                    }
                }).start();
                 */
                returnValue = true;
                break;
            case KeyEvent.KEYCODE_4:
                /*人脸识别测试
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String imagePath4 = "/mnt/sda/sda1/picture/liangchaowei3.jpg";
                        AipFaceDemo.AipFaceIdentify(imagePath4,Arrays.asList("Tanglinggang"));
                    }
                }).start();
                 */
                returnValue = true;
                break;
            case KeyEvent.KEYCODE_5:
                /*人脸识别测试
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String imagePath4 = "/mnt/sda/sda1/picture/liangchaowei3.jpg";
                        AipFaceDemo.AipFaceVerify(imagePath4,"liangchaowei_1",Arrays.asList("Tanglinggang"));
                    }
                }).start();
                 */
                returnValue = true;
                break;
            default:
                returnValue = false;
                break;
        }

        return returnValue || super.onKeyDown(keyCode, event);
    }
}
