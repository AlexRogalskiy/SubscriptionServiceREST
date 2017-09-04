package com.wildbeeslabs.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

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
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 *
 * User REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Entity(name = "User")
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "login")
})
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JacksonXmlRootElement(localName = "user")
public class User extends BaseEntity implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "login", nullable = false, unique = true, updatable = false)
    @JacksonXmlProperty(localName = "login")
    private String login;

    @Range(min = 0, max = 150)
    @Column(name = "age", nullable = true)
    @JacksonXmlProperty(localName = "age")
    private Integer age;

    @Min(1)
    @Column(name = "rating", precision = 10, scale = 2, nullable = false)
    @JacksonXmlProperty(localName = "rating")
    private Double rating;

    @Column(name = "registered_at", nullable = true, insertable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @JacksonXmlProperty(localName = "registeredAt")
    private Date registeredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @JacksonXmlProperty(localName = "status")
    private UserStatusType status;

    public static enum UserStatusType {
        ACTIVE, BLOCKED, UNVERIFIED
    }

    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Column(name = "subscriptions", nullable = true)
    //@JsonBackReference(value = "userOrderToUser")
    @JsonIgnore
    @JacksonXmlProperty(localName = "subscriptions")
    private final Set<UserSubOrder> subOrders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(final Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public UserStatusType getStatus() {
        return status;
    }

    public void setStatus(final UserStatusType status) {
        this.status = status;
    }

    public Set<UserSubOrder> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(final Set<UserSubOrder> subOrders) {
        this.subOrders.clear();
        if (Objects.nonNull(subOrders)) {
            this.subOrders.addAll(subOrders);
        }
    }

    public void addSubscription(final UserSubOrder subOrder) {
        if (Objects.nonNull(subOrder)) {
            this.subOrders.add(subOrder);
        }
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        if (!Objects.equals(this.rating, other.rating)) {
            return false;
        }
        if (!Objects.equals(this.registeredAt, other.registeredAt)) {
            return false;
        }
        return this.status == other.status;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.login);
        hash = 29 * hash + Objects.hashCode(this.age);
        hash = 29 * hash + Objects.hashCode(this.rating);
        hash = 29 * hash + Objects.hashCode(this.registeredAt);
        hash = 29 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("User {id: %d, login: %s, age: %d, rating: %f, status: %s, registeredAt: %s, inherited: %s}", this.id, this.login, this.age, this.rating, this.status, this.registeredAt, super.toString());
    }
}
