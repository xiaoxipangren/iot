package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 4:37 PM
 * @Modified:
 */
@Table(name = "asset")
@Entity
public class Asset extends FiledEntity implements IAsset {


    @Column(name="guide")
    @Comment("帮助手册url")
    private String guide;



    public void setGuide(String guide) {
        this.guide = guide;
    }


    @Override
    public String getGuide() {
        return guide;
    }



}
