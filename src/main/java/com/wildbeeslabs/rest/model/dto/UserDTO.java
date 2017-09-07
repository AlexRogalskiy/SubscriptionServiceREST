package com.wildbeeslabs.rest.model.dto;

import com.wildbeeslabs.rest.model.User.UserStatusType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.wildbeeslabs.rest.model.User.UserGenderType;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "subOrders"})
@JacksonXmlRootElement(localName = "user")
public class UserDTO extends BaseDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;
    @JacksonXmlProperty(localName = "login")
    private String login;
    @JacksonXmlProperty(localName = "name")
    private String name;
    @JacksonXmlProperty(localName = "age")
    private Integer age;
    @JacksonXmlProperty(localName = "phone")
    private String phone;
    @JacksonXmlProperty(localName = "rating")
    private Double rating;
    @JacksonXmlProperty(localName = "gender")
    private UserGenderType gender;
    @JacksonXmlProperty(localName = "registeredAt")
    @JsonProperty("registeredAt")
    private String registeredAt;
    @JacksonXmlProperty(localName = "status")
    private UserStatusType status;
    //@JsonBackReference(value = "userOrderToUser")
    @JsonIgnore
    @JacksonXmlProperty(localName = "subscriptions")
    private final Set<UserSubOrderDTO> subOrders = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    public UserGenderType getGender() {
        return gender;
    }

    public void setGender(final UserGenderType gender) {
        this.gender = gender;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(final String registeredAt) {
        this.registeredAt = registeredAt;
    }

//    public Date getRegisteredDate() {
//        return (Objects.nonNull(this.registeredDate)) ? DateUtils.strToDate(this.registeredDate) : null;
//    }
//
//    public void setRegisteredDate(final Date date) {
//        this.registeredDate = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
//    }
    public UserStatusType getStatus() {
        return status;
    }

    public void setStatus(final UserStatusType status) {
        this.status = status;
    }

    public Set<UserSubOrderDTO> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(final Set<UserSubOrderDTO> subOrders) {
        this.subOrders.clear();
        if (Objects.nonNull(subOrders)) {
            this.subOrders.addAll(subOrders);
        }
    }

    public void addSubscription(final UserSubOrderDTO subOrder) {
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
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.rating, other.rating)) {
            return false;
        }
        if (this.gender != other.gender) {
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
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.age);
        hash = 29 * hash + Objects.hashCode(this.phone);
        hash = 29 * hash + Objects.hashCode(this.rating);
        hash = 29 * hash + Objects.hashCode(this.gender);
        hash = 29 * hash + Objects.hashCode(this.registeredAt);
        hash = 29 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("UserDTO {id: %d, login: %s, name: %s, age: %d, phone: %s, rating: %f, gender: %s, status: %s, registeredAt: %s, inherited: %s}", this.id, this.login, this.name, this.age, this.phone, this.rating, this.gender, this.status, this.registeredAt, super.toString());
    }
}
