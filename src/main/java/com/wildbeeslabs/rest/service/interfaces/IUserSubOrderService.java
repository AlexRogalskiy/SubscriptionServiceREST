package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.model.UserSubOrderId;

import java.util.Date;
import java.util.List;

/**
 *
 * UserSubOrder REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface IUserSubOrderService<T extends UserSubOrder> extends IBaseService<T> {

    /**
     * Get subscription order by id (compound primary key)
     *
     * @param id - subscription order identifier
     * @return subscription order
     */
    T findById(final UserSubOrderId id);

    /**
     * Delete subscription order by id (compound primary key)
     *
     * @param id - subscription order identifier
     */
    //void deleteById(final UserSubOrderId id);
    /**
     * Get list of subscription orders by user entity
     *
     * @param user - user entity
     * @return list of subscription orders
     */
    List<T> findByUser(final User user);

    /**
     * Get list of subscription orders by subscription entity
     *
     * @param subscription - subscription entity
     * @return list of subscription orders
     */
    List<T> findBySubscription(final Subscription subscription);

    /**
     * Get list of subscription orders by user and subscription entity
     *
     * @param user - user entity
     * @param subscription - subscription entity
     * @return subscription order
     */
    T findByUserAndSubscription(final User user, final Subscription subscription);

    /**
     * Get list of subscription orders by date range (start date / end date)
     *
     * @param dateFrom - start date of range
     * @param dateTo - end date of range
     * @return list of subscription orders
     */
    List<T> findBySubscribedAtBetween(final Date dateFrom, final Date dateTo);
}
