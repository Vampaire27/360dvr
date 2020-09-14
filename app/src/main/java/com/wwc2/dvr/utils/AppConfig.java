package com.wwc2.dvr.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class AppConfig {


    private static final String USER_INFO = "user_info";

    public static final String KEY_SENSOR = "Sensor";

    public AppConfig( ) {
    }



    public static void putInt(Context mContext, String key, int value) {
        SharedPreferences pref = mContext.getSharedPreferences(USER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key,value);
        editor.apply();
    }


    public int getIntValue(Context mContext, String key, int value){
        SharedPreferences pref = mContext.getSharedPreferences(USER_INFO,
                Context.MODE_PRIVATE);
        return  pref.getInt(key,value);
    }




    public void put(Context mContext, String key, String value){

        SharedPreferences pref = mContext.getSharedPreferences(USER_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String getValue(Context mContext, String key, String value){
        SharedPreferences pref = mContext.getSharedPreferences(USER_INFO,
                Context.MODE_PRIVATE);
        return  pref.getString(key,value);
    }

    private static class SingletonHolder {
        //由JVM保证只初始化一次
        private static AppConfig instance = new AppConfig();
    }

    public static AppConfig getInstance(){
        return  SingletonHolder.instance;
    }
}
