package com.wwc2.dvr;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemProperties;
import android.util.Log;

import com.wwc2.dvr.file.FileoberverMg;
import com.wwc2.dvr.fourCamera.FourCameraProxy;

import androidx.annotation.Nullable;

import com.wwc2.dvr.room.FileRepository;

public class RecordService extends Service {
    private static final String TAG = "RecordService";

    public static final String AUTHORITY = "com.wwc2.main.provider.logic";
    public static final String ACC_STATUS = "acc_status";
    private Binder mBinder;
    private FourCameraProxy mFourCameraProxy;
    private  FileRepository mFileRepository;

    private FileoberverMg mFileoberverMg;

    @Override
    public void onCreate() {
        super.onCreate();
        mFourCameraProxy =  new FourCameraProxy();
        Log.d(TAG," onCreate Builder DB");

        mFileRepository = new FileRepository(getApplication());

        mFileoberverMg = new FileoberverMg(mFileRepository);
        mFileoberverMg.startWatching();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d(TAG,"-----onBind  networks----------------->");
        mBinder = new CarDvrImpl(this, RecordService.this);
        return mBinder;


    }

    public FourCameraProxy getFourCameraProxy(){
          if(mFourCameraProxy ==null) {
              mFourCameraProxy = new FourCameraProxy();
          }
          return mFourCameraProxy;
        }

    public FileRepository getFileRepository() {
        return mFileRepository;
    }


    public void startAVM360(){
        if(!getAccState()) {
            ComponentName online = new ComponentName("com.baony.avm360", "com.baony.ui.activity.AVMBVActivity");
            Intent mIntent = new Intent();
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mIntent.setComponent(online);
            RecordService.this.startActivity(mIntent);


            SystemProperties.set("wwc2.camera.running","0");
            mFourCameraProxy.getAVM360Stat();

            int times =20;
            String camera = "0";
            String avm360 = "0";

           while(times-- >= 0 ){
               try {
                   Thread.sleep(250);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }

               camera = SystemProperties.get("wwc2.camera.running","0");
               avm360 = SystemProperties.get("wwc2.avm360cb.running","0");

               if("1".equals(camera) && "1".equals(avm360 )){
                   Log.d(TAG,"AVM 360 camera is open !");
                       break;
               }
           }
        }else{
            Log.d(TAG,"acc on state has camera is open !");
        }
    }

    public   boolean getAccState(){
        boolean ret = false;

        Uri uri_acc = Uri.parse("content://" + AUTHORITY
                + "/" + ACC_STATUS);
        String strAcc =RecordService.this.getContentResolver().getType(uri_acc);

        Log.d(TAG,"updateAccStateFromContentResolover strAcc:"+ strAcc );
        if("true".equals(strAcc)){
            ret = true;
        }
        return ret;
    }

}
