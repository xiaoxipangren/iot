package com.nationalchip.iot.data.model;

import org.eclipse.persistence.descriptors.DescriptorEvent;

import java.util.Collections;
import java.util.List;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/7/18 3:35 PM
 * @Modified:
 */
public interface IEventEntity {

    /**
     * Fired for the Entity creation.
     *
     * @param descriptorEvent
     */
    void fireCreateEvent(DescriptorEvent descriptorEvent);

    /**
     * Fired for the Entity updation.
     *
     * @param descriptorEvent
     */
    void fireUpdateEvent(DescriptorEvent descriptorEvent);

    /**
     * Fired for the Entity deletion.
     *
     * @param descriptorEvent
     */
    void fireDeleteEvent(DescriptorEvent descriptorEvent);

    /**
     * @return list of entity fields that if the only changed fields prevents
     *         {@link #fireUpdateEvent(DescriptorEvent)} call.
     */
    default List<String> getUpdateIgnoreFields() {
        return Collections.emptyList();
    }
}
