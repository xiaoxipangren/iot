package com.nationalchip.iot.rest.model.resource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 6:51 PM
 * @Modified:
 */
public class ResourceDto {
    private String description;
    private String name;
    private String category;
    private String sha1;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }
}
