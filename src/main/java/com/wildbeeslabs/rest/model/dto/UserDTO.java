package com.wildbeeslabs.rest.model.dto;

import com.wildbeeslabs.rest.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.wildbeeslabs.rest.model.User.UserStatusType;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * UserDTO REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JacksonXmlRootElement(localName = "user")
public class UserDTO extends BaseDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;
    @JacksonXmlProperty(localName = "login")
    private String login;
    @JacksonXmlProperty(localName = "age")
    private Integer age;
    @JacksonXmlProperty(localName = "rating")
    private Double rating;
    @JacksonXmlProperty(localName = "registeredAt")
    private Date registeredAt;
    @JacksonXmlProperty(localName = "status")
    private UserStatusType status;
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
        final UserDTO other = (UserDTO) obj;
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
        return String.format("UserDTO {id: %d, login: %s, age: %d, rating: %f, status: %s, registeredAt: %s, inherited: %s}", this.id, this.login, this.age, this.rating, this.status, this.registeredAt, super.toString());
    }
}
