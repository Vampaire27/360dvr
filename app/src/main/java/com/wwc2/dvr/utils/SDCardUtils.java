package com.wwc2.dvr.utils;

import android.os.Environment;
import android.os.StatFs;



import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/**
 * SD卡相关的辅助类
 *
 * @see SDCardUtils
 */
public class SDCardUtils {
    //剩余1G开始循环删除
    public static final long LEVEL_3_1G = 1024 * 2; //2G或总容量的10%   开始删除文件.
    //最小剩余容量
    public static final long LEVEL_2_500M = 500 * 10;//500M  提示用户空间不足. //经讨论，此等级去掉2020-08-01
    public static final long LEVEL_1_250M = 1024 * 1;//1G  空间不足,停止录像
    //拍照最小容量,5M
    public static final long MIN_TAKEPICTURE_SIZE = 50;

    private SDCardUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断内置SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }


    /**
     * 检查挂载状态
     *
     * @return true:挂载 false未挂载
     */
    public static boolean checkMountStatus(String name) {
        if (null == name) {
            return false;
        }
        boolean isMounted = false;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(name.substring(0, name.length() - 1))) {
                    String[] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory()) {
                        isMounted = true;
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
        }
        return isMounted;
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定设备的剩余可用容量字节数，单位byte
     *
     * @param storageDevice
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(int storageDevice) {
        if (null != StorageDevice.getPath(storageDevice)) {
            StatFs stat = new StatFs(StorageDevice.getPath(storageDevice));
            long availableBlocks = stat.getAvailableBlocksLong() - 4;
            return stat.getBlockSizeLong() * availableBlocks;
        } else {
            return -1;
        }
    }

    public static long getFreeBytesNew(int storageDevice) {
        File file = new File(StorageDevice.getPath(storageDevice));
        if (null != file) {
            long usableSpace = file.getUsableSpace() / 1048576;//转换为MB
            return usableSpace;
        } else {
            return -1;
        }
    }

    public static long getTotalSpace(int storage) {
        File file = new File(StorageDevice.getPath(storage));
        if (null != file) {
            long total = file.getTotalSpace() / 1048576;//转换为MB
            return total;
        }
        return -1;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath()
                + File.separator;
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }



/*	public static boolean checkFreeBytes() {
		//有外置sd卡则判断外置sd卡
		if (getExtSDCardPath() != null) {
			if (getFreeBytes(OUTSIDE_SD_PATH) > MIN_REMAIN_SIZE) {
				return true;
			} else {

			}
			//没有则判断车机内存
		} else {
			if (getFreeBytes(INSIDE_SD_PATH) > MIN_REMAIN_SIZE) {
				return true;
			} else {

			}
		}
	}*/
}

