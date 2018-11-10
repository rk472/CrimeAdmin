package com.smarttersstudio.crimeadmin.pojo;

public class complain {
    String title,desc,pin,uid,status,date;

    public complain() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitile(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public complain(String titile, String desc, String pin, String uid, String status, String date) {

        this.title = titile;
        this.desc = desc;
        this.pin = pin;
        this.uid = uid;
        this.status = status;
        this.date = date;
    }
}
