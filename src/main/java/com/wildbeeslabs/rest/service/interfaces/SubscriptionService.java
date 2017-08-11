package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.rest.model.Subscription;
import java.util.List;

/**
 *
 * Subscription REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface SubscriptionService<T extends Subscription> extends BaseService<T> {

    public List<T> findByUserId(long userId);
}
