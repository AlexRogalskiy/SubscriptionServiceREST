package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * User REST Application storage repository
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@Repository
public interface UserRepository<T extends User> extends BaseRepository<T> {

    /**
     * Default query to find subscribed users by subscription type and date (after)
     * before
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o INNER JOIN o.subscription s WHERE s.type = :subType AND o.subscribedAt > :subDate
    public final static String FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_AFTER_QUERY = "SELECT o.pk.user FROM UserSubOrder o INNER JOIN o.pk.subscription s WHERE s.type = :subType AND o.subscribedAt > :subDate";

    /**
     * Default query to find subscribed users by subscription type and date (before)
     * after
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o INNER JOIN o.subscription s WHERE s.type = :subType AND o.subscribedAt <= :subDate
    public final static String FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_BEFORE_QUERY = "SELECT o.pk.user FROM UserSubOrder o INNER JOIN o.pk.subscription s WHERE s.type = :subType AND o.subscribedAt <= :subDate";

    /**
     * Default query to find subscribed users by date (after)
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o WHERE o.subscribedAt > :subDate
    public final static String FIND_USER_BY_SUB_DATE_AFTER_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE o.subscribedAt > :subDate";

    /**
     * Default query to find subscribed users by date after (before)
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o WHERE o.subscribedAt <= :subDate
    public final static String FIND_USER_BY_SUB_DATE_BEFORE_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE o.subscribedAt <= :subDate";

    /**
     * Default query to find subscribed users by subscription type
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o INNER JOIN o.subscription s WHERE s.type = :subType
    public final static String FIND_USER_BY_SUB_TYPE_QUERY = "SELECT o.pk.user FROM UserSubOrder o INNER JOIN o.pk.subscription s WHERE s.type = :subType";

    /**
     * Get user entity by login (case insensitive)
     *
     * @param name - user login
     * @return user entity
     */
    T findByLoginIgnoreCase(final String name);

    /**
     * Get list of user entities by subscription type after (excluding)
     * particular date
     *
     * @param subDate - request date
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_BEFORE_QUERY)
    List<T> findBySubscriptionTypeAndDateBefore(@Param("subDate") final Date subDate, @Param("subType") final Subscription.SubscriptionStatusType subType);

    /**
     * Get list of user entities by subscription type before (including)
     * particular date
     *
     * @param subDate - request date
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_AFTER_QUERY)
    List<T> findBySubscriptionTypeAndDateAfter(@Param("subDate") final Date subDate, @Param("subType") final Subscription.SubscriptionStatusType subType);

    /**
     * Get list of user entities after (excluding) particular date
     *
     * @param subDate - request date
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_DATE_BEFORE_QUERY)
    List<T> findByDateBefore(@Param("subDate") final Date subDate);

    /**
     * Get list of user entities before (including) particular date
     *
     * @param subDate - request date
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_DATE_AFTER_QUERY)
    List<T> findByDateAfter(@Param("subDate") final Date subDate);

    /**
     * Get list of user entities by subscription type
     *
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_TYPE_QUERY)
    List<T> findBySubscriptionType(@Param("subType") final Subscription.SubscriptionStatusType subType);
}
