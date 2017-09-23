package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.api.rest.common.service.interfaces.IJpaBaseService;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;

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
public interface ISubscriptionService<T extends Subscription> extends IJpaBaseService<T, Long> {

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
    List<? extends T> findByPatternName(final String pattern);

    /**
     * Get list of subscription entities by type
     *
     * @param status - subscription type
     * @return list of subscription entities
     */
    List<? extends T> findByStatus(final SubscriptionStatusInfo.SubscriptionStatusType status);

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    List<? extends T> findByUserId(final Long userId);
}
