package com.wildbeeslabs.rest.model;

import com.wildbeeslabs.api.rest.common.model.BaseEntity;
import com.wildbeeslabs.api.rest.common.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * UserSubOrder REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Entity(name = "UserSubOrder")
@Table(name = "user_sub_orders")
@AssociationOverrides({
    @AssociationOverride(name = "id.user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false))
    ,
		@AssociationOverride(name = "id.subscription",
            joinColumns = @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id", nullable = false))})
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings({"ConsistentAccessType", "IdDefinedInHierarchy"})
public class UserSubOrder extends BaseEntity<UserSubOrderId> implements Serializable {

    @Id
    @EmbeddedId
//    @JsonIgnore
    private UserSubOrderId id = new UserSubOrderId();

    @Override
    public UserSubOrderId getId() {
        return id;
    }

    @Override
    public void setId(final UserSubOrderId id) {
        this.id = id;
    }

    @Transient
    public User getUser() {
        return getId().getUser();
    }

    public void setUser(final User user) {
        getId().setUser(user);
    }

    @Transient
    public Subscription getSubscription() {
        return getId().getSubscription();
    }

    public void setSubscription(final Subscription subscription) {
        getId().setSubscription(subscription);
    }

    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN)
    @Column(name = "subscribed_at", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date subscribedAt;

    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN)
    @Column(name = "expired_at", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date expiredAt;

    public Date getSubscribedAt() {
        return this.subscribedAt;
    }

    public void setSubscribedAt(final Date subscribedAt) {
        this.subscribedAt = subscribedAt;
    }
//    public String getSubscribedAt() {
//        return (Objects.nonNull(this.subscribedAt)) ? DateUtils.dateToStr(this.subscribedAt) : null;
//    }
//
//    public void setSubscribedAt(final String str) {
//        this.subscribedAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
//    }

    public Date getExpiredAt() {
        return this.expiredAt;
    }

    public void setExpiredAt(final Date expiredAt) {
        this.expiredAt = expiredAt;
    }

//    public String getExpiredAt() {
//        return (Objects.nonNull(this.expiredAt)) ? DateUtils.dateToStr(this.expiredAt) : null;
//    }
//
//    public void setExpiredAt(final String str) {
//        this.expiredAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
//    }
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final UserSubOrder other = (UserSubOrder) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.subscribedAt, other.subscribedAt)
                && Objects.equals(this.expiredAt, other.expiredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.subscribedAt, this.expiredAt);
    }

    @Override
    public String toString() {
        return String.format("UserSubOrder {primary key: %s, subscribedAt: %s, expiredAt: %s, inherited: %s}", this.id, this.subscribedAt, this.expiredAt, super.toString());
    }
}
