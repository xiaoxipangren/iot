package com.nationalchip.iot.rest.resource;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/22/18 3:49 PM
 * @Modified:
 */
public abstract class VersionedRequest extends ArchivedRequest {
    private String version;

    public Optional<String> getVersion() {
        return Optional.ofNullable(version);
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
