package com.nationalchip.iot.rest.resource;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/22/18 4:05 PM
 * @Modified:
 */
public class AssetRequest extends FiledRequest {
    private String guide;

    public Optional<String> getGuide() {
        return Optional.ofNullable(guide);
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }



}
