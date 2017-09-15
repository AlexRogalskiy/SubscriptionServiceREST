package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.User;

import java.util.Date;
import java.util.List;

/**
 *
 * User REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface IUserService<T extends User> extends IBaseService<T> {

    /**
     * Get user entity by login
     *
     * @param login - user login
     * @return user entity
     */
    T findByLogin(final String login);

    /**
     * Get list of user entities by subscription status and subscription date
     * (after - excluding / before - including)
     *
     * @param subDate - subscription date
     * @param subStatus - subscription status
     * @param dateTypeOrder - date type order (before / after)
     * @return list of user entities
     */
    List<T> findAllBySubscriptionStatusAndDate(final Date subDate, final SubscriptionStatusInfo.SubscriptionStatusType subStatus, final DateTypeOrder dateTypeOrder);

    /**
     * Get list of user entities by subscription date (after - excluding /
     * before - including)
     *
     * @param subDate - subscription date
     * @param dateTypeOrder - date type order (before / after)
     * @return - list of user entities
     */
    List<T> findAllBySubscriptionDate(final Date subDate, final DateTypeOrder dateTypeOrder);

    /**
     * Get list of user entities by subscription date between request period
     * (including)
     *
     * @param startSubDate - start date of period
     * @param endSubDate - end date of period
     * @return - list of user entities
     */
    List<T> findAllBySubscriptionDateBetween(final Date startSubDate, final Date endSubDate);

    /**
     * Get list of user entities by subscription status
     *
     * @param subStatus - subscription type
     * @return list of user entities
     */
    List<T> findAllBySubscriptionStatus(final SubscriptionStatusInfo.SubscriptionStatusType subStatus);

    /**
     * Get list of user entities by subscription ID
     *
     * @param subscriptionId - subscription identifier
     * @return list of user entities
     */
    List<T> findBySubscriptionId(final Long subscriptionId);
}
