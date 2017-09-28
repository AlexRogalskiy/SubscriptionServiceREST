package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.api.rest.common.service.interfaces.IBaseService;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.model.UserSubOrderId;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * UserSubOrder REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface IUserSubOrderService<T extends UserSubOrder> extends IBaseService<T, UserSubOrderId> {

    /**
     * Get list of subscription orders by user entity
     *
     * @param user - user entity
     * @return list of subscription orders
     */
    List<? extends T> findByUser(final User user);

    /**
     * Get list of subscription orders by subscription entity
     *
     * @param subscription - subscription entity
     * @return list of subscription orders
     */
    List<? extends T> findBySubscription(final Subscription subscription);

    /**
     * Get list of subscription orders by user and subscription entity
     *
     * @param user - user entity
     * @param subscription - subscription entity
     * @return subscription order
     */
    Optional<T> findByUserAndSubscription(final User user, final Subscription subscription);

    /**
     * Get list of subscription orders by date range (start date / end date)
     *
     * @param dateFrom - start date of range
     * @param dateTo - end date of range
     * @return list of subscription orders
     */
    List<? extends T> findBySubscribedAtBetween(final Date dateFrom, final Date dateTo);
}
