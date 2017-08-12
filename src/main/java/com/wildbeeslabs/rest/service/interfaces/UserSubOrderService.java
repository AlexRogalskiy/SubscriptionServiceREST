package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import java.util.List;

/**
 *
 * UserSubOrder REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface UserSubOrderService<T extends UserSubOrder> extends BaseService<T> {

    /**
     * Get list of subscription orders by user entity
     *
     * @param user - user entity
     * @return list of subscription orders
     */
    List<T> findByUser(final User user);

    /**
     * Get list of subscription orders by subscription entity
     *
     * @param subscription - subscription entity
     * @return list of subscription orders
     */
    List<T> findBySubscription(final Subscription subscription);
}
