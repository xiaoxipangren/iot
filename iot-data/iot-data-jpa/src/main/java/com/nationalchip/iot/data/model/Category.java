package com.nationalchip.iot.data.model;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 9:40 AM
 * @Modified:
 */

import com.nationalchip.iot.data.annotation.Comment;
import javax.persistence.*;
import java.util.Set;

/**
 * 产品类别，
 * 品类
 */
@MappedSuperclass
@Entity
@Table(name = "product_category")
public class Category extends NamedEntity implements ICategory {

    public Category() {
    }

    @Comment("品类的编码")
    @Column(name = "code")
    private String code;

    @Comment("品类的详细描述")
    @Column(name = "detail")
    private String detail;

    @Comment("所拥有的产品")
    @OneToMany(targetEntity = Product.class,mappedBy = "category")
    private Set<Product> products;


    public String getDetail() {
        return detail;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
