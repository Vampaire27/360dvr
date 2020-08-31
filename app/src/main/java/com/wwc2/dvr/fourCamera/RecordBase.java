package com.wwc2.dvr.fourCamera;


import android.util.Log;

import com.wwc2.dvr.utils.Utils;
import com.wwc2.dvr.utils.Config;

public abstract class RecordBase {


    private void driveWrite(String node, int value) {
        Utils.writeTextFile(String.valueOf(value),node);
        Log.d("CameraBean","driveWrite "+ node +" = "+value);
    }


    protected String driveRead(String node) {
        String value = Utils.readTextFile(node);
        Log.d("CameraBean","driveRead "+ node +" = "+value);
        return value;
    }

    public void setRecordMute(boolean mute) {
        if(mute){
            driveWrite(FourCameraProxy.audio_enable,0);
        }else{
            driveWrite(FourCameraProxy.audio_enable,1);
        }
    }


    public void stopRecord() {
        CameraBean bean = new CameraBean(FourCameraProxy.CAMERA_ACTION_NODE, Config.MODE_WWC2_RECORD, Config.STOP_RECORD);
        bean.Action();
    }



    public void setRecordLatency(int time) {
        driveWrite(FourCameraProxy.record_latency,time);
    }


    public void setRecordWaterMask(boolean haveWater) {
        if(haveWater){
            driveWrite(FourCameraProxy.water_mask,1);
        }else{
            driveWrite(FourCameraProxy.water_mask,0);
        }
    }

    protected abstract  void startRecord();

    protected abstract boolean isRecorded();

    protected abstract boolean isRecorded(int type);

    protected abstract String getCode();

}
