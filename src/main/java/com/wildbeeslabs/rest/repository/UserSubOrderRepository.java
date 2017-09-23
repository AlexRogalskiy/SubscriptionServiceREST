package com.wildbeeslabs.rest.repository;

import com.wildbeeslabs.api.rest.common.repository.BaseRepository;
//import com.wildbeeslabs.api.rest.common.repository.JpaBaseRepository;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.model.UserSubOrderId;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
public interface UserSubOrderRepository<T extends UserSubOrder> extends JpaBaseRepository<T, UserSubOrderId>, BaseRepository<T> {

    /**
     * Default query to find order by id
     */
    public final static String FIND_ORDER_BY_ID_QUERY = "SELECT o FROM UserSubOrder o WHERE o.id = :userSubOrderId";

    /**
     * Default query to delete order by id
     */
    public final static String DELETE_ORDER_BY_ID_QUERY = "DELETE FROM UserSubOrder o WHERE o.id = :userSubOrderId";

    /**
     * Default query to find orders by user id
     */
    public final static String FIND_ORDER_BY_USER_ID_QUERY = "SELECT o FROM UserSubOrder o WHERE o.id.user.id = :userId";

    /**
     * Default query to find orders by user entity
     */
    public final static String FIND_ORDER_BY_USER_QUERY = "SELECT o FROM UserSubOrder o WHERE o.id.user = :user";

    /**
     * Default query to find orders by subscription id
     */
    public final static String FIND_ORDER_BY_SUB_ID_QUERY = "SELECT o FROM UserSubOrder o WHERE o.id.subscription.id = :subscriptionId";

    /**
     * Default query to find orders by subscription entity
     */
    public final static String FIND_ORDER_BY_SUB_QUERY = "SELECT o FROM UserSubOrder o WHERE o.id.subscription = :subscription";

    /**
     * Get list of subscription orders by user id
     *
     * @param userId - user identifier
     * @return list of subscription orders
     */
    @Query(FIND_ORDER_BY_USER_ID_QUERY)
    List<T> findByUserId(@Param("userId") final Long userId);

    /**
     * Get list of subscription orders by user entity
     *
     * @param user - user entity
     * @return list of subscription orders
     */
    @Query(FIND_ORDER_BY_USER_QUERY)
    List<T> findByUser(@Param("user") final User user);

    /**
     * Get list of subscription orders by subscription id
     *
     * @param subscriptionId - subscription identifier
     * @return list of subscription orders
     */
    @Query(FIND_ORDER_BY_SUB_ID_QUERY)
    List<T> findBySubscriptionId(@Param("subscriptionId") final Long subscriptionId);

    /**
     * Get list of subscription orders by subscription entity
     *
     * @param subscription - subscription entity
     * @return list of subscription orders
     */
    @Query(FIND_ORDER_BY_SUB_QUERY)
    List<T> findBySubscription(@Param("subscription") final Subscription subscription);

    /**
     * Get subscription order by id
     *
     * @param userSubOrderId - subscription order identifier
     * @return subscription order
     */
    @Query(FIND_ORDER_BY_ID_QUERY)
    T findById(@Param("userSubOrderId") final UserSubOrderId userSubOrderId);

    /**
     * Delete subscription order by id
     *
     * @param userSubOrderId - subscription order identifier
     */
    //@Modifying
    //@Query(DELETE_ORDER_BY_ID_QUERY)
    //void deleteById(@Param("userSubOrderId") final UserSubOrderId userSubOrderId);
    /**
     * Get list of subscription orders by subscribed date period
     *
     * @param dateFrom - start date of period
     * @param dateTo - end date of period
     * @return list of subscription orders
     */
    List<T> findBySubscribedAtBetween(final Date dateFrom, final Date dateTo);

    /**
     * Get list of subscription orders by subscribed date before
     *
     * @param date - request date (including)
     * @return list of subscription orders
     */
    List<T> findBySubscribedAtLessThanEqual(final Date date);

    /**
     * Get list of subscription orders by subscribed date after
     *
     * @param date - request date (excluding)
     * @return list of subscription orders
     */
    List<T> findBySubscribedAtGreaterThan(final Date date);
}
