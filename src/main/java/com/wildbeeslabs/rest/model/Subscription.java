package com.wildbeeslabs.rest.model;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
//import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
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
    @UniqueConstraint(columnNames = "name")
})
@Inheritance(strategy = InheritanceType.JOINED)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JacksonXmlRootElement(localName = "subscription")
public class Subscription extends BaseEntity implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id", unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "Field <name> cannot be blank")
    @Column(name = "name", nullable = false, unique = true)
    //@JacksonXmlProperty(localName = "name")
    private String name;

    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN)
    @Column(name = "expired_at", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    //@JacksonXmlProperty(localName = "expireAt")
    //@JsonProperty("expireAt")
    private Date expireAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    //@JacksonXmlProperty(localName = "type")
    private SubscriptionStatusType type;

    public static enum SubscriptionStatusType {
        PREMIUM, STANDARD, ADVANCED
    }

    @OneToMany(mappedBy = "pk.subscription", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Column(name = "users", nullable = true)
    //@JsonBackReference(value = "subOrderToSubscription")
    //@JsonIgnore
    //@JacksonXmlProperty(localName = "users")
    private final Set<UserSubOrder> userOrders = new HashSet<>();

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

    public String getExpireAt() {
        return (Objects.nonNull(this.expireAt)) ? DateUtils.dateToStr(this.expireAt) : null;
    }

    public void setExpireAt(final String str) {
        this.expireAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
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
        if (!super.equals(obj)) {
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
        return Objects.equals(this.expireAt, other.expireAt);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.expireAt);
        hash = 79 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("Subscription {id: %d, name: %s, expireAt: %s, type: %s, inherited: %s}", this.id, this.name, this.expireAt, this.type, super.toString());
    }
}
