package com.ljwj.ddb.taimian.bean;

import java.io.Serializable;

/**
 * Created by dell on 2017/2/23.
 */

public class ADDClientBean implements Serializable{

    /**
     * c : 0
     * d : {"result":1,"customerid":3}
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
         * result : 1
         * customerid : 3
         */

        private int result;
        private int customerid;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getCustomerid() {
            return customerid;
        }

        public void setCustomerid(int customerid) {
            this.customerid = customerid;
        }
    }
}
