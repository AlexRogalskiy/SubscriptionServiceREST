package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.repository.SubscriptionRepository;
import com.wildbeeslabs.rest.service.interfaces.ISubscriptionService;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Subscription REST Application Service implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@Service("subscriptionService")
@Transactional
public class SubscriptionServiceImpl<T extends Subscription> extends JpaBaseServiceImpl<T, Long, SubscriptionRepository<T>> implements ISubscriptionService<T> {

    @Override
    public boolean isExist(final T subscription) {
        return Objects.nonNull(findByName(subscription.getName()));
    }

    @Override
    public T findByName(final String name) {
        return getRepository().findByNameIgnoreCase(name);
    }

    @Override
    public List<? extends T> findByPatternName(final String pattern) {
        return getRepository().findByNameLike(pattern);
    }

    @Override
    public List<? extends T> findByStatus(final SubscriptionStatusInfo.SubscriptionStatusType status) {
        return getRepository().findByStatus(status);
    }

    @Override
    public List<? extends T> findByUserId(final Long userId) {
        return getRepository().findByUserId(userId);
    }
}
