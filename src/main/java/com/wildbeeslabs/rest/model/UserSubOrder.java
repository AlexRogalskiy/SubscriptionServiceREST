package com.wildbeeslabs.rest.model;

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
public class UserSubOrder extends BaseEntity implements Serializable {

    @Id
    @EmbeddedId
    private UserSubOrderId pk;

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

    /*@Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    //@JsonManagedReference(value = "userOrderToUser")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id", referencedColumnName = "subscription_id", nullable = false)
    //@JsonManagedReference(value = "subOrderToSubscription")
    private Subscription subscription;
     */
    @Column(name = "subscribed_at", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date subscribedAt;

    @Column(name = "expired_at", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date expireAt;

    /*public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(final Subscription subscription) {
        this.subscription = subscription;
    }*/
    public Date getSubscribedAt() {
        return subscribedAt;
    }

    public void setSubscribedAt(final Date subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(final Date expireAt) {
        this.expireAt = expireAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final UserSubOrder other = (UserSubOrder) obj;
        if (!Objects.equals(this.pk, other.pk)) {
            return false;
        }
        if (!Objects.equals(this.subscribedAt, other.subscribedAt)) {
            return false;
        }
        return Objects.equals(this.expireAt, other.expireAt);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.pk);
        hash = 23 * hash + Objects.hashCode(this.subscribedAt);
        hash = 23 * hash + Objects.hashCode(this.expireAt);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("UserSubOrder {primary key: %s, subscribedAt: %s, expiredAt: %s}", this.pk, this.subscribedAt, this.expireAt);
    }

    /*@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final UserSubOrder other = (UserSubOrder) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.subscribedAt, other.subscribedAt)) {
            return false;
        }
        if (!Objects.equals(this.expireAt, other.expireAt)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return Objects.equals(this.subscription, other.subscription);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.user);
        hash = 23 * hash + Objects.hashCode(this.subscription);
        hash = 23 * hash + Objects.hashCode(this.subscribedAt);
        hash = 23 * hash + Objects.hashCode(this.expireAt);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("UserSubOrder {id: %d, user: %s, subscription: %s, subscribedAt: %s, expiredAt: %s}", this.id, this.user, this.subscription, this.subscribedAt, this.expireAt);
    }*/
}
