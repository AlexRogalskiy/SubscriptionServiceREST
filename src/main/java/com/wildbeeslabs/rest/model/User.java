package com.wildbeeslabs.rest.model;

import com.wildbeeslabs.api.rest.common.model.BaseEntity;
import com.wildbeeslabs.api.rest.common.utils.DateUtils;
import com.wildbeeslabs.api.rest.common.model.validation.Phone;
import com.wildbeeslabs.api.rest.common.model.validation.BigDecimalRange;
import com.wildbeeslabs.api.rest.common.model.validation.UID;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

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
    @UniqueConstraint(columnNames = "login", name = "login_unique_constraint")
})
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity<Long> implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Email(message = "Field <login> is only allowed in the following format=user@domain.com")
    @NotBlank(message = "Field <login> cannot be blank")
    @Column(name = "login", nullable = false, unique = true, updatable = false)
    private String login;

    @Size(min = 2, max = 50, message = "Field <name> is only allowed in the following length range=[min={%d}, max={%d}]")
    @NotBlank(message = "Field <name> cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @Range(min = 1, max = 150, message = "Field <age> is only allowed in the following range=[min={%d}, max={%d}]")
    @Column(name = "age", nullable = true)
    private Integer age;

    @Size(min = 5, max = 25, message = "Field <phone> is only allowed in the following length range=[min={%d}, max={%d}]")
    @Phone(message = "Field <phone> is only allowed in the following format: +[0-9]")
    @Column(name = "phone", nullable = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Field <gender> cannot be null")
    @Column(name = "gender", nullable = false)
    private UserGenderType gender;

    @UID
    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    //@GeneratedValue(generator = "UUID")
    //@GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    //@GenericGenerator(name = "uuid", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID uuId;

    public static enum UserGenderType {
        MALE, FEMALE
    }

    @BigDecimalRange(minPrecision = 1, maxPrecision = 10, scale = 2, message = "Field <rating> is only allowed in the following precision range=[min={%d}, max={%d}], scale max bound={%d}")
    //@Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/)
    //@Range(min = 1, max = 100, message = "Field <rating> is only allowed within the following range=[min={%d}, max={%d}]")
    @Column(name = "rating", precision = 10, scale = 2, nullable = false)
    private BigDecimal rating;

    @Past
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN)
    @Column(name = "birthday_at", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date birthdayAt;

    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN)
    @Column(name = "registered_at", nullable = true, insertable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date registeredAt;

    @Column(name = "isEnabledSubscription", nullable = true)
    private Boolean isEnabledSubscription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatusType status;

    public static enum UserStatusType {
        ACTIVE, BLOCKED, UNVERIFIED
    }

    @OneToMany(mappedBy = "id.user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Column(name = "subscriptions", nullable = true)
    private final Set<UserSubOrder> subOrders = new HashSet<>();

    @PrePersist
    public void initializeUUID() {
        if (Objects.isNull(this.uuId)) {
            this.uuId = UUID.randomUUID();
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public UserGenderType getGender() {
        return gender;
    }

    public void setGender(final UserGenderType gender) {
        this.gender = gender;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(final BigDecimal rating) {
        this.rating = rating;
    }

//    public String getBirthdayAt() {
//        return (Objects.nonNull(this.birthdayAt)) ? DateUtils.dateToStr(this.birthdayAt) : null;
//    }
//
//    public void setBirthdayAt(final String str) {
//        this.birthdayAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
//    }
    public Date getBirthdayAt() {
        return this.birthdayAt;
    }

    public void setBirthdayAt(final Date birthdayAt) {
        this.birthdayAt = birthdayAt;
    }

//    public String getRegisteredAt() {
//        return (Objects.nonNull(this.registeredAt)) ? DateUtils.dateToStr(this.registeredAt) : null;
//    }
//
//    public void setRegisteredAt(final String str) {
//        this.registeredAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
//    }
    public Date getRegisteredAt() {
        return this.registeredAt;
    }

    public void setRegisteredAt(final Date registeredAt) {
        this.registeredAt = registeredAt;
    }

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

    public UUID getUuId() {
        return uuId;
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
        final User other = (User) obj;
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
        return String.format("User {id: %d, uuid: %s, login: %s, name: %s, age: %d, phone: %s, gender: %s, rating: %f, birthdayAt: %s, isEnabledSubscription: %s, status: %s, registeredAt: %s, inherited: %s}", this.id, this.uuId, this.login, this.name, this.age, this.phone, this.gender, this.rating, this.birthdayAt, this.isEnabledSubscription, this.status, this.registeredAt, super.toString());
    }
}
