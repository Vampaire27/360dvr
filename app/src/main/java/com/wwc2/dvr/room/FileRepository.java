package com.wwc2.dvr.room;

import android.app.Application;

import java.util.List;

public class FileRepository {
    private FileInfoDao mFileInfoDao;
    private List<FileInfo> mFileInfo;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public FileRepository(Application application) {
        FileInfoDatabase db = FileInfoDatabase.getDatabase(application);
        mFileInfoDao = db.fileInfoDao();
        mFileInfo = mFileInfoDao.getAlphabetizedName();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public List<FileInfo> getAllWords() {
        return mFileInfo;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(FileInfo fileInfo) {
        FileInfoDatabase.databaseWriteExecutor.execute(() -> {
            mFileInfoDao.insert(fileInfo);
        });
    }

    public void delete(String path) {
        FileInfoDatabase.databaseWriteExecutor.execute(() -> {
            mFileInfoDao.deletePath(path);
        });
    }

    public List<FileInfo>  getFileInfoList(int start , int size) {
       // FileInfoDatabase.databaseWriteExecutor.execute(() -> {
           return mFileInfoDao.getFileInfo(start,size);
       // });
    }

}
