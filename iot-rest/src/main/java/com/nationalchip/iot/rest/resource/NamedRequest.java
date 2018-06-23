package com.nationalchip.iot.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/22/18 3:26 PM
 * @Modified:
 */
public abstract class NamedRequest extends BaseRequest {

    private String name;

    @JsonProperty(required = true)
    private String description;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }


    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
