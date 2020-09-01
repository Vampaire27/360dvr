package com.wwc2.dvr.file;

import android.os.FileObserver;
import android.util.Log;

import com.wwc2.dvr.room.FileInfo;
import com.wwc2.dvr.room.FileRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileoberverMg implements MultiFileObserver.FileListener {

    private String TAG = "FileoberverMg";
    private FileRepository mFileRepository;

    private final String DVR_DIR = "DCIM/Camera/";

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




    public FileoberverMg( FileRepository fileRepository) {
        mFileRepository = fileRepository;
        mObserverDir.put(NAND_FLASH, "/storage/emulated/0/" + DVR_DIR);
        mObserverDir.put(MEDIA_CARD, "/storage/sdcard1/" + DVR_DIR );
        mObserverDir.put(MEDIA_USB, "/storage/usbotg/" + DVR_DIR );
        mObserverDir.put(MEDIA_USB1, "/storage/usbotg1/" + DVR_DIR );
        mObserverDir.put(MEDIA_USB2, "/storage/usbotg2/" + DVR_DIR);
        mObserverDir.put(MEDIA_USB3, "/storage/usbotg3/" + DVR_DIR);
        Log.i(TAG, "FileoberverMg....");
    }


    public void startWatching() {
        MultiFileObserver multiFileObserver;
        for(int i = 0; i< mObserverDir.size(); i ++ ){
            multiFileObserver =new MultiFileObserver(i, mObserverDir.get(i)/*getVideoDir(location)*/,
                    FileObserver.DELETE | FileObserver.CREATE | FileObserver.DELETE_SELF );

            multiFileObserver.setFileListener(this);
            Log.i(TAG, "startWatching....path =" +mObserverDir.get(i));
            File file = new File(mObserverDir.get(i));
            if(!file.exists()){
                file.mkdir();
            }
            multiFileObserver.startWatching();
            mMultiFileObserverList.add(multiFileObserver);
        }
        Log.i(TAG, "startWatching....");
    }

    @Override
    public void onFileCreated(int storageId, String name) {
        Log.i(TAG, "onFileCreated storageId = " + storageId + " name " + name);
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
}
