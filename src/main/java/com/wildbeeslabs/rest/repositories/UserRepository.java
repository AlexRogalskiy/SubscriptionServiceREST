package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    /**
     * Default query to find subscribed users by subscription type and date
     * before
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o INNER JOIN o.subscription s WHERE s.type = :subType AND o.createdAt > :subDate
    public final static String FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_AFTER_QUERY = "SELECT o.user FROM UserSubOrder o INNER JOIN o.subscription s WHERE s.type = :subType AND o.createdAt > :subDate";

    /**
     * Default query to find subscribed users by subscription type and date
     * after
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o INNER JOIN o.subscription s WHERE s.type = :subType AND o.createdAt <= :subDate
    public final static String FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_BEFORE_QUERY = "SELECT o.user FROM UserSubOrder o INNER JOIN o.subscription s WHERE s.type = :subType AND o.createdAt <= :subDate";

    /**
     * Default query to find subscribed users by date before
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o WHERE o.createdAt > :subDate
    public final static String FIND_USER_BY_SUB_DATE_AFTER_QUERY = "SELECT o.user FROM UserSubOrder o WHERE o.createdAt > :subDate";

    /**
     * Default query to find subscribed users by date after
     */
    //SELECT u FROM User u INNER JOIN u.subOrders o WHERE o.createdAt <= :subDate
    public final static String FIND_USER_BY_SUB_DATE_BEFORE_QUERY = "SELECT o.user FROM UserSubOrder o WHERE o.createdAt <= :subDate";

    /**
     * Get user entity by login
     *
     * @param name - user login
     * @return user entity
     */
    T findByLogin(final String name);

    /**
     * Get list of users entities by subscription type after (excluding) particular date
     *
     * @param subDate - request date
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_BEFORE_QUERY)
    List<T> findBySubscriptionTypeAndDateBefore(@Param("subDate") final Date subDate, @Param("subType") final Subscription.SubscriptionType subType);

    /**
     * Get list of users entities by subscription type before (including) particular date
     *
     * @param subDate - request date
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_AFTER_QUERY)
    List<T> findBySubscriptionTypeAndDateAfter(@Param("subDate") final Date subDate, @Param("subType") final Subscription.SubscriptionType subType);

    /**
     * Get list of users entities after (excluding) particular date
     *
     * @param subDate - request date
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_DATE_BEFORE_QUERY)
    List<T> findByDateBefore(@Param("subDate") final Date subDate);

    /**
     * Get list of users entities before (including) particular date
     *
     * @param subDate - request date
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_DATE_AFTER_QUERY)
    List<T> findByDateAfter(@Param("subDate") final Date subDate);
}
