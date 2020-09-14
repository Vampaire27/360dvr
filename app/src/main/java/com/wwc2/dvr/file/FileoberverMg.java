package com.wwc2.dvr.file;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.FileObserver;
import android.util.Log;

import com.wwc2.dvr.room.FileInfo;
import com.wwc2.dvr.room.FileRepository;
import com.wwc2.dvr.utils.StorageDevice;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FileoberverMg implements MultiFileObserver.FileListener {

    private String TAG = "FileoberverMg";
    private FileRepository mFileRepository;
    private Context mContext;

    private final String DVR_DIR = "DCIM/Camera/";

    private final Executor mFileListExecutor = Executors.newSingleThreadExecutor();

    /**
     * the nand flash storage device.
     */
    public static final int NAND_FLASH = 0;
    public static final int MEDIA_CARD = 1;
    public static final int MEDIA_USB = 2;
    public static final int MEDIA_USB1 = 3;
    public static final int MEDIA_USB2 = 4;
    public static final int MEDIA_USB3 = 5;

    List<MultiFileObserver> mMultiFileObserverList = new ArrayList<>();


    private static final Map<Integer, String> mObserverDir = new HashMap<>();




    public FileoberverMg( FileRepository fileRepository,Context ctx) {
        mFileRepository = fileRepository;
        mObserverDir.put(NAND_FLASH, "/storage/emulated/0/" + DVR_DIR);
        mObserverDir.put(MEDIA_CARD, "/storage/sdcard1/" + DVR_DIR );
        mObserverDir.put(MEDIA_USB, "/storage/usbotg/" + DVR_DIR );
        mObserverDir.put(MEDIA_USB1, "/storage/usbotg1/" + DVR_DIR );
        mObserverDir.put(MEDIA_USB2, "/storage/usbotg2/" + DVR_DIR);
        mObserverDir.put(MEDIA_USB3, "/storage/usbotg3/" + DVR_DIR);
        mContext = ctx;
        Log.i(TAG, "FileoberverMg....");
    }


    public void startWatching() {
        MultiFileObserver multiFileObserver;
        for(int i = 0; i< mObserverDir.size(); i ++ ){

            String path = mObserverDir.get(i);
            String mountPoint = path.substring(0,path.length() - DVR_DIR.length());
            if(StorageDevice.isDiskMounted(mContext,mountPoint)) {
                multiFileObserver = new MultiFileObserver(i, mObserverDir.get(i)/*getVideoDir(location)*/,
                        FileObserver.DELETE | FileObserver.CREATE | FileObserver.DELETE_SELF);

                multiFileObserver.setFileListener(this);
                Log.i(TAG, "startWatching....path =" + mObserverDir.get(i));
                File file = new File(mObserverDir.get(i));
                if (!file.exists()) {
                    file.mkdir();
                }
                multiFileObserver.startWatching();
                mMultiFileObserverList.add(multiFileObserver);
            }else {
                Log.d(TAG,"Storage is not mount:" + mountPoint);
            }
        }
        Log.i(TAG, "startWatching....");
    }

    public void stopWatch(){
        for(int i = 0; i< mMultiFileObserverList.size(); i ++ ){
            MultiFileObserver multiFileObserver = mMultiFileObserverList.get(i);
            if(multiFileObserver != null) {
                multiFileObserver.stopWatching();
            }
        }
        mMultiFileObserverList.clear();
    }

    @Override
    public void onFileCreated(int storageId, String name) {
        Log.i(TAG, "onFileCreated storageId = " + storageId + " name " + name);
        Log.i(TAG, "onFileCreated storageId = " + storageId + " path " + mObserverDir.get(storageId));
        mFileRepository.insert(new FileInfo(name,mObserverDir.get(storageId)));
    }

    @Override
    public void onFileDeleted(int storageId, String name) {
        Log.i(TAG, "onFileDeleted storageId = " + storageId + " name " + name);
        mFileRepository.delete(name);
    }


    @Override
    public void onFileDeleteSelf(int storageId, String name) {
        Log.i(TAG, "onFileDeleteSelf storageId = " + storageId + " name " + name);
        mFileRepository.delete(name);
    }

    public void removeDevice(String mountPoint){
        Log.i(TAG, "onFileDeleteSelf path= " + mountPoint + DVR_DIR );
        mFileRepository.deleteDevice(mountPoint +DVR_DIR);
    }


   public void updateFileDB(String mountPoint){ //PATH
       mFileListExecutor.execute(new Runnable() {
           @Override
           public void run() {
               String Path = mountPoint +DVR_DIR;
               File mFile = new File(Path);
               File[] subFiles = mFile.listFiles();
               if(subFiles != null){
                   for(File file:subFiles){
                       Log.i(TAG, "updateFileDB  Path =" +Path + " name " + file.getName());
                       mFileRepository.insert(new FileInfo(Path+file.getName(),Path));
                   }
               }
               stopWatch();
               startWatching();
           }
       });
   }

}
