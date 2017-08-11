package com.wildbeeslabs.rest.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * Subscription REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class Subscription {

    private long id;

    private String name;

    private Date expireAt;

    private SubscriptionType type;

    public static enum SubscriptionType {
        PREMIUM, STANDARD, ADVANCED
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final Subscription other = (Subscription) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.expireAt, other.expireAt)) {
            return false;
        }
        return this.type == other.type;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.expireAt);
        hash = 79 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("Subscription {id: %d, name: %s, expireAt: %s, type: %s}", this.id, this.name, this.expireAt, this.type);
    }
}
