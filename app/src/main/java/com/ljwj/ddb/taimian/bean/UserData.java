package com.ljwj.ddb.taimian.bean;

/**用户回话信息
 * Created by dell on 2017/2/23.
 */

public class UserData {

    /**
     * c : 0
     * d : {"uid":2,"nickname":"叶飞","img":null,"companyid":3}
     */

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

    public static class DBean {
        /**
         * uid : 2
         * nickname : 叶飞
         * img : null
         * companyid : 3
         */

        private int uid;
        private String nickname;
        private Object img;
        private int companyid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public int getCompanyid() {
            return companyid;
        }

        public void setCompanyid(int companyid) {
            this.companyid = companyid;
        }
    }
}
