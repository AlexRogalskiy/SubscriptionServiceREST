package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.rest.model.Subscription;

import java.util.List;

/**
 *
 * Subscription REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface SubscriptionService<T extends Subscription> extends BaseService<T> {

    /**
     * Get subscription entity by name
     *
     * @param name - subscription name pattern
     * @return list of subscription entities
     */
    T findByName(final String name);

    /**
     * Get list of subscription entities by name pattern
     *
     * @param pattern - subscription name pattern
     * @return list of subscription entities
     */
    List<T> findByPatternName(final String pattern);

    /**
     * Get list of subscription entities by type
     *
     * @param type - subscription type
     * @return list of subscription entities
     */
    List<T> findByType(final Subscription.SubscriptionStatusType type);

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    List<T> findByUserId(final Long userId);
}
