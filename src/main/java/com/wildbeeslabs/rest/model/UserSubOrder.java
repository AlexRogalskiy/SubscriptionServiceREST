package com.wildbeeslabs.rest.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * UserSubOrder REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Entity
@Table(name = "user_sub_orders")
public class UserSubOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private Long subscriptionId;

    @Column(name = "created_at", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdAt;

    @Column(name = "expired_at", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expireAt;

    @ManyToMany(mappedBy = "users")
    private Set<Subscription> subscriptions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
