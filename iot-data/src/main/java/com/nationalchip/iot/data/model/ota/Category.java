package com.nationalchip.iot.data.model.ota;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 9:40 AM
 * @Modified:
 */

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.NamedEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 产品类别，
 * 品类
 */
@MappedSuperclass
@Entity
@Table(name = "product_category")
public class Category extends NamedEntity {

    public Category() {
    }

    @Comment("品类的详细描述")
    @Column(name = "detail")
    private String detail;

    @Comment("所拥有的产品")
    @OneToMany(targetEntity = Product.class,mappedBy = "category")
    private Set<Product> products;


    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
