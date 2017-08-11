package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;

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
     * Default query to find subscriptions by user id
     */
    //SELECT s FROM Subscription s INNER JOIN s.userOrders o INNER JOIN o.user u WHERE u.id = :userId
    public final static String FIND_BY_USERID_QUERY = "SELECT s FROM Subscription s INNER JOIN s.userOrders o WHERE o.user.id = :userId";

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
     * Get list of subscription entities by type
     *
     * @param type - subscription type
     * @return list of subscription entities
     */
    List<T> findByType(final Subscription.SubscriptionType type);

    /**
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    @Query(FIND_BY_USERID_QUERY)
    List<T> findByUserId(@Param("userId") final Long userId);

    /**
     * Get list of subscription entities by user
     *
     * @param user - user entity
     * @return list of subscription entities
     */
    List<T> findByUser(final User user);
}
