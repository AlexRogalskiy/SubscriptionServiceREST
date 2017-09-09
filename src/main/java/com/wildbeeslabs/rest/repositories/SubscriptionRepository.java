package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * Subscription REST Application storage repository
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@Repository
public interface SubscriptionRepository<T extends Subscription> extends BaseRepository<T> {

    /**
     * Default query to find subscriptions by user id
     */
    public final static String FIND_SUB_BY_USER_ID_QUERY = "SELECT o.pk.subscription FROM UserSubOrder o WHERE o.pk.user.id = :userId";

    /**
     * Get subscription entity by name (case insensitive)
     *
     * @param name - subscription name
     * @return subscription entity
     */
    T findByNameIgnoreCase(final String name);

    /**
     * Get list of subscription entities by name pattern
     *
     * @param name - subscription name pattern
     * @return list of subscription entities
     */
    List<T> findByNameLike(final String name);

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
     * @return list of subscription orders
     */
    @Query(FIND_SUB_BY_USER_ID_QUERY)
    List<T> findByUserId(@Param("userId") final Long userId);
}
