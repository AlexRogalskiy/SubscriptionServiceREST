package com.wildbeeslabs.rest.model;

import com.wildbeeslabs.rest.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * Subscription REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Entity(name = "Subscription")
@Table(name = "subscriptions", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name", name = "name_unique_constraint")
})
@Inheritance(strategy = InheritanceType.JOINED)
public class Subscription extends BaseEntity<Long> implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id", unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "Field <name> cannot be blank")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN)
    @Column(name = "expired_at", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date expireAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    //@JsonManagedReference(value = "subscriptionToSubscriptionStatus")
    @JoinColumn(name = "subscription_status_id", referencedColumnName = "subscription_status_id")
    private SubscriptionStatusInfo statusInfo;

    @OneToMany(mappedBy = "id.subscription", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Column(name = "users", nullable = true)
    private final Set<UserSubOrder> userOrders = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
        return this.expireAt;
    }

    public void setExpireAt(final Date expireAt) {
        this.expireAt = expireAt;
    }

//    public String getExpireAt() {
//        return (Objects.nonNull(this.expireAt)) ? DateUtils.dateToStr(this.expireAt) : null;
//    }
//
//    public void setExpireAt(final String str) {
//        this.expireAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
//    }
    public SubscriptionStatusInfo getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(final SubscriptionStatusInfo statusInfo) {
        this.statusInfo = statusInfo;
    }

    public Set<UserSubOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(final Set<UserSubOrder> userOrders) {
        this.userOrders.clear();
        if (Objects.nonNull(userOrders)) {
            this.userOrders.addAll(userOrders);
        }
    }

    public void addUserOrder(final UserSubOrder userOrder) {
        if (Objects.nonNull(userOrder)) {
            this.userOrders.add(userOrder);
        }
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final Subscription other = (Subscription) obj;
        return Objects.equals(this.statusInfo, other.statusInfo)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.expireAt, other.expireAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.expireAt, this.statusInfo);
    }

    @Override
    public String toString() {
        return String.format("Subscription {id: %d, name: %s, expireAt: %s, statusInfo: %s, inherited: %s}", this.id, this.name, this.expireAt, this.statusInfo, super.toString());
    }
}
