package com.nationalchip.iot.data.model.auth;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/21/18 12:50 PM
 * @Modified:
 */

/**
 * 账户状态
 */
public enum Status {
    REGISTERED("REGISTERED",1),//已注册
    ACTIVED("ACTIVED",2),//已激活
    PENDING("PENDING",3),//待审核
    APPROVED("APPROVED",4);//审核通过

    private String name;
    private int index;

    private Status(String name,int index){
        this.index=index;
        this.name=name;
    }


}
