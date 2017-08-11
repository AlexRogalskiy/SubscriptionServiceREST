package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.repositories.SubscriptionRepository;
import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SubscriptionServiceImpl<T extends Subscription> implements SubscriptionService<T> {

    @Autowired
    private SubscriptionRepository<T> subscriptionRepository;

    @Override
    public List<T> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public T findById(final Long id) {
        return subscriptionRepository.findOne(id);
    }

    @Override
    public T findByName(final String name) {
        return subscriptionRepository.findByName(name);
    }

    @Override
    public void save(final T subscription) {
        subscriptionRepository.save(subscription);
    }

    @Override
    public void update(final T subscription) {
        save(subscription);
    }

    @Override
    public void deleteById(final Long id) {
        subscriptionRepository.delete(id);
    }

    @Override
    public boolean isExist(final T subscription) {
        return Objects.nonNull(findByName(subscription.getName()));
    }

    @Override
    public void deleteAll() {
        subscriptionRepository.deleteAll();
    }

    @Override
    public List<T> findByUserId(final Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    public List<T> findByNamePattern(final String pattern) {
        return subscriptionRepository.findByNameLike(pattern);
    }
}
