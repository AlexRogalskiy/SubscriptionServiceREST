package com.wildbeeslabs.rest.model;

import com.wildbeeslabs.rest.utils.DateUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * Base REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", nullable = true, insertable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @NotBlank(message = "Field <createdBy> cannot be blank")
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "modified_by", nullable = true, insertable = false)
    private String modifiedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = new Date();
    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(final Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getModifiedAt() {
//        return modifiedAt;
//    }
//
//    public void setModifiedAt(final Date modifiedAt) {
//        this.modifiedAt = modifiedAt;
//    }
    public String getCreatedAt() {
        return (Objects.nonNull(this.createdAt)) ? DateUtils.dateToStr(this.createdAt) : null;
    }

    public void setCreatedAt(final String str) {
        this.createdAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
    }

    public String getModifiedAt() {
        return (Objects.nonNull(this.modifiedAt)) ? DateUtils.dateToStr(this.modifiedAt) : null;
    }

    public void setModifiedAt(final String str) {
        this.modifiedAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        if (!Objects.equals(this.createdBy, other.createdBy)) {
            return false;
        }
        if (!Objects.equals(this.modifiedBy, other.modifiedBy)) {
            return false;
        }
        if (!Objects.equals(this.createdAt, other.createdAt)) {
            return false;
        }
        return Objects.equals(this.modifiedAt, other.modifiedAt);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.createdAt);
        hash = 53 * hash + Objects.hashCode(this.modifiedAt);
        hash = 53 * hash + Objects.hashCode(this.createdBy);
        hash = 53 * hash + Objects.hashCode(this.modifiedBy);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("BaseEntity {createdAt: %s, createdAt: %s, createdBy: %s, modifiedBy: %s}", this.createdAt, this.modifiedAt, this.createdBy, this.modifiedBy);
    }
}
