package com.nationalchip.iot.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/19/18 2:32 PM
 * @Modified:
 */
public abstract class BaseResponse extends ResourceSupport implements IResponse {
    private Date createdAt;
    private String createdBy;
    private Date lastModifiedAt;
    private String lastModifiedBy;

    @JsonProperty("id")
    private Long identity;

    @Override
    public Long getIdentity() {
        return identity;
    }

    public void setIdentity(long identity) {
        this.identity = identity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
