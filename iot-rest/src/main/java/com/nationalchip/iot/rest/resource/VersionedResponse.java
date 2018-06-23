package com.nationalchip.iot.rest.resource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/21/18 4:13 PM
 * @Modified:
 */
public abstract class VersionedResponse extends ArchivedResponse {
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
