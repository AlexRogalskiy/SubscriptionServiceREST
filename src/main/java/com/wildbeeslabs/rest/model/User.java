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
 * User REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Entity(name = "User")
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    @Column(name = "login", nullable = false, unique = true, updatable = false)
    private String login;

    @Column(name = "age", nullable = true)
    private Integer age;

    @Column(name = "rating", columnDefinition = "Decimal(10,2) default '1.00'", nullable = false)
    private Double rating;

    @Basic(optional = false)
    @Column(name = "registered_at", nullable = false, insertable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date registeredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatusType status;

    public static enum UserStatusType {
        ACTIVE, BLOCKED, UNVERIFIED
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<UserSubOrder> subOrders = new HashSet<>();

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
        this.subOrders = subOrders;
    }

    public void addSubscription(final UserSubOrder subOrder) {
        if (Objects.isNull(subOrders)) {
            this.subOrders = new HashSet<>();
        }
        this.subOrders.add(subOrder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
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
        if (this.status != other.status) {
            return false;
        }
        return Objects.equals(this.subOrders, other.subOrders);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.login);
        hash = 29 * hash + Objects.hashCode(this.age);
        hash = 29 * hash + Objects.hashCode(this.rating);
        hash = 29 * hash + Objects.hashCode(this.registeredAt);
        hash = 29 * hash + Objects.hashCode(this.status);
        hash = 29 * hash + Objects.hashCode(this.subOrders);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("User {id: %d, login: %s, age: %d, rating: %f, status: %s, registeredAt: %s, subscriptions: %s}", this.id, this.login, this.age, this.rating, this.status, this.registeredAt, this.subOrders);
    }
}
