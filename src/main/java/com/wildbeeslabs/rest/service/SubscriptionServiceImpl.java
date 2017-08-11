package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.service.interfaces.SubscriptionService;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 *
 * Subscription REST Application Service implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService<Subscription> {

    private List<Subscription> subscriptions;

    @Override
    public List<Subscription> findAll() {
        return subscriptions;
    }

    @Override
    public Subscription findById(long id) {
        for (Subscription subscription : subscriptions) {
            if (Objects.equals(subscription.getId(), id)) {
                return subscription;
            }
        }
        return null;
    }

    @Override
    public Subscription findByName(String name) {
        for (Subscription subscription : subscriptions) {
            if (subscription.getName().equalsIgnoreCase(name)) {
                return subscription;
            }
        }
        return null;
    }

    @Override
    public void save(Subscription subscription) {
        subscriptions.add(subscription);
    }

    @Override
    public void update(Subscription subscription) {
        int index = subscriptions.indexOf(subscription);
        subscriptions.set(index, subscription);
    }

    @Override
    public void deleteById(long id) {
        for (Iterator<Subscription> iterator = subscriptions.iterator(); iterator.hasNext();) {
            Subscription subscription = iterator.next();
            if (Objects.equals(subscription.getId(), id)) {
                iterator.remove();
            }
        }
    }

    @Override
    public boolean isExist(Subscription subscription) {
        return Objects.nonNull(findByName(subscription.getName()));
    }

    @Override
    public void deleteAll() {
        subscriptions.clear();
    }

    @Override
    public List<Subscription> findByUserId(long userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
