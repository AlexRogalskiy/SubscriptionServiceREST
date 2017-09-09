package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;

import java.util.Date;
import java.util.List;

/**
 *
 * User REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface UserService<T extends User> extends BaseService<T> {

    /**
     * Get user entity by login
     *
     * @param login - user login
     * @return user entity
     */
    T findByLogin(final String login);

    /**
     * Get list of user entities by subscription type and subscription date
     * (after - excluding / before - including)
     *
     * @param subDate - subscription date
     * @param subType - subscription type
     * @param dateTypeOrder - date type order (before / after)
     * @return list of user entities
     */
    List<T> findAllBySubscriptionTypeAndDate(final Date subDate, final Subscription.SubscriptionStatusType subType, final DateTypeOrder dateTypeOrder);

    /**
     * Get list of user entities by subscription date (after - excluding /
     * before - including)
     *
     * @param subDate - subscription date
     * @param dateTypeOrder - date type order (before / after)
     * @return - list of user entities
     */
    List<T> findAllBySubscriptionDate(final Date subDate, final DateTypeOrder dateTypeOrder);

    /**
     * Get list of user entities by subscription type
     *
     * @param subType - subscription type
     * @return list of user entities
     */
    List<T> findAllBySubscriptionType(final Subscription.SubscriptionStatusType subType);

    /**
     * Get list of user entities by subscription ID
     *
     * @param subscriptionId - subscription identifier
     * @return list of user entities
     */
    List<T> findBySubscriptionId(final Long subscriptionId);
}
