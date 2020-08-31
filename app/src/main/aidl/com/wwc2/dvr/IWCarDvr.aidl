// IWCarDvr.aidl
package com.wwc2.dvr;

import com.wwc2.dvr.DeleteVideoCallBack;

interface IWCarDvr {

    //创建预览
    void createDvr();
    //获取预览状态
    //id=摄像头ID,1前,2后
    boolean getDvrStatus(int id);

    //停止
    boolean stopDvr();

    //返回值 return 拍照的全路径
    //参数 channel：通道id 0-1  1：front  2：back
    //isOff：true=off，false=on
    String takePicture(boolean isOff, int channel);

    //fromType 1:前置 2;后置  pageNum:第几页 pageSize:每页多少条数据
    String getAllDriveVideo(int fromType ,int pageNum , int pageSize);

    //G_Sensor
    String postSensor(boolean isOff, boolean syncStatus);
    void updateSensor();

    //关闭
    void close();
 /*   //获取当前摄像头是否支持实时视频
    boolean canTakeVideo(int channel);

    //获取当前摄像头是否支持拍照
    boolean canTakePickture(int channel);*/

    int getTakeCameraStatus();//0:获取异常 1:单录 2:双录

    void setH264Mode(int mode);

    void deleteFile(String path,DeleteVideoCallBack callBack);
}
