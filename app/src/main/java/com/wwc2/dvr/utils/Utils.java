package com.wwc2.dvr.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import androidx.annotation.NonNull;

/**
 * Created by goldze on 2017/5/14.
 * 常用工具类
 */
public final class Utils {

    static final String TAG = "Utils";

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }

    public static String readTextFile(String realPath) {
        File file = new File(realPath);
        if (!file.exists()) {
            Log.d(TAG,"...File not exist!");
            return null;
        }
        String txt = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(realPath), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                txt += temp;
            }
            br.close();
        }catch (Exception e){
            Log.d(TAG,"...readTextFile e=" + e.toString());
        }

        return txt;
    }

    public static void writeTextFile(String tivoliMsg, String fileName) {
        try {
            byte[] bMsg = tivoliMsg.getBytes();
            FileOutputStream fOut = new FileOutputStream(fileName);
            fOut.write(bMsg);
            fOut.getFD().sync();
            fOut.close();
        } catch (IOException e) {
            //throw the exception
        }
    }


    public static Point getDisplaySize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point;
    }


    public static int byteArrayToInt(byte[] valueBuf, int offset) {
        ByteBuffer converter = ByteBuffer.wrap(valueBuf);
        converter.order(ByteOrder.nativeOrder());
        return converter.getInt(offset);
    }

    public static byte[] intToByteArray(int value) {
        ByteBuffer converter = ByteBuffer.allocate(4);
        converter.order(ByteOrder.nativeOrder());
        converter.putInt(value);
        return converter.array();
    }

    public static boolean isImageFile(String path) {
        if (path != null) {
            if (path.contains(".jpg") || path.contains(".png")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 杀死指定包名的进程
     */
    public static boolean killProcess(Context context, String packageName) {
        boolean ret = false;
        if (null == context) {
            Log.w(TAG,"#killProcess failed, because the context is null.");
            return ret;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null != am) {
            try {
                Log.d(TAG,"#killProcess packageName = " + packageName);
                Method forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
                forceStopPackage.setAccessible(true);
                forceStopPackage.invoke(am, packageName);
                ret = true;
            } catch (NoSuchMethodException e) {
                Log.w(TAG,"#killProcess failed, because NoSuchMethodException.");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                Log.w(TAG,"#killProcess failed, because IllegalAccessException.");
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                Log.w(TAG,"#killProcess failed, because InvocationTargetException.");
                e.printStackTrace();
            } catch (Exception e) {
                Log.w(TAG,"#killProcess failed, because Exception.");
                e.printStackTrace();
            }
        } else {
            Log.w(TAG,"#killProcess failed, because the activity manager is null.");
        }
        return ret;
    }
}