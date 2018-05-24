package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.InputStream;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/23/18 4:37 PM
 * @Modified:
 */
@Table(name = "resource")
@Entity
public class Resource extends FiledEntity implements IResource {


    @Column(name="guide")
    @Comment("帮助手册url")
    private String guide;

    @Column(name="category")
    @Comment("资源类别")
    private String category;



    public void setGuide(String guide) {
        this.guide = guide;
    }


    @Override
    public String getGuide() {
        return guide;
    }

    @Override
    public String getCatagory() {
        return null;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
