package com.example.bsproperty.bean;

import java.io.Serializable;
import java.util.List;

public class ShopBean implements Serializable {

    private Long id;
    private String name;
    private String info;
    private String addr;
    private String tel;
    private int sum;
    private Long uid;
    private List<ProductBean> productBeans;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<ProductBean> getProductBeans() {
        return productBeans;
    }

    public void setProductBeans(List<ProductBean> productBeans) {
        this.productBeans = productBeans;
    }

}
