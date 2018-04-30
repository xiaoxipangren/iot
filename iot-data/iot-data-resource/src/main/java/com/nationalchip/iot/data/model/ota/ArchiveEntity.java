package com.nationalchip.iot.data.model.ota;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.NamedEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 1:00 PM
 * @Modified:
 */

@MappedSuperclass
public abstract class ArchiveEntity extends NamedEntity implements IArchiveEntity {
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Comment("标记是否删除")
    @Column(name="deleted")
    private boolean deleted;
}
