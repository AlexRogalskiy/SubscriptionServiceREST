package com.wildbeeslabs.rest.model.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

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
    private String subscribedDate;
    @JacksonXmlProperty(localName = "expiredAt")
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

    public Date getSubscribedDateConverted(final String timezone) throws ParseException {
        getDefaultDateFormat().setTimeZone(TimeZone.getTimeZone(timezone));
        return getDefaultDateFormat().parse(this.subscribedDate);
    }

    public void setSubscribedDate(final Date date, final String timezone) {
        getDefaultDateFormat().setTimeZone(TimeZone.getTimeZone(timezone));
        this.subscribedDate = getDefaultDateFormat().format(date);
    }

    public Date getExpiredDateConverted(final String timezone) throws ParseException {
        getDefaultDateFormat().setTimeZone(TimeZone.getTimeZone(timezone));
        return getDefaultDateFormat().parse(this.expiredDate);
    }

    public void setExpiredDate(final Date date, final String timezone) {
        getDefaultDateFormat().setTimeZone(TimeZone.getTimeZone(timezone));
        this.expiredDate = getDefaultDateFormat().format(date);
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
