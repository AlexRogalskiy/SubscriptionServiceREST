/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.rest.model;

import com.wildbeeslabs.api.rest.common.model.IBaseEntity;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * SubscriptionStatus Information REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Entity(name = "SubscriptionStatusInfo")
@Table(name = "subscription_status_info", uniqueConstraints = {
    @UniqueConstraint(columnNames = "prefix", name = "prefix_unique_constraint")
    ,
    @UniqueConstraint(columnNames = "status", name = "status_unique_constraint")
})
@Inheritance(strategy = InheritanceType.JOINED)
@SuppressWarnings("ValidAttributes")
public class SubscriptionStatusInfo implements IBaseEntity<Long> {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_status_id", unique = true, nullable = false)
    private Long id;

    @Size(min = 2, max = 50, message = "Field <prefix> is only allowed in the following length range=[min={%d}, max={%d}]")
    @NotBlank(message = "Field <prefix> cannot be blank")
    @Column(name = "prefix", nullable = false, unique = true)
    private String prefix;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatusType status;

    @OneToMany(mappedBy = "statusInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Column(name = "subscriptions", nullable = true)
//    //@JsonBackReference(value = "subscriptionToSubscriptionStatus")
//    //@JsonIgnore
//    //@JacksonXmlProperty(localName = "subscriptions")
    private final Set<Subscription> subscriptions = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public static enum SubscriptionStatusType {
        PREMIUM, STANDARD, ADVANCED
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public SubscriptionStatusType getStatus() {
        return status;
    }

    public void setStatus(final SubscriptionStatusType status) {
        this.status = status;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(final Set<Subscription> subscription) {
        this.subscriptions.clear();
        if (Objects.nonNull(subscription)) {
            this.subscriptions.addAll(subscription);
        }
    }

    public void addSubscription(final Subscription subscription) {
        if (Objects.nonNull(subscription)) {
            this.subscriptions.add(subscription);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final SubscriptionStatusInfo other = (SubscriptionStatusInfo) obj;
        return Objects.equals(this.prefix, other.prefix)
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.status, other.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.prefix, this.status);
    }

    @Override
    public String toString() {
        return String.format("SubscriptionStatusInfo {id: %d, prefix: %s, status: %s}", this.id, this.prefix, this.status);
    }
}
