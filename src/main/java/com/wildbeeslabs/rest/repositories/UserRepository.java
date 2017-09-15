package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
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
     * Default query to find subscribed users by subscription status (optional)
     * and date after (excluding)
     */
    public final static String FIND_USER_BY_OPT_SUB_STATUS_AND_SUB_DATE_AFTER_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE (:subStatus IS NULL OR o.pk.subscription.statusInfo.status = :subStatus) AND o.subscribedAt > :subDate";

    /**
     * Default query to find subscribed users by subscription status (optional)
     * and date before (including)
     */
    public final static String FIND_USER_BY_OPT_SUB_STATUS_AND_SUB_DATE_BEFORE_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE (:subStatus IS NULL OR o.pk.subscription.statusInfo.status = :subStatus) AND o.subscribedAt <= :subDate";

    /**
     * Default query to find subscribed users by subscription status (optional)
     * and date
     */
    public final static String FIND_USER_BY_OPT_SUB_STATUS_AND_SUB_DATE_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE (:subStatus IS NULL OR o.pk.subscription.statusInfo.status = :subStatus) AND ((:order = 'BEFORE' AND o.subscribedAt <= :subDate) OR o.subscribedAt >= :subDate)";

    /**
     * Default query to find subscribed users by date after (excluding)
     */
    public final static String FIND_USER_BY_SUB_DATE_AFTER_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE o.subscribedAt > :subDate";

    /**
     * Default query to find subscribed users by date before (including)
     */
    public final static String FIND_USER_BY_SUB_DATE_BEFORE_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE o.subscribedAt <= :subDate";

    /**
     * Default query to find subscribed users by date
     */
    public final static String FIND_USER_BY_SUB_DATE_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE ((:order = 'BEFORE' AND o.subscribedAt <= :subDate) OR o.subscribedAt >= :subDate)";

    /**
     * Default query to find subscribed users by date between (including)
     */
    public final static String FIND_USER_BY_SUB_DATE_BETWEEN_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE o.subscribedAt BETWEEN :startSubDate AND :endSubDate";

    /**
     * Default query to find subscribed users by subscription status
     */
    public final static String FIND_USER_BY_SUB_STATUS_QUERY = "SELECT DISTINCT o.pk.user FROM UserSubOrder o WHERE o.pk.subscription.statusInfo.status = :subStatus";

    /**
     * Get user entity by login (case insensitive)
     *
     * @param name - user login
     * @return user entity
     */
    T findByLoginIgnoreCase(final String name);

    /**
     * Get list of user entities by status
     *
     * @param status - user status
     * @return list of user entities
     */
    List<T> findByStatus(final User.UserStatusType status);

    /**
     * Get list of user entities by subscription status (optional) and date
     * before
     *
     * @param subDate - request date (including)
     * @param subStatus - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_OPT_SUB_STATUS_AND_SUB_DATE_BEFORE_QUERY)
    List<T> findByOptionalSubscriptionStatusAndDateBefore(@Param("subDate") final Date subDate, @Param("subStatus") final SubscriptionStatusInfo.SubscriptionStatusType subStatus);

    /**
     * Get list of user entities by subscription status (optional) and date
     * after
     *
     * @param subDate - request date (excluding)
     * @param subStatus - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_OPT_SUB_STATUS_AND_SUB_DATE_AFTER_QUERY)
    List<T> findByOptionalSubscriptionStatusAndDateAfter(@Param("subDate") final Date subDate, @Param("subStatus") final SubscriptionStatusInfo.SubscriptionStatusType subStatus);

    /**
     * Get list of user entities by subscription status (optional) and date
     *
     * @param subDate - request date (excluding)
     * @param subStatus - subscription type
     * @param order - order of comparison (date after / before)
     * @return list of user entities
     */
    @Query(FIND_USER_BY_OPT_SUB_STATUS_AND_SUB_DATE_QUERY)
    List<T> findByOptionalSubscriptionStatusAndDate(@Param("subDate") final Date subDate, @Param("subStatus") final SubscriptionStatusInfo.SubscriptionStatusType subStatus, @Param("order") final String order);

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
     * Get list of user entities by date
     *
     * @param subDate - request date (excluding)
     * @param order - order of comparison (date after / before)
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_DATE_QUERY)
    List<T> findByDate(@Param("subDate") final Date subDate, @Param("order") final String order);

    /**
     * Get list of user entities by subscription type
     *
     * @param subStatus - subscription type
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_STATUS_QUERY)
    List<T> findBySubscriptionStatus(@Param("subStatus") final SubscriptionStatusInfo.SubscriptionStatusType subStatus);

    /**
     * Get list of user entities by subscription ID
     *
     * @param subscriptionId - subscription identifier
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_ID_QUERY)
    List<T> findBySubscriptionId(@Param("subscriptionId") final Long subscriptionId);

    /**
     * Get list of user entities by subscription date between (including)
     *
     * @param startSubDate - request start date (including)
     * @param endSubDate - request end date (including)
     * @return list of user entities
     */
    @Query(FIND_USER_BY_SUB_DATE_BETWEEN_QUERY)
    List<T> findByDateBetween(@Param("startSubDate") final Date startSubDate, @Param("endSubDate") final Date endSubDate);
}
