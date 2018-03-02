package com.nationalchip.iot.data.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Access(AccessType.FIELD)
//审计功能，注意需要在configuration中申明DateTime和AuditorAware的实现
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String createdBy;
    private String lastModifiedBy;
    private Date createdAt;
    private Date lastModifiedAt;

    @Version
    @Column(name = "optlock_revision")
    private int optLockRevision;

    @Override
    @Access(AccessType.PROPERTY)
    @Column(name = "created_at", insertable = true, updatable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    @Access(AccessType.PROPERTY)
    @Column(name = "created_by", insertable = true, updatable = false, length = 40)
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    @Access(AccessType.PROPERTY)
    @Column(name = "last_modified_at", insertable = true, updatable = true)
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    @Override
    @Access(AccessType.PROPERTY)
    @Column(name = "last_modified_by", insertable = true, updatable = true, length = 40)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @CreatedBy
    public void setCreatedBy(final String createdBy) {

        this.createdBy = createdBy;
    }

    @CreatedDate
    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    @LastModifiedDate
    public void setLastModifiedAt(final Date lastModifiedAt) {

        this.lastModifiedAt = lastModifiedAt;
    }

    @LastModifiedBy
    public void setLastModifiedBy(final String lastModifiedBy) {

        this.lastModifiedBy = lastModifiedBy;
    }


    @Override
    public int getOptLockRevision() {
        return optLockRevision;
    }

    public void setOptLockRevision(final int optLockRevision) {
        this.optLockRevision = optLockRevision;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id=" + id + "]";
    }

    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Defined equals/hashcode strategy for the data in general is that an
     * entity is equal if it has the same {@link #getId()} and
     * {@link #getOptLockRevision()} and class.
     *
     * @see Object#hashCode()
     */
    @Override
    // Exception squid:S864 - generated code
    @SuppressWarnings({ "squid:S864" })
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + optLockRevision;
        result = prime * result + this.getClass().getName().hashCode();
        return result;
    }

    /**
     * Defined equals/hashcode strategy for the data in general is that an
     * entity is equal if it has the same {@link #getId()} and
     * {@link #getOptLockRevision()} and class.
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(this.getClass().isInstance(obj))) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return optLockRevision == other.optLockRevision;
    }
}


