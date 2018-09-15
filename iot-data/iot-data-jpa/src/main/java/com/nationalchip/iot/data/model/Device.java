package com.nationalchip.iot.data.model;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.auth.Consumer;
import com.nationalchip.iot.data.model.auth.Developer;
import com.nationalchip.iot.data.model.ota.Distribution;
import com.nationalchip.iot.data.model.ota.Status;
import org.eclipse.persistence.descriptors.DescriptorEvent;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/7/18 3:37 PM
 * @Modified:
 */


/**
 * 终端设备类
 */

@Entity
@Table(name="device")
@MappedSuperclass
public class Device extends NamedEntity implements IEventEntity {

    public Device(){

    }

    @Comment("全局序列号")
    @Column(name="uuid")
    private String uid;

    @Comment("设备芯片编号")
    @Column(name="chip_id")
    private String cid;

    @Comment("设备激活时的ip")
    @Column(name = "ip")
    private String ip;

    @Comment("设备的通信地址")
    @Column(name="address")
    private String address;

    @Comment("设备mac地址")
    @Column(name="mac")
    private String mac;

    @Comment("设备是否已激活")
    @Column(name="actived")
    private boolean actived;

    @Comment("被分配的更新内容")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="assigned_distri",foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Distribution assignedDistribution;

    @Comment("已安装的更新内容")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="installed_distri",foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Distribution installedDistribution;

    @Comment("绑定的用户")
    @ManyToOne(optional = true,fetch = FetchType.LAZY,targetEntity = Consumer.class)
    @JoinColumn(name="bound_consumer",nullable = true,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Consumer consumer;

    @Comment("所属的产品类别")
    @ManyToOne(optional = true,targetEntity = Product.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_product",nullable = true,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Product product;

    @Comment("设备制造商")
    @ManyToOne(optional = true,targetEntity = Developer.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "produced_by",nullable = true,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Developer developer;

    @Column(name = "update_status")
    @Comment("设备更新状态")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Comment("激活日期")
    @Column(name="actived_date")
    private Date activedDate;

    @Comment("安全令牌")
    @Column(name="security_token")
    private String token;



    @Override
    public void fireCreateEvent(DescriptorEvent descriptorEvent) {

    }

    @Override
    public void fireUpdateEvent(DescriptorEvent descriptorEvent) {

    }

    @Override
    public void fireDeleteEvent(DescriptorEvent descriptorEvent) {

    }
}
