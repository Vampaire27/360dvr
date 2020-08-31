package com.wwc2.dvr.bean;

/**
 * description ： TODO:类的作用
 * user: wangpeng on 2019/9/26.
 * emai: wpeng@waterworld.com.cn
 */
public class DriveVideoBean {
    private String name;
    private String url;
    private boolean lockStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }
}
