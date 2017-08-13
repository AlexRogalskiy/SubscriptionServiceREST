package com.wildbeeslabs.rest.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;

/**
 *
 * Base REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public abstract class BaseEntity {

    @Column(name = "created_at", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "modified_at", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modifiedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(final Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
