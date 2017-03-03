package com.ljwj.ddb.taimian.bean;

/**
 * Created by dell on 2017/2/8.
 */

public class ClientBean {

    String name;
    String sex;
    String relation;
    String phone;
    String site;
    String date;
    int state;
    String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRelation() {
        return relation;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSite() {
        return site;
    }

    public String getDate() {
        return date;
    }

}
