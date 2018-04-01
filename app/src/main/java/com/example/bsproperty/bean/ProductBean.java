package com.example.bsproperty.bean;

import java.io.Serializable;

public class ProductBean implements Serializable {

    private Long id;
    private String name;
    private String img;
    private int sum = 0;
    private boolean activity;
    private double actProce;
    private double price;
    private String info;
    private Long sid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    public double getActProce() {
        return actProce;
    }

    public void setActProce(double actProce) {
        this.actProce = actProce;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

}
