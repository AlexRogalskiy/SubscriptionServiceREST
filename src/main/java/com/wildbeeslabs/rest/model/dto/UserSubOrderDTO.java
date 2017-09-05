package com.wildbeeslabs.rest.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.wildbeeslabs.rest.utils.DateUtils;

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
    private String subscribedDate;
    @JacksonXmlProperty(localName = "expiredAt")
    @JsonProperty("expiredAt")
    private String expiredDate;

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

    public Date getSubscribedDate() {
        return (Objects.nonNull(this.subscribedDate)) ? DateUtils.strToDate(this.subscribedDate) : null;
    }

    public void setSubscribedDate(final Date date) {
        this.subscribedDate = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
    }

    public Date getExpiredDate() {
        return (Objects.nonNull(this.expiredDate)) ? DateUtils.strToDate(this.expiredDate) : null;
    }

    public void setExpiredDate(final Date date) {
        this.expiredDate = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        final UserSubOrderDTO other = (UserSubOrderDTO) obj;
        if (!Objects.equals(this.subscribedDate, other.subscribedDate)) {
            return false;
        }
        return Objects.equals(this.expiredDate, other.expiredDate);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 23 * hash + Objects.hashCode(this.subscribedDate);
        hash = 23 * hash + Objects.hashCode(this.expiredDate);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("UserSubOrderDTO {user: %s, subscription: %s, subscribedAt: %s, expiredAt: %s, inherited: %s}", this.user, this.subscription, this.subscribedDate, this.expiredDate, super.toString());
    }
}
