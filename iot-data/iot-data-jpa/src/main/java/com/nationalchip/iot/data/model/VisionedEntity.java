package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 1:48 PM
 * @Modified:
 */

@MappedSuperclass
public abstract class VisionedEntity extends ArchiveEntity implements IVersionedEntity {

    @Comment("版本号")
    @Column(name = "version")
    @NotNull
    private String version;

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
