package com.nationalchip.iot.data.model.ota;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.Device;
import com.nationalchip.iot.data.model.VersionedEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 10:27 AM
 * @Modified:
 */
@MappedSuperclass
@Entity
//@Table(name = "distribution")
public class Distribution extends VersionedEntity {

//    @Comment("更新包含的子模块")
//    @ManyToMany
//    @JoinTable(name="distri_module",
//            joinColumns = {@JoinColumn(name="distri_id",nullable = false,updatable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))},
//            inverseJoinColumns = {@JoinColumn(name="module_id",nullable = false,updatable = false,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))})
//    private Set<Module> modules;


    @Comment("更新被分配至的设备集合")
    @OneToMany(mappedBy = "assignedDistribution",targetEntity = Device.class)
    private Set<Device> assignedToDevices;

    @Comment("更新内容已被安装至的设备集合")
    @OneToMany(mappedBy = "installedDistribution",targetEntity = Device.class)
    private Set<Device> installedAtDevices;

    @Comment("被分配至的推送")
    @OneToMany(mappedBy = "distribution",targetEntity = Rollout.class)
    private Set<Rollout> rollouts;


    @Comment("是否完成")
    private boolean complete;





}
