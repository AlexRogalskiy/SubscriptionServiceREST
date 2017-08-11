package com.wildbeeslabs.rest.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * User REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    @Column(name = "Login", nullable = false)
    private String login;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "status", nullable = false)
    private UserStatusType status;

    public static enum UserStatusType {
        ACTIVE, BLOCKED, UNVERIFIED
    }

    @ManyToMany(mappedBy = "users")
    private Set<Subscription> subscriptions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public UserStatusType getStatus() {
        return status;
    }

    public void setStatus(final UserStatusType status) {
        this.status = status;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(final Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void addSubscription(Subscription subscription) {
        if (null == subscriptions) {
            this.subscriptions = new HashSet<>();
        }
        this.subscriptions.add(subscription);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (Double.doubleToLongBits(this.rating) != Double.doubleToLongBits(other.rating)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return Objects.equals(this.subscriptions, other.subscriptions);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.login);
        hash = 89 * hash + this.age;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.rating) ^ (Double.doubleToLongBits(this.rating) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.status);
        hash = 89 * hash + Objects.hashCode(this.subscriptions);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("User {id: %d, login: %s, age: %d, rating: %f, status: %s, subscriptions: %s}", this.id, this.login, this.age, this.rating, this.status, this.subscriptions);
    }
}
