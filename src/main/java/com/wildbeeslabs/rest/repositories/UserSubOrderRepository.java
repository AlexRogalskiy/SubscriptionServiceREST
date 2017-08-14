package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import java.util.Date;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 *
 * UserSubOrder REST Application storage repository
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@Repository
public interface UserSubOrderRepository<T extends UserSubOrder> extends BaseRepository<T> {

    /**
     * Get list of subscription orders by user
     *
     * @param user - user entity
     * @return list of subscription orders
     */
    List<T> findByUser(final User user);

    /**
     * Get list of subscription orders by subscription
     *
     * @param subscription - subscription entity
     * @return list of subscription orders
     */
    List<T> findBySubscription(final Subscription subscription);

    /**
     * Get list of subscription orders by date range (from / to)
     *
     * @param dateFrom - start date of range
     * @param dateTo - end date of range
     * @return list of subscription ordersF
     */
    List<T> findBySubscribedAtBetween(final Date dateFrom, final Date dateTo);

    /**
     * Get list of subscription orders before (including) particular date
     *
     * @param date - request date
     * @return list of subscription orders
     */
    List<T> findBySubscribedAtLessThanEqual(final Date date);

    /**
     * Get list of subscription orders after (excluding) particular date
     *
     * @param date - request date
     * @return list of subscription orders
     */
    List<T> findBySubscribedAtGreaterThan(final Date date);
}
