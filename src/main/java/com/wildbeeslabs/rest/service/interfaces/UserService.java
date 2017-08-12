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
     * Get list of users entities by subscription type after (excluding)
     * particular date
     *
     * @param subDate - request date
     * @param subType - subscription type
     * @return list of user entities
     */
    List<T> findBySubscriptionTypeAndDateBefore(final Date subDate, final Subscription.SubscriptionType subType);

    /**
     * Get list of users entities by subscription type before (including)
     * particular date
     *
     * @param subDate - request date
     * @param subType - subscription type
     * @return list of user entities
     */
    List<T> findBySubscriptionTypeAndDateAfter(final Date subDate, final Subscription.SubscriptionType subType);

    /**
     * Get list of users entities after (excluding) particular date
     *
     * @param subDate - request date
     * @return list of user entities
     */
    List<T> findByDateBefore(final Date subDate);

    /**
     * Get list of users entities before (including) particular date
     *
     * @param subDate - request date
     * @return list of user entities
     */
    List<T> findByDateAfter(final Date subDate);
}
