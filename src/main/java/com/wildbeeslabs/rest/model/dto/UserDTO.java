package com.wildbeeslabs.rest.model.dto;

import com.wildbeeslabs.api.rest.common.utils.DateUtils;
import com.wildbeeslabs.api.rest.common.model.dto.BaseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildbeeslabs.rest.model.User.UserStatusType;
import com.wildbeeslabs.rest.model.User.UserGenderType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "user")
public class UserDTO extends BaseDTO<Long> {

    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "uuid")
    //@JacksonInject
    private UUID uuId;

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

    @JacksonXmlProperty(localName = "birthdayAt")
    @JsonProperty("birthdayAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN, locale = DateUtils.DEFAULT_DATE_FORMAT_LOCALE)
    private Date birthdayAt;

    @JacksonXmlProperty(localName = "registeredAt")
    @JsonProperty("registeredAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN_EXT, locale = DateUtils.DEFAULT_DATE_FORMAT_LOCALE)
    private Date registeredAt;

    @JacksonXmlProperty(localName = "enableSubscription")
    private Boolean isEnabledSubscription;

    @JacksonXmlProperty(localName = "status")
    private UserStatusType status;

    //@JsonBackReference(value = "userOrderToUser")
    @JsonIgnore
    @JacksonXmlProperty(localName = "subscriptions")
    //@JacksonXmlElementWrapper(localName = "subscriptions", useWrapping = false)
    private final Set<UserSubOrderDTO> subOrders = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public UUID getUuId() {
        return uuId;
    }

    public void setUuId(final UUID uuId) {
        this.uuId = uuId;
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

    public Date getBirthdayAt() {
        return birthdayAt;
    }

    public void setBirthdayAt(final Date birthdayAt) {
        this.birthdayAt = birthdayAt;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(final Date registeredAt) {
        this.registeredAt = registeredAt;
    }

//    public Date getRegisteredDate() {
//        return (Objects.nonNull(this.registeredDate)) ? DateUtils.strToDate(this.registeredDate) : null;
//    }
//
//    public void setRegisteredDate(final Date date) {
//        this.registeredDate = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
//    }
    public Boolean getIsEnabledSubscription() {
        return isEnabledSubscription;
    }

    public void setIsEnabledSubscription(final Boolean isEnabledSubscription) {
        this.isEnabledSubscription = isEnabledSubscription;
    }

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
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final UserDTO other = (UserDTO) obj;
        return Objects.equals(this.login, other.login)
                && Objects.equals(this.uuId, other.uuId)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.age, other.age)
                && Objects.equals(this.phone, other.phone)
                && Objects.equals(this.gender, other.gender)
                && Objects.equals(this.rating, other.rating)
                && Objects.equals(this.birthdayAt, other.birthdayAt)
                && Objects.equals(this.registeredAt, other.registeredAt)
                && Objects.equals(this.isEnabledSubscription, other.isEnabledSubscription)
                && Objects.equals(this.status, other.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.uuId, this.login, this.name, this.age, this.phone, this.gender, this.rating, this.birthdayAt, this.registeredAt, this.isEnabledSubscription, this.status);
    }

    @Override
    public String toString() {
        return String.format("UserDTO {id: %d, login: %s, name: %s, age: %d, phone: %s, rating: %f, gender: %s, birthdayAt: %s, isEnabledSubscription: %s, status: %s, registeredAt: %s, inherited: %s}", this.id, this.login, this.name, this.age, this.phone, this.rating, this.gender, this.birthdayAt, this.isEnabledSubscription, this.status, this.registeredAt, super.toString());
    }
}
