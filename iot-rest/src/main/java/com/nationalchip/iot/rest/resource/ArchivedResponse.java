package com.nationalchip.iot.rest.resource;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/21/18 4:11 PM
 * @Modified:
 */
public abstract class ArchivedResponse extends NamedResponse {
    private boolean deleted;
    private String tag;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
