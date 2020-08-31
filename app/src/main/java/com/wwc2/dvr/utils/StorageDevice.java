package com.wwc2.dvr.utils;


import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * the storage device.
 *
 * @author xuxiaohong
 * @date 2016/3/26
 */
public class StorageDevice {
    /**
     * the unknown storage device.
     */
    public static final int UNKNOWN = 0;

    /**
     * the nand flash storage device.
     */
    public static final int NAND_FLASH = 1;

    /**
     * the media card storage device.
     */
    public static final int MEDIA_CARD = 2;

    /**
     * the gps card storage device.
     */
    public static final int GPS_CARD = 3;

    /**
     * the usb storage device.
     */
    public static final int USB = 4;

    /**
     * the usb1 storage device.
     */
    public static final int USB1 = 5;

    /**
     * the usb2 storage device.
     */
    public static final int USB2 = 6;

    /**
     * the usb3 storage device.
     */
    public static final int USB3 = 7;

    /**
     * the cd rom storage device.
     */
    public static final int CD_ROM = 8;

    /**
     * the default storage device.
     */
    public static final int DEFAULT = USB;

    /**
     * All storage device containers
     */
    private static final Map<Integer, String> mStorageDevices = new ConcurrentHashMap<>();

    public static final String NOT_MOUNTED = "not_mounted";

    static {
        mStorageDevices.put(NAND_FLASH, "/storage/emulated/0/");
        mStorageDevices.put(MEDIA_CARD, "/storage/sdcard1/");
        mStorageDevices.put(GPS_CARD, "/storage/sdcard2/");
        mStorageDevices.put(USB, "/storage/usbotg/");
        mStorageDevices.put(USB1, "/storage/usbotg1/");
        mStorageDevices.put(USB2, "/storage/usbotg2/");
        mStorageDevices.put(USB3, "/storage/usbotg3/");
        mStorageDevices.put(CD_ROM, "/storage/cdrom/");
    }

    /**
     * 获取TF路径
     *
     * @param con
     * @return
     */
    public static String getMediaPath(Context con) {
        String path = NOT_MOUNTED;
        StorageManager storageManager = (StorageManager)
                con.getSystemService(Context.STORAGE_SERVICE);
        try {
            Method getVolumeList = storageManager.getClass().getMethod("getVolumeList");
            Class<?> storageValumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getPath = storageValumeClazz.getMethod("getPath");
            Method isRemovable = storageValumeClazz.getMethod("isRemovable");
            Object invokeVolumeList = getVolumeList.invoke(storageManager);
            int length = Array.getLength(invokeVolumeList);
            for (int i = 0; i < length; i++) {
                Object storageValume = Array.get(invokeVolumeList, i);//得到StorageVolume对象
                path = (String) getPath.invoke(storageValume);
                boolean removable = (Boolean) isRemovable.invoke(storageValume);
//                LogUtils.i(TAG, "------removable="+ removable +"--------path="+path);
                if (removable && !path.contains("usb")) {
                    return path + File.separator;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NOT_MOUNTED;
    }

    /**
     * convent the storage to string.
     */
    public static String toString(int storage) {
        String string = "null";
        for (Map.Entry<Integer, String> entry : mStorageDevices.entrySet()) {
            if (storage == entry.getKey()) {
                string = entry.getValue();
                break;
            }
        }
        return string;
    }

    /**
     * 获取设备路径
     */
    public static String getPath(Context context, int storage) {
        String env = null;

        for (Map.Entry<Integer, String> entry : mStorageDevices.entrySet()) {
            if (storage == entry.getKey()) {
                if (storage == MEDIA_CARD) {
                    env = getMediaPath(context);
                } else {
                    env = entry.getValue();
                }
                break;
            }
        }

        return env;
    }

    /**
     * 获取设备路径
     */
    public static String getPath(int storage) {
        String env = null;
        String ret = null;

        for (Map.Entry<Integer, String> entry : mStorageDevices.entrySet()) {
            if (storage == entry.getKey()) {
                env = entry.getValue();
                break;
            }
        }
/*
            if (null != env) {
                final File file = Environment.getExternalStorageDirectory(env);
                if (null != file) {
                    final String path = file.getAbsolutePath();
                    if (!TextUtils.isEmpty(path)) {
                        ret = path + "/";
                    }
                }
            }
*/
        ret = env;
        return ret;
    }

    /**
     * Check whether the letter has been successfully mount
     */
    public static boolean isDiskMounted(Context context, String path) {
        boolean ret = false;
        if (null != path) {
            if (null != context) {
                StorageManager mStorageManager = (StorageManager) context.getSystemService(Activity.STORAGE_SERVICE);
                if (null != mStorageManager) {
                    final int size = path.length();
                    if (size > 1) {
                        final String _path = path.substring(0, size - 1);
                        Class classMethod;
                        Method method;
                        Object object;
                        try {
                            classMethod = Class.forName(StorageManager.class.getName());
                            if (null != classMethod) {
                                method = classMethod.getMethod("getVolumeState", String.class);
                                if (null != method) {
                                    object = method.invoke(mStorageManager, _path);
                                    if (object instanceof String) {
                                        String state = (String) object;
                                        ret = Environment.MEDIA_MOUNTED.equals(state);
                                        if (ret) {
                                            File file = new File(path);
                                            ret = file.exists();
                                        }
                                    }
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Get all storage devices
     */
    public static List<Integer> getAllStorageDevices() {
        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : mStorageDevices.entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    /**
     * Get all storage devices that have been mount
     */
    public static List<Integer> getAllMountStorageDevices(Context context) {
        List<Integer> ret = new ArrayList<>();
        List<Integer> list = getAllStorageDevices();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                final int mount_storage = list.get(i);
                if (isDiskMounted(context, mount_storage)) {
                    ret.add(mount_storage);
                }
            }
        }
        return ret;
    }

    /**
     * Access to all storage devices
     */
    public static int getStorageDeviceNum() {
        return mStorageDevices.size();
    }

    /**
     * Gets the number of storage devices that have been mount
     */
    public static int getMountStorageDeviceNum(Context context) {
        int total = 0;
        List<Integer> list = getAllMountStorageDevices(context);
        if (null != list) {
            total = list.size();
        }
        return total;
    }

    /**
     * Gets a storage device under the specified device
     *
     * @param storage The specified storage device, if {@link #UNKNOWN}, no longer retrieved
     * @return Next storage device
     */
    public static int getNextStorageDevice(int storage) {
        final List<Integer> list = getAllStorageDevices();
        return getNextStorageDevice(list, storage);
    }

    /**
     * Gets a storage device that has mount under the specified device, and if there is no mount
     * {@link #getAllStorageDevices()}Get the next one.
     *
     * @param storage The specified storage device, if for{@link #UNKNOWN},Is no longer retrieved
     * @return Next mount storage device
     */
    public static int getNextMountStorageDevice(Context context, int storage) {
        boolean mount_ret = false;
        while (!isDiskMounted(context, storage)) {
            mount_ret = true;
            storage = getNextStorageDevice(storage);
        }
        if (mount_ret) {
            return storage;
        } else {
            final List<Integer> list = getAllMountStorageDevices(context);
            return getNextStorageDevice(list, storage);
        }
    }

    /**
     * Get the first mount successful equipment
     */
    public static int getFirstMountStorageDevice(Context context) {
        final int storage = DEFAULT;
        if (isDiskMounted(context, storage)) {
            return storage;
        } else {
            return getNextMountStorageDevice(context, storage);
        }
    }

    /**
     * Gets the storage device for the specified device in the list of the specified storage devices
     *
     * @param list    List of storage devices
     * @param storage The specified storage device, if for{@link #UNKNOWN}, is no longer retrieval
     * @return Next storage device
     */
    private static int getNextStorageDevice(List<Integer> list, int storage) {
        int ret = UNKNOWN;
        if (UNKNOWN != storage) {
            if (null != list) {
                int index = -1;
                final int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (storage == list.get(i)) {
                        index = i;
                        break;
                    }
                }
                if (-1 != index) {
                    if (index + 2 <= size) {
                        ret = list.get(index + 1);
                    } else {
                        ret = list.get(0);
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Whether the incoming ID is a storage device
     */
    public static boolean isStorageDevice(int storage) {
        boolean ret = false;
        for (Map.Entry<Integer, String> entry : mStorageDevices.entrySet()) {
            if (storage == entry.getKey()) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * Check the device name is mount successful
     */
    public static boolean isDiskMounted(Context context, int storage) {
        String path = getPath(storage);
        if (null != path) {
            return isDiskMounted(context, path);
        }
        return false;
    }

    /**
     * Parse the incoming file or directory to which device
     */
    public static int parseFileOrDirName(Context context, String name) {
        int storage = UNKNOWN;
        if (null != name) {
            for (Map.Entry<Integer, String> entry : mStorageDevices.entrySet()) {
                final int _storage = entry.getKey();
                final String path = getPath(context, _storage);
                if (null != path) {
                    if (name.startsWith(path)) {
                        storage = _storage;
                        break;
                    }
                }
            }
        }
        return storage;
    }
}
