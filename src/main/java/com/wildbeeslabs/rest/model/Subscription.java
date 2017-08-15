package com.wildbeeslabs.rest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * Subscription REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Entity(name = "Subscription")
@Table(name = "subscriptions")
@Inheritance(strategy = InheritanceType.JOINED)
public class Subscription extends BaseEntity implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "expired_at", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date expireAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatusType type;

    public static enum SubscriptionStatusType {
        PREMIUM, STANDARD, ADVANCED
    }

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<UserSubOrder> userOrders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(final Date expireAt) {
        this.expireAt = expireAt;
    }

    public SubscriptionStatusType getType() {
        return type;
    }

    public void setType(final SubscriptionStatusType type) {
        this.type = type;
    }

    public Set<UserSubOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(final Set<UserSubOrder> userOrders) {
        this.userOrders = userOrders;
    }

    public void addUserOrder(final UserSubOrder userOrder) {
        if (Objects.isNull(userOrders)) {
            this.userOrders = new HashSet<>();
        }
        this.userOrders.add(userOrder);
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
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.expireAt, other.expireAt)) {
            return false;
        }
        return Objects.equals(this.userOrders, other.userOrders);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.expireAt);
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.userOrders);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("Subscription {id: %d, name: %s, expireAt: %s, type: %s, users: %s}", this.id, this.name, this.expireAt, this.type.name(), this.userOrders);
    }
}
