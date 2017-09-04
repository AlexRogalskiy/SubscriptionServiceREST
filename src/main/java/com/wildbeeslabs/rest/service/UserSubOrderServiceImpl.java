package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.model.UserSubOrderId;
import com.wildbeeslabs.rest.repositories.UserSubOrderRepository;
import com.wildbeeslabs.rest.service.interfaces.UserSubOrderService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * UserSubOrder REST Application Service implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@Service("userSubOrderService")
@Transactional
public class UserSubOrderServiceImpl<T extends UserSubOrder> implements UserSubOrderService<T> {

    @Autowired
    private UserSubOrderRepository<T> userSubOrderRepository;

    @Override
    public T findById(final UserSubOrderId id) {
        return userSubOrderRepository.findById(id);
    }

    @Override
    public void save(final T userSubOrder) {
        userSubOrderRepository.save(userSubOrder);
    }

    @Override
    public void update(final T userSubOrder) {
        save(userSubOrder);
    }

    //@Override
    //public void deleteById(final UserSubOrderId id) {
    //    userSubOrderRepository.deleteById(id);
    //}
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return userSubOrderRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void deleteAll() {
        userSubOrderRepository.deleteAll();
    }

    @Override
    public boolean isExist(final T userSubOrder) {
        return Objects.nonNull(findById(userSubOrder.getPk()));
    }

    @Override
    public List<T> findByUser(final User user) {
        return userSubOrderRepository.findByUser(user);
    }

    @Override
    public List<T> findBySubscription(final Subscription subscription) {
        return userSubOrderRepository.findBySubscription(subscription);
    }

    @Override
    public void merge(final T itemTo, final T itemFrom) {
        itemTo.setExpiredAt(itemFrom.getExpiredAt());
        itemTo.setSubscribedAt(itemFrom.getSubscribedAt());
        itemTo.setModifiedAt(new Date());
        itemTo.setModifiedBy(itemFrom.getModifiedBy());
        update(itemTo);
    }

    @Override
    public void delete(final T item) {
        userSubOrderRepository.delete(item);
    }

    @Override
    public void delete(final List<? extends T> items) {
        userSubOrderRepository.delete(items);
    }

    @Override
    public List<T> findBySubscribedAtBetween(final Date dateFrom, final Date dateTo) {
        return userSubOrderRepository.findBySubscribedAtBetween(dateFrom, dateTo);
    }

    @Override
    public T findByUserAndSubscription(final User user, final Subscription subscription) {
        UserSubOrder order = new UserSubOrder();
        order.setSubscription(subscription);
        order.setUser(user);
        return this.findById(order.getPk());
    }
}
