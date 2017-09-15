package com.wildbeeslabs.rest.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "subscription")
public class SubscriptionDTO extends BaseDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "prefix")
    private String prefix;

    @JacksonXmlProperty(localName = "status")
    private SubscriptionStatusInfo.SubscriptionStatusType status;

    @JacksonXmlProperty(localName = "expireAt")
    @JsonProperty("expireAt")
    private String expireAt;

//    @JacksonXmlProperty(localName = "status")
//    //@JsonManagedReference(value = "statusToSubscription")
//    private SubscriptionStatusInfo status;
    @JsonIgnore
    @JacksonXmlProperty(localName = "users")
    //@JsonBackReference(value = "subOrderToSubscription")
//    @JacksonXmlElementWrapper(localName = "users", useWrapping = false)
    private final Set<UserSubOrderDTO> userOrders = new HashSet<>();

    @Override
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(final String expireAt) {
        this.expireAt = expireAt;
    }

//    public Date getExpireAt() {
//        return (Objects.nonNull(this.expireAt)) ? DateUtils.strToDate(this.expireAt) : null;
//    }
//
//    public void setExpireAt(final Date date) {
//        this.expireAt = (Objects.nonNull(date)) ? DateUtils.dateToStr(date) : null;
//    }
    public SubscriptionStatusInfo.SubscriptionStatusType getStatus() {
        return status;
    }

    public void setStatus(final SubscriptionStatusInfo.SubscriptionStatusType status) {
        this.status = status;
    }

    public Set<UserSubOrderDTO> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(final Set<UserSubOrderDTO> userOrders) {
        this.userOrders.clear();
        if (Objects.nonNull(userOrders)) {
            this.userOrders.addAll(userOrders);
        }
    }

    public void addUserOrder(final UserSubOrderDTO userOrder) {
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
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.expireAt, other.expireAt);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.expireAt);
        hash = 79 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("SubscriptionDTO {id: %d, name: %s, expireAt: %s, status: %s, inherited: %s}", this.id, this.name, this.expireAt, this.status, super.toString());
    }
}
