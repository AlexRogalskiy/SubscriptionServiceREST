package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface UserSubOrderRepository<T extends UserSubOrder> extends JpaRepository<T, Long> {

    /**
     * Default query to find subscriptions by user id
     */
    //SELECT s FROM Subscription s INNER JOIN s.userOrders o INNER JOIN o.user u WHERE u.id = :userId
    //SELECT o.subscription FROM UserSubOrder o WHERE o.user.id = :userId
    //SELECT s FROM Subscription s INNER JOIN s.userOrders o WHERE o.user.id = :userId
    public final static String FIND_SUB_BY_USER_ID_QUERY = "SELECT o.subscription FROM UserSubOrder o INNER JOIN o.user u WHERE u.id = :userId";

    /**
     * Default query to find subscribed users by subscription type and date
     * before
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o INNER JOIN o.subscription s WHERE s.type = :subType AND o.createdAt > :subDate
    public final static String FIND_SUB_USER_BY_SUB_TYPE_AND_DATE_AFTER_QUERY = "SELECT o.user FROM UserSubOrder o INNER JOIN o.subscription s WHERE s.type = :subType AND o.createdAt > :subDate";

    /**
     * Default query to find subscribed users by subscription type and date
     * after
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o INNER JOIN o.subscription s WHERE s.type = :subType AND o.createdAt <= :subDate
    public final static String FIND_SUB_USER_BY_SUB_TYPE_AND_DATE_BEFORE_QUERY = "SELECT o.user FROM UserSubOrder o INNER JOIN o.subscription s WHERE s.type = :subType AND o.createdAt <= :subDate";

    /**
     * Default query to find subscribed users by date before
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o WHERE o.createdAt > :subDate
    public final static String FIND_SUB_USER_BY_DATE_AFTER_QUERY = "SELECT o.user FROM UserSubOrder o WHERE o.createdAt > :subDate";

    /**
     * Default query to find subscribed users by date after
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o WHERE o.createdAt <= :subDate
    public final static String FIND_SUB_USER_BY_DATE_BEFORE_QUERY = "SELECT o.user FROM UserSubOrder o WHERE o.createdAt <= :subDate";

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
     * Get list of subscription entities by user ID
     *
     * @param userId - user identifier
     * @return list of subscription entities
     */
    @Query(FIND_SUB_BY_USER_ID_QUERY)
    List<T> findByUserId(@Param("userId") final Long userId);

    /**
     * Get list of subscribed users by subscription type after (excluding)
     * particular date
     *
     * @param subDate - request date
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_SUB_USER_BY_SUB_TYPE_AND_DATE_BEFORE_QUERY)
    List<T> findBySubscriptionTypeAndDateBefore(@Param("subDate") final Date subDate, @Param("subType") final Subscription.SubscriptionType subType);

    /**
     * Get list of subscribed users by subscription type before (including)
     * particular date
     *
     * @param subDate - request date
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_SUB_USER_BY_SUB_TYPE_AND_DATE_AFTER_QUERY)
    List<T> findBySubscriptionTypeAndDateAfter(@Param("subDate") final Date subDate, @Param("subType") final Subscription.SubscriptionType subType);

    /**
     * Get list of subscribed users after (excluding) particular date
     *
     * @param subDate - request date
     * @return list of user entities
     */
    @Query(FIND_SUB_USER_BY_DATE_BEFORE_QUERY)
    List<T> findByDateBefore(@Param("subDate") final Date subDate);

    /**
     * Get list of subscribed users before (including) particular date
     *
     * @param subDate - request date
     * @return list of user entities
     */
    @Query(FIND_SUB_USER_BY_DATE_AFTER_QUERY)
    List<T> findByDateAfter(@Param("subDate") final Date subDate);
}
