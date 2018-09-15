package com.nationalchip.iot.rest.resource;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 9/4/18 10:40 AM
 * @Modified:
 */
public class DocumentRequest extends NamedRequest {
    private String url;


    public Optional<String> getUrl() {
        return Optional.ofNullable(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
