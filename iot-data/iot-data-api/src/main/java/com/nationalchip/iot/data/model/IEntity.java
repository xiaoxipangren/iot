package com.nationalchip.iot.data.model;

import org.springframework.hateoas.Identifiable;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface IEntity extends Serializable,Identifiable<Long> {
    static Long getIdOrNull(final IEntity entity) {
        return entity == null ? null : entity.getId();
    }

    /**
     * @return time in {@link TimeUnit#MILLISECONDS} when the {@link IEntity}
     *         was created.
     */
    Date getCreatedAt();

    /**
     * @return user that created the {@link IEntity}.
     */
    String getCreatedBy();

    /**
     * @return time in {@link TimeUnit#MILLISECONDS} when the {@link IEntity}
     *         was last time changed.
     */
    Date getLastModifiedAt();

    /**
     * @return user that updated the {@link IEntity} last.
     */
    String getLastModifiedBy();


    int getOptLockRevision();
}
