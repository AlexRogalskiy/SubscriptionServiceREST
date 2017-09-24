package com.wildbeeslabs.rest.repository;

import com.wildbeeslabs.api.rest.common.repository.BaseRepository;
import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;

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
public interface SubscriptionRepository<T extends Subscription> extends JpaBaseRepository<T, Long>, BaseRepository<T> {

    /**
     * Default query to find subscriptions by user id
     */
    public final static String FIND_SUB_BY_USER_ID_QUERY = "SELECT o.id.subscription FROM UserSubOrder o WHERE o.id.user.id = :userId";

    /**
     * Default query to find subscriptions by status
     */
    public final static String FIND_SUB_BY_STATUS_QUERY = "SELECT s FROM Subscription s WHERE s.statusInfo.status = :status";

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
    List<? extends T> findByNameLike(final String name);

    /**
     * Get list of subscription entities by type
     *
     * @param status - subscription status
     * @return list of subscription entities
     */
    @Query(FIND_SUB_BY_STATUS_QUERY)
    List<? extends T> findByStatus(@Param("status") final SubscriptionStatusInfo.SubscriptionStatusType status);

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription orders
     */
    @Query(FIND_SUB_BY_USER_ID_QUERY)
    List<? extends T> findByUserId(@Param("userId") final Long userId);
}
