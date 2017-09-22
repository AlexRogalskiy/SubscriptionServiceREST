package com.wildbeeslabs.rest.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * UserSubOrder Compound Primary key REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Embeddable
@SuppressWarnings("ValidAttributes")
public class UserSubOrderId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Subscription subscription;

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
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final UserSubOrderId other = (UserSubOrderId) obj;
        return Objects.equals(this.user, other.user)
                && Objects.equals(this.subscription, other.subscription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.user, this.subscription);
    }

    @Override
    public String toString() {
        return String.format("UserSubOrderId {userId: %d, subscriptionId: %d}", this.user.getId(), this.subscription.getId());
    }
}
