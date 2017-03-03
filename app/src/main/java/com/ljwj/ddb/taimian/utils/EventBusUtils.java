package com.ljwj.ddb.taimian.utils;

import com.ljwj.ddb.taimian.bean.ClientBean;

import java.util.List;


/**
 * Created by dell on 2017/2/8.
 */

public class EventBusUtils {

    private ClientBean newClientBean;
    private String string;

   public EventBusUtils(ClientBean newClientBean){
        this.newClientBean = newClientBean;
    }

    public ClientBean getNewClientBean(){

        return newClientBean;
    }

}
