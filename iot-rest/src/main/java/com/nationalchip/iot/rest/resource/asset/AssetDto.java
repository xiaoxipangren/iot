package com.nationalchip.iot.rest.resource.asset;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 5/24/18 6:51 PM
 * @Modified:
 */
public class AssetDto {
    private String description;
    private String name;
    private String category;
    private String sha1;
    private String guide;
    private String version;

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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
