package com.nationalchip.iot.data.model.ota;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 3/8/18 10:30 AM
 * @Modified:
 */

import com.nationalchip.iot.data.annotation.Comment;
import com.nationalchip.iot.data.model.NamedEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * 更新目标类
 * 更新目标是由一系列的查询过滤器决定的
 * 由Target类中的query语句呈现
 */

@Entity
//@Table(name="target")
@MappedSuperclass
public class Target extends NamedEntity {

    public Target() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Comment("过滤查询语句")
    @Column(name = "query")
    private String query;

    @Comment("自动分配的更新内容")
    @JoinColumn(name = "auto_assigned_distribution",nullable = true,foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    @ManyToOne(optional = true,targetEntity = Distribution.class,fetch = FetchType.LAZY)
    private Distribution distribution;


    @Comment("被分配的推送集合")
    @OneToMany(targetEntity = Rollout.class,mappedBy = "target")
    private Set<Rollout> rollouts;


    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }
}
