package com.nationalchip.iot.rest.resource;

import java.util.Optional;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/22/18 3:49 PM
 * @Modified:
 */
public abstract class ArchivedRequest extends NamedRequest{
    private boolean deleted;
    private String tag;

    public Optional<Boolean> isDeleted() {
        return Optional.ofNullable(deleted);
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Optional<String> getTag() {
        return Optional.ofNullable(tag);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
