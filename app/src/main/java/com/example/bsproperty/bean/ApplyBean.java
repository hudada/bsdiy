package com.example.bsproperty.bean;

import java.io.Serializable;

/**
 * Created by wdxc1 on 2018/3/31.
 */

public class ApplyBean implements Serializable {
    private Long id;
    private Long uid;
    private String addr;
    private String tel;
    private String name;
    private Long time;
    private int status = 0; // 0申请中，1同意，2拒绝
    private String uname;
    private String oldAddr;
    private String oldName;
    private String oldTel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOldAddr() {
        return oldAddr;
    }

    public void setOldAddr(String oldAddr) {
        this.oldAddr = oldAddr;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldTel() {
        return oldTel;
    }

    public void setOldTel(String oldTel) {
        this.oldTel = oldTel;
    }
}
