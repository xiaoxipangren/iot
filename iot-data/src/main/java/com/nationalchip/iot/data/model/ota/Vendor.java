package com.nationalchip.iot.data.model.ota;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.auth.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 设备制造商
 */

@Entity
@Table(name = "vendor")
class Vendor extends User {

    @Column(name="eng_name")
    @Comment("厂商英文名称")
    @NotNull
    @Max(16)
    private String englishName;


    @Column(name = "linkman")
    @Comment("厂商联系人")
    @NotNull
    private String linkman;

    @Column(name = "website")
    @Comment("厂商网站")
    private String website;


    @Comment("拥有的产品")
    @OneToMany(mappedBy = "vendor",targetEntity = Product.class)
    private Set<Product> products;

    @Comment("拥有的设备")
    @OneToMany(mappedBy = "vendor",targetEntity = Device.class)
    private Set<Device> devices;


    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
