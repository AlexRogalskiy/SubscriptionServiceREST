package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.repositories.SubscriptionRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wildbeeslabs.rest.service.interfaces.ISubscriptionService;

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
public class SubscriptionServiceImpl<T extends Subscription> implements ISubscriptionService<T> {

    @Autowired
    private SubscriptionRepository<T> subscriptionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public T findById(final Long id) {
        return subscriptionRepository.findOne(id);
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
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void deleteById(final Long id) {
        subscriptionRepository.delete(id);
    }

    @Override
    public boolean isExist(final T subscription) {
        return Objects.nonNull(findByName(subscription.getName()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void deleteAll() {
        subscriptionRepository.deleteAll();
    }

    @Override
    public T findByName(final String name) {
        return subscriptionRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<T> findByPatternName(final String pattern) {
        return subscriptionRepository.findByNameLike(pattern);
    }

    @Override
    public List<T> findByType(final Subscription.SubscriptionStatusType type) {
        return subscriptionRepository.findByType(type);
    }

    @Override
    public List<T> findByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    public void merge(final T itemTo, final T itemFrom) {
        itemTo.setExpireAt(itemFrom.getExpireAt());
//        itemTo.setModifiedAt(new Date());
        itemTo.setModifiedBy(itemFrom.getModifiedBy());
        itemTo.setName(itemFrom.getName());
        itemTo.setType(itemFrom.getType());
        itemTo.setUserOrders(itemFrom.getUserOrders());
        update(itemTo);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void delete(final T item) {
        subscriptionRepository.delete(item);
    }

    @Override
    public void delete(final List<? extends T> items) {
        subscriptionRepository.delete(items);
    }
}
