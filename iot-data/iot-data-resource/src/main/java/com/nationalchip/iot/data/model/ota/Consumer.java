package com.nationalchip.iot.data.model.ota;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.auth.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * 消费者
 */

@Entity
@Table(name = "consumer")
class Consumer extends User {

    @Column(name="location")
    @Comment("注册时的地点")
    private String location;


    @Comment("关联的设备")
    @OneToMany(mappedBy = "consumer",targetEntity = Device.class)
    private Set<Device> devices;


}
