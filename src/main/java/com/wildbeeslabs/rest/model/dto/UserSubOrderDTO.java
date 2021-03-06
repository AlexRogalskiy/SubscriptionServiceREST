package com.wildbeeslabs.rest.model.dto;

import com.wildbeeslabs.api.rest.common.utils.DateUtils;

import com.wildbeeslabs.api.rest.common.model.dto.BaseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * UserSubOrderDTO REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Inheritance(strategy = InheritanceType.JOINED)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "userSubOrder")
public class UserSubOrderDTO extends BaseDTO {

    @JacksonXmlProperty(localName = "user")
    //@JsonManagedReference(value = "userOrderToUser")
    private UserDTO user;

    @JacksonXmlProperty(localName = "subscription")
    //@JsonManagedReference(value = "subOrderToSubscription")
    private SubscriptionDTO subscription;

    @JacksonXmlProperty(localName = "subscribedAt")
    @JsonProperty("subscribedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN_EXT, locale = DateUtils.DEFAULT_DATE_FORMAT_LOCALE)
    private Date subscribedAt;

    @JacksonXmlProperty(localName = "expiredAt")
    @JsonProperty("expiredAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN_EXT, locale = DateUtils.DEFAULT_DATE_FORMAT_LOCALE)
    private Date expiredAt;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(final UserDTO user) {
        this.user = user;
    }

    public SubscriptionDTO getSubscription() {
        return subscription;
    }

    public void setSubscription(final SubscriptionDTO subscription) {
        this.subscription = subscription;
    }

    public Date getSubscribedAt() {
        return subscribedAt;
    }

    public void setSubscribedAt(final Date subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(final Date expiredAt) {
        this.expiredAt = expiredAt;
    }

//    public Date getSubscribedDate() {
//        return (Objects.nonNull(this.subscribedDate)) ? DateUtils.strToDate(this.subscribedDate) : null;
//    }
//
//    public void setSubscribedDate(final Date date) {
//        this.subscribedDate = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
//    }
//
//    public Date getExpiredDate() {
//        return (Objects.nonNull(this.expiredDate)) ? DateUtils.strToDate(this.expiredDate) : null;
//    }
//
//    public void setExpiredDate(final Date date) {
//        this.expiredDate = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
//    }
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final UserSubOrderDTO other = (UserSubOrderDTO) obj;
        return Objects.equals(this.subscribedAt, other.subscribedAt)
                && Objects.equals(this.expiredAt, other.expiredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.subscribedAt, this.expiredAt);
    }

    @Override
    public String toString() {
        return String.format("UserSubOrderDTO {user: %s, subscription: %s, subscribedAt: %s, expiredAt: %s, inherited: %s}", this.user, this.subscription, this.subscribedAt, this.expiredAt, super.toString());
    }
}
