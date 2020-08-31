package com.wwc2.dvr.bean;


import java.util.List;

/**
 * description ： TODO:类的作用
 * user: wangpeng on 2019/9/26.
 * emai: wpeng@waterworld.com.cn
 */
public class ResultDriveVideoBean {
    private String code;
    private String msg;
    private int total; //视频总数

    private List<DriveVideoBean> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DriveVideoBean> getList() {
        return list;
    }

    public void setList(List<DriveVideoBean> list) {
        this.list = list;
    }
}
