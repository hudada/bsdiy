package com.example.bsproperty.bean;

import java.io.Serializable;

/**
 * Created by wdxc1 on 2018/3/28.
 */

public class OrderBean implements Serializable {
    private Long id;
    private String title;
    private String sname;
    private String saddr;
    private double price;
    private double actPrice;
    private Long time;
    private String diyInfo;
    private String img;
    private Long uid;
    private Long pid;
    private Long suid;
    private String uname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSaddr() {
        return saddr;
    }

    public void setSaddr(String saddr) {
        this.saddr = saddr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getActPrice() {
        return actPrice;
    }

    public void setActPrice(double actPrice) {
        this.actPrice = actPrice;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDiyInfo() {
        return diyInfo;
    }

    public void setDiyInfo(String diyInfo) {
        this.diyInfo = diyInfo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getSuid() {
        return suid;
    }

    public void setSuid(Long suid) {
        this.suid = suid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
