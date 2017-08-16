package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import static com.wildbeeslabs.rest.repositories.UserRepository.FIND_USER_BY_SUB_TYPE_QUERY;
import java.util.Date;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

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
public interface UserSubOrderRepository<T extends UserSubOrder> extends BaseRepository<T> {

    /**
     * Default query to find orders by user id
     */
    public final static String FIND_ORDER_BY_USER_ID_QUERY = "SELECT o FROM UserSubOrder o INNER JOIN o.pk.user u WHERE u.id = :userId";

    /**
     * Default query to find orders by user entity
     */
    public final static String FIND_ORDER_BY_USER_QUERY = "SELECT o FROM UserSubOrder o WHERE o.pk.user = :user";

    /**
     * Default query to find orders by subscription id
     */
    public final static String FIND_ORDER_BY_SUB_ID_QUERY = "SELECT o FROM UserSubOrder o INNER JOIN o.pk.subscription s WHERE s.id = :subscriptionId";

    /**
     * Default query to find orders by subscription entity
     */
    public final static String FIND_ORDER_BY_SUB_QUERY = "SELECT o FROM UserSubOrder o WHERE o.pk.subscription = :subscription";

    /**
     * Get list of subscription orders by user id
     *
     * @param userId - user identifier
     * @return list of subscription orders
     */
    @Query(FIND_ORDER_BY_USER_ID_QUERY)
    List<T> findByUserId(final Long userId);

    /**
     * Get list of subscription orders by user entity
     *
     * @param user - user entity
     * @return list of subscription orders
     */
    @Query(FIND_ORDER_BY_USER_QUERY)
    List<T> findByUser(final User user);

    /**
     * Get list of subscription orders by subscription id
     *
     * @param subscriptionId - subscription identifier
     * @return list of subscription orders
     */
    @Query(FIND_ORDER_BY_SUB_ID_QUERY)
    List<T> findBySubscriptionId(final Long subscriptionId);

    /**
     * Get list of subscription orders by subscription entity
     *
     * @param subscription - subscription entity
     * @return list of subscription orders
     */
    @Query(FIND_ORDER_BY_SUB_QUERY)
    List<T> findBySubscription(final Subscription subscription);

    /**
     * Get list of subscription orders by date range (from / to)
     *
     * @param dateFrom - start date of range
     * @param dateTo - end date of range
     * @return list of subscription ordersF
     */
    List<T> findBySubscribedAtBetween(final Date dateFrom, final Date dateTo);

    /**
     * Get list of subscription orders before (including) particular date
     *
     * @param date - request date
     * @return list of subscription orders
     */
    List<T> findBySubscribedAtLessThanEqual(final Date date);

    /**
     * Get list of subscription orders after (excluding) particular date
     *
     * @param date - request date
     * @return list of subscription orders
     */
    List<T> findBySubscribedAtGreaterThan(final Date date);
}
