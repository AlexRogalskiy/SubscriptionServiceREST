package com.wildbeeslabs.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
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
    @AssociationOverride(name = "pk.user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false))
    ,
		@AssociationOverride(name = "pk.subscription",
            joinColumns = @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id", nullable = false))})
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings({"ConsistentAccessType", "IdDefinedInHierarchy"})
@JacksonXmlRootElement(localName = "userSubOrder")
public class UserSubOrder extends BaseEntity implements Serializable {

    @Id
    @EmbeddedId
    @JsonIgnore
    private UserSubOrderId pk = new UserSubOrderId();

    public UserSubOrderId getPk() {
        return pk;
    }

    public void setPk(final UserSubOrderId pk) {
        this.pk = pk;
    }

    @Transient
    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(final User user) {
        getPk().setUser(user);
    }

    @Transient
    public Subscription getSubscription() {
        return getPk().getSubscription();
    }

    public void setSubscription(final Subscription subscription) {
        getPk().setSubscription(subscription);
    }

    @Column(name = "subscribed_at", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date subscribedAt;

    @Column(name = "expired_at", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date expiredAt;

    public Date getSubscribedAt() {
        return subscribedAt;
    }

    public void setSubscribedAt(final Date subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(final Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        final UserSubOrder other = (UserSubOrder) obj;
        if (!Objects.equals(this.pk, other.pk)) {
            return false;
        }
        if (!Objects.equals(this.subscribedAt, other.subscribedAt)) {
            return false;
        }
        return Objects.equals(this.expiredAt, other.expiredAt);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 23 * hash + Objects.hashCode(this.pk);
        hash = 23 * hash + Objects.hashCode(this.subscribedAt);
        hash = 23 * hash + Objects.hashCode(this.expiredAt);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("UserSubOrder {primary key: %s, subscribedAt: %s, expiredAt: %s, inherited: %s}", this.pk, this.subscribedAt, this.expiredAt, super.toString());
    }
}
