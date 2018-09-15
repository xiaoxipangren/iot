package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 9:38 AM
 * @Modified:
 */

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.auth.Developer;

import javax.persistence.*;

/**
 * 产品定义
 * 产品由厂商定义，产品是类，设备是对象
 */
@Entity
@MappedSuperclass
@Table(name = "product")
public class Product extends NamedEntity {

    public Product() {
    }

    @Comment("产品详细描述")
    @Column(name = "detail")
    private String detail;

    @Comment("所属的产品大类")
    @ManyToOne(targetEntity = Category.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_category",nullable = true,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Category category;

    @Comment("建立产品的厂商")
    @ManyToOne(targetEntity = Developer.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "owned_by",nullable = true,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Developer developer;


}
