package com.wwc2.dvr.fourCamera;

import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;



import com.wwc2.dvr.utils.Config;

import com.wwc2.dvr.utils.Utils;

public class FourCameraProxy {

    final static String CAMERA_ACTION_NODE = "/sys/devices/platform/wwc2_camera_combine/camera_action";

    final static String displayModeOb = "/sys/devices/platform/wwc2_camera_combine/display_mode";

    final static String record_latency = "/sys/devices/platform/wwc2_camera_combine/record_latency";

    final static String water_mask = "/sys/devices/platform/wwc2_camera_combine/water_mask";

    final static String audio_enable = "/sys/devices/platform/wwc2_camera_combine/audio_enable";

//    final static String CAMERA_PLATFORM_TYPE = "ro.wwc2camera.platformtype";
    final static String CAMERA_PLATFORM_TYPE = "persist.wwc2camera.platformtype";//需要DVR设置

//    write channel_id mode param > IC_PARAM
//
//    channel_id:摄像头通道号，取值 0~3
//
//    mode:模式, 取值 0~3, 0:对比度，1:亮度，2:饱和度，3:色调
//
//    param:对应模式数值，取值0~255

//    获取默认值 read IC_PARAM
//     read的数据格式类似下面数值
//
//        128 128 144 128
//        128 128 144 128
//        128 128 144 128
//        128 128 144 128
//
//        0~3行对应到通道号，数值分别表示 对比度、亮度、饱和度、色调数值
    final static String display_ic_param = "/sys/devices/platform/wwc2_camera_combine/ic_param";

    final static String CAPTURE_FILE = "/sys/devices/platform/wwc2_camera_combine/capture_file";

    final static String CAR_NUMBER_DATA = "/sys/devices/platform/wwc2_camera_combine/card_data";

    //打开Camera前设置一次全部参数
    final static String CAMERA_PARAM  = "/sys/devices/platform/wwc2_camera_combine/camera_param";



    public FourCameraProxy()  {

    }

    public void SettingFrontCameraView() {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_DISPLAY, Config.FRONT_DISPLAY);
        bean.Action();
    }

    public void SettingBackCameraView() {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_DISPLAY, Config.BACK_DISPLAY);
        bean.Action();
    }

    public void SettingLeftCameraView() {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_DISPLAY, Config.LEFT_DISPLAY);
        bean.Action();
    }

    public void SettingRightCameraView() {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_DISPLAY, Config.RIGHT_DISPLAY);
        bean.Action();
    }

    public void SettingQuartCameraView() {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_DISPLAY, Config.QUART_DISPLAY);
        bean.Action();
    }

    public void getAVM360Stat() {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_DISPLAY,8);
        bean.Action();
    }

    public boolean isFourDisplay() {
        boolean ret = false;
        String value = Utils.readTextFile(displayModeOb);
        if (value == null || Config.DISPLAY_STATE_FOUR.equals(value)) {
            ret = true;
        }
        return ret;
    }

    public  int getDisplayMode(){
        int mode ;
        String value = Utils.readTextFile(displayModeOb);
        mode = Integer.valueOf(value);
        if(mode > Config.QUART_DISPLAY
             || mode < Config.DISABLE_DISPLAY){
            mode =Config.QUART_DISPLAY;
        }
        return mode;
    }



    public void startTakePhoto(String path, int channel) {
        SystemProperties.set(Config.CAPTURE_FILE_NAME, path);
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_CAPTURE,channel);
        bean.Action();
    }

    public void startTakePhoto(int device, int channel) {
        setPhotoLocation(device);
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_CAPTURE,channel);
        bean.Action();
    }

    public String readCaptureFile() {
        return Utils.readTextFile(CAPTURE_FILE);
    }


    public void setRecordMute(boolean mute) {
//        mRecordBase.setRecordMute(mute);
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_AUDIOENABLE, mute ? 0 : 1);
        bean.Action();
    }

    public void setChannelWaterMask(boolean haveWater) {
        CameraBean bean;
        if(haveWater) {
             bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_CHANNELWATERMARK, 1);
        }else{
             bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_CHANNELWATERMARK, 0);
        }
        bean.Action();
    }

    public void setTimeWaterMask(boolean haveWater) {
        CameraBean bean;
        if(haveWater) {
            bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_TIMEWATERMARK, 1);
        }else{
            bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_TIMEWATERMARK, 0);
        }
        bean.Action();
    }

    public void setGPSWaterMask(boolean haveWater) {
        CameraBean bean;
        if(haveWater) {
            bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_GPSWATERMARK, 1);
        }else{
            bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_GPSWATERMARK, 0);
        }
        bean.Action();
    }

    public void setCarWaterMask(boolean haveWater) {
        CameraBean bean;
        if(haveWater) {
            bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_CARDWATERMARK, 1);
        }else{
            bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_CARDWATERMARK, 0);
        }
        bean.Action();
    }

    //车牌水印
    public void setCarNumberToNode(String number) {
        char[] numberArr = number.toCharArray();
        byte[] carNumber = new byte[10];
        for (int i = 0; i < numberArr.length && i < 10; i++) {
            int positon = getIndex(Config.CAR_NUMBER_ARRAY, numberArr[i]);
            carNumber[i] = (byte) positon;

//            LogUtils.d("setCarNumberToNode-----number=" + number + ", i=" + i + ", positon=" + positon + ", str=" + numberArr[i]);
        }

        String value = carNumber[0] + " " + carNumber[1] + " " + carNumber[2] + " " + carNumber[3] + " " + carNumber[4] + " " +
                carNumber[5] + " " + carNumber[6] + " " + carNumber[7] + " " + carNumber[8] + " " + carNumber[0];
        Utils.writeTextFile(value, CAR_NUMBER_DATA);
    }

    private int getIndex(char[] src, char c) {
        int index = 0;
        if (src != null) {
            for (int i = 0; i < src.length; i++) {
                if (src[i] == c) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

//    public void setH264FPS() {
//
//    }
//
//    public void setH264BPS(boolean haveWater) {
//
//    }

    public void disableCameraView() {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_DISPLAY, Config.DISABLE_DISPLAY);
        bean.Action();
    }




    public void enableCameraView() {

        if (getDisplayMode() == Config.DISABLE_DISPLAY) {
            CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_DISPLAY, Config.QUART_DISPLAY);
            bean.Action();
        }
    }




    public void setH264StreamMode(int mode){
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.MODE_WWC2_H264, mode);
        bean.Action();
    }

    public void setCameraMirror(int channel, int mirror) {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, channel, mirror);
        bean.Action();
    }

    public void setRecordQuality(int type) {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_RECORD_BPS, type);
        bean.Action();
    }

    public void setRecordLocation(int deviceId) {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_RECORD_DIR, deviceId);
        bean.Action();
    }

    public void setPhotoLocation(int deviceId) {
        CameraBean bean = new CameraBean(CAMERA_ACTION_NODE, Config.WWC2_CAPTURE_DIR, deviceId);
        bean.Action();
    }

    public void setSensorType(int sensor) {

        Utils.writeTextFile(sensor + "", Config.SENSOR_NODE);

        String test = Utils.readTextFile(Config.SENSOR_NODE);

    }

    public static String getCameraPlatformType() {
        String ret = SystemProperties.get(CAMERA_PLATFORM_TYPE, "undefine");


        return ret;//YDG "quart_stream";//
    }

    public void setCameraPlatformType(Context context, String playformType) {
        SystemProperties.set(CAMERA_PLATFORM_TYPE, playformType);

        Intent intent = new Intent();
        intent.putExtra(Config.VIDEO_SETTINGS, 4);//此值无实际作用，只要不为1000，都会上传。
        intent.setAction(Config.ACTION_VIDEO_SETTINGS);
        context.sendBroadcast(intent);
    }

    public void setCameraParam(int[] value) {
        if (value != null) {
            String params = "";
            for (int i = 0; i < value.length; i++) {
                if (i == value.length - 1) {
                    params += (value[i] + "");
                } else {
                    params += (value[i] + " ");
                }
            }
            Utils.writeTextFile(params, CAMERA_PARAM);

        }
    }
}
