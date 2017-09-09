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
     * Default query to find users by subscription id
     */
    public final static String FIND_USER_BY_SUB_ID_QUERY = "SELECT o.pk.user FROM UserSubOrder o WHERE o.pk.subscription.id = :subscriptionId";

    /**
     * Default query to find subscribed users by subscription type and date
     * after (excluding)
     */
    public final static String FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_AFTER_QUERY = "SELECT o.pk.user FROM UserSubOrder o WHERE o.pk.subscription.type = :subType AND o.subscribedAt > :subDate";

    /**
     * Default query to find subscribed users by subscription type and date
     * before (including)
     */
    public final static String FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_BEFORE_QUERY = "SELECT o.pk.user FROM UserSubOrder o WHERE o.pk.subscription.type = :subType AND o.subscribedAt <= :subDate";

    /**
     * Default query to find subscribed users by date after (excluding)
     */
    public final static String FIND_USER_BY_SUB_DATE_AFTER_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE o.subscribedAt > :subDate";

    /**
     * Default query to find subscribed users by date before (including)
     */
    public final static String FIND_USER_BY_SUB_DATE_BEFORE_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE o.subscribedAt <= :subDate";

    /**
     * Default query to find subscribed users by subscription type
     */
    public final static String FIND_USER_BY_SUB_TYPE_QUERY = "SELECT o.pk.user FROM UserSubOrder o WHERE o.pk.subscription.type = :subType";

    /**
     * Get user entity by login (case insensitive)
     *
     * @param name - user login
     * @return user entity
     */
    T findByLoginIgnoreCase(final String name);

    /**
     * Get list of user entities by subscription type and date before
     *
     * @param subDate - request date (including)
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_BEFORE_QUERY)
    List<T> findBySubscriptionTypeAndDateBefore(@Param("subDate") final Date subDate, @Param("subType") final Subscription.SubscriptionStatusType subType);

    /**
     * Get list of user entities by subscription type and date after
     *
     * @param subDate - request date (excluding)
     * @param subType - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_TYPE_AND_SUB_DATE_AFTER_QUERY)
    List<T> findBySubscriptionTypeAndDateAfter(@Param("subDate") final Date subDate, @Param("subType") final Subscription.SubscriptionStatusType subType);

    /**
     * Get list of user entities by date before (including)
     *
     * @param subDate - request date (including)
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_DATE_BEFORE_QUERY)
    List<T> findByDateBefore(@Param("subDate") final Date subDate);

    /**
     * Get list of user entities by date after (excluding)
     *
     * @param subDate - request date (excluding)
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

    /**
     * Get list of user entities by subscription ID
     *
     * @param subscriptionId - subscription identifier
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_ID_QUERY)
    List<T> findBySubscriptionId(@Param("subscriptionId") final Long subscriptionId);
}
