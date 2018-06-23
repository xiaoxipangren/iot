package com.nationalchip.iot.data.model.ota;

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.ArchivedEntity;

import javax.persistence.*;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 10:28 AM
 * @Modified:
 */

/**
 * 更新类
 * 更新由更新内容Distribution和更新对象Target组成
 */
@Entity
@Table(name = "rollout")
@MappedSuperclass
public class Rollout extends ArchivedEntity {
    @Comment("更新对象")
    @ManyToOne(targetEntity = Target.class,fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "target_id",nullable = false,foreignKey = @ForeignKey())
    private Target target;

    @Comment("更新内容")
    @ManyToOne(optional =true,targetEntity = Distribution.class,fetch = FetchType.LAZY)
    private Distribution distribution;


    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }
}
