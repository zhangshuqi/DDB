package com.ljwj.ddb.taimian.utils;

import com.ljwj.ddb.taimian.bean.ClientBean;


/**
 * Created by dell on 2017/2/8.
 */

public class EventBusOfficialUtils {

    private ClientBean newClientBean;

   public EventBusOfficialUtils(ClientBean newClientBean){
        this.newClientBean = newClientBean;
    }

    public ClientBean getNewClientBean(){

        return newClientBean;
    }

}
