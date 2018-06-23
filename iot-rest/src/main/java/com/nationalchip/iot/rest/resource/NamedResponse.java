package com.nationalchip.iot.rest.resource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/21/18 4:10 PM
 * @Modified:
 */
public abstract class NamedResponse extends BaseResponse {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
