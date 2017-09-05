package com.wildbeeslabs.rest.model.dto;

import com.wildbeeslabs.rest.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.wildbeeslabs.rest.model.Subscription.SubscriptionStatusType;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * SubscriptionDTO REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "userOrders"})
@JacksonXmlRootElement(localName = "subscription")
public class SubscriptionDTO extends BaseDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;
    @JacksonXmlProperty(localName = "name")
    private String name;
    @JacksonXmlProperty(localName = "expireAt")
    private String expiredDate;
    @JacksonXmlProperty(localName = "type")
    private SubscriptionStatusType type;
    //@JsonBackReference(value = "subOrderToSubscription")
    @JsonIgnore
    @JacksonXmlProperty(localName = "users")
    private final Set<UserSubOrder> userOrders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getExpiredDateConverted(final String timezone) throws ParseException {
        getDefaultDateFormat().setTimeZone(TimeZone.getTimeZone(timezone));
        return getDefaultDateFormat().parse(this.expiredDate);
    }

    public void setExpiredDate(final Date date, final String timezone) {
        getDefaultDateFormat().setTimeZone(TimeZone.getTimeZone(timezone));
        this.expiredDate = getDefaultDateFormat().format(date);
    }

    public SubscriptionStatusType getType() {
        return type;
    }

    public void setType(final SubscriptionStatusType type) {
        this.type = type;
    }

    public Set<UserSubOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(final Set<UserSubOrder> userOrders) {
        this.userOrders.clear();
        if (Objects.nonNull(userOrders)) {
            this.userOrders.addAll(userOrders);
        }
    }

    public void addUserOrder(final UserSubOrder userOrder) {
        if (Objects.nonNull(userOrder)) {
            this.userOrders.add(userOrder);
        }
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        final SubscriptionDTO other = (SubscriptionDTO) obj;
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.expiredDate, other.expiredDate);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.expiredDate);
        hash = 79 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("SubscriptionDTO {id: %d, name: %s, expireAt: %s, type: %s, inherited: %s}", this.id, this.name, this.expiredDate, this.type, super.toString());
    }
}
