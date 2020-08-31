package com.wwc2.dvr.fourCamera;

import com.wwc2.dvr.utils.Config;

public  class QuartRecord extends RecordBase{

    final static String record_status = "/sys/devices/platform/wwc2_camera_combine/record_status";

    public void startRecord() {
        CameraBean bean = new CameraBean(FourCameraProxy.CAMERA_ACTION_NODE, Config.MODE_WWC2_RECORD, Config.QUART_RECORD);
        bean.Action();
    }

    @Override
    protected boolean isRecorded() {
        boolean ret = false;
        String status = driveRead(record_status);
        if(Config.RECORD_STATUS_START.equals(status) ||
                Config.RECORD_STATUS_RUNING.equals(status)){
            ret = true;
        }
        return ret;
    }

    @Override
    protected boolean isRecorded(int type) {
        boolean ret = false;
        String status = driveRead(record_status);
        if(Config.RECORD_STATUS_START.equals(status) ||
                Config.RECORD_STATUS_RUNING.equals(status)){
            ret = true;
        }
        return ret;
    }

    @Override
    protected String getCode() {
        return driveRead(record_status);
    }
}
