package com.wwc2.dvr;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;
import com.wwc2.dvr.bean.DriveVideoBean;
import com.wwc2.dvr.bean.ResultDriveVideoBean;
import com.wwc2.dvr.room.FileInfo;
import com.wwc2.dvr.utils.Config;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by huwei on 19-3-4.
 */
public class CarDvrImpl extends IWCarDvr.Stub {
    private static final String TAG = "CarDvrImpl";
    private RecordService recordService;
    private Context mContext;

    public CarDvrImpl(RecordService recordService, Context context) {
        this.recordService = recordService;
        this.mContext = context;
    }

    @Override
    public void createDvr() throws RemoteException {
        recordService.startAVM360();
    }

    @Override
    public boolean getDvrStatus(int id) throws RemoteException {
        Log.d(TAG,"CarDvrImpl---getDvrStatus-->");
        return false;
    }

    @Override
    public boolean stopDvr() throws RemoteException {
        Log.d(TAG ,"CarDvrImpl---stopDvr-->");

        return true;
    }


    private boolean checkState(){
       return true;
    }

    @Override
    public String takePicture(boolean isOff, int channel) throws RemoteException {
        String fileCapture = null;
        Log.d(TAG ,"CarDvrImpl---takePicture  isOff-->" + isOff);
        if(checkState()){
            recordService.startAVM360();
            recordService.getFourCameraProxy().startTakePhoto(Config.DIR_LOCAL, channel);
            fileCapture = recordService.getFourCameraProxy().readCaptureFile();

        };
        return fileCapture; //recordService.takePicture(isOff,channel, true);
    }




    @Override
    public String getAllDriveVideo(int fromType , int pageNum , int pageSize) throws RemoteException {
        Log.d(TAG ,"CarDvrImpl---getAllDriveFontVideo fromType=" + ", pageNum-->" + pageNum + "    pageSize-->" + pageSize);
        Gson gson=new Gson();

        int size = recordService.getFileRepository().getSize();

        if(size == 0){
            return null;
        }

        ResultDriveVideoBean fontVideoBean = new ResultDriveVideoBean();
        List<DriveVideoBean> mDriveVideoBeanList = new ArrayList<>();

        List<FileInfo> fileinfolist = recordService.getFileRepository().getFileInfoList((pageNum-1)*pageSize,pageSize);

        for (FileInfo file:fileinfolist){
            DriveVideoBean mDriveVideoBean =new DriveVideoBean();
            mDriveVideoBean.setLockStatus(false);
            mDriveVideoBean.setUrl(file.getName());
            mDriveVideoBean.setName( file.getName().substring(file.getName().lastIndexOf("/")).replaceAll("/",""));
            mDriveVideoBeanList.add(mDriveVideoBean);
        }
        fontVideoBean.setTotal(size);
        fontVideoBean.setList(mDriveVideoBeanList);

        String fontJson =  gson.toJson(fontVideoBean);


        Log.d(TAG,"getAll " + fontJson);
        return fontJson;
    }

    @Override
    public void close() throws RemoteException {

    }

    @Override
    public int getTakeCameraStatus() throws RemoteException {
        return 1;
    }

  /*  @Override
    public boolean canTakeVideo(int channel) throws RemoteException {
        return recordService.canTakeVideo(channel);
    }

    @Override
    public boolean canTakePickture(int channel) throws RemoteException {

        return recordService.canTakePickture(channel);
    }*/

    @Override
    public String postSensor(boolean isOff, boolean syncStatus) throws RemoteException {
        return TAG;
    }

    @Override
    public void updateSensor() throws RemoteException {
        recordService.updateSensor();
    }

    @Override
    public void setH264Mode(int mode) throws RemoteException {
        recordService.getFourCameraProxy().setH264StreamMode(mode);
    }

    @Override
    public void deleteFile(String path, DeleteVideoCallBack callBack) throws RemoteException {

    }
}
