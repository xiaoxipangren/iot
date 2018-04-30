package com.nationalchip.iot.data.model.hub;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.auth.User;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 4/8/18 10:21 AM
 * @Modified:
 */

@Entity
@Table(name = "developer")
public class Developer extends User {

    public Developer(){

    }

    public Developer(String username,String password){
        super(username,password);
    }
}
