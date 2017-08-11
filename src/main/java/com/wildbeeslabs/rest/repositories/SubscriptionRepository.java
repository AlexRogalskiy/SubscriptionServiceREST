package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
public interface SubscriptionRepository<T extends Subscription> extends JpaRepository<T, Long> {

    /**
     * Default find_by_user_id select query
     */
    public final static String FIND_BY_USERID_QUERY = "SELECT sub FROM Subscription sub INNER JOIN p.userOrders order WHERE order.user.id = :userId";

    /**
     * Get subscription entity by name
     *
     * @param name - subscription name
     * @return subscription entity
     */
    T findByName(final String name);

    /**
     * Get list of subscription entities by name pattern
     *
     * @param name - subscription name pattern
     * @return list of subscription entities
     */
    List<T> findByNameLike(final String name);

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    List<T> findByUserId(final Long userId);

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    @Query(FIND_BY_USERID_QUERY)
    public List<T> findByUser(@Param("userId") final Long userId);

}
