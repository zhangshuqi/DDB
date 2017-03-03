package com.ljwj.ddb.taimian.bean;

import java.io.Serializable;

/**
 * 用户信息类
 * Created by dell on 2017/2/17.
 */
public class UserInfo  {

    private int c;
    private DBean d;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean implements Serializable{
        /**
         * result : 1
         * userid : 2
         * companyid : 3
         * nickname : 叶飞
         * token : 8e1ec413f1ee5f5008d4f5af1b3d9d19
         * hxid : null
         * hxpass :
         * mobile : 13928411884
         */

        private int result;
        private int userid;
        private int companyid;
        private String nickname;
        private String token;
        private Object hxid;
        private String hxpass;
        private String mobile;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getCompanyid() {
            return companyid;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Object getHxid() {
            return hxid;
        }

        public void setHxid(Object hxid) {
            this.hxid = hxid;
        }

        public String getHxpass() {
            return hxpass;
        }

        public void setHxpass(String hxpass) {
            this.hxpass = hxpass;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
