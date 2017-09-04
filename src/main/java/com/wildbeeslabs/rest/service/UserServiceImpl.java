package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.repositories.UserRepository;
import com.wildbeeslabs.rest.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * User REST Application Service implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@Service("userService")
@Transactional
public class UserServiceImpl<T extends User> implements UserService<T> {

    @Autowired
    private UserRepository<T> userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return userRepository.findAll();
    }

    @Override
    public T findById(final Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public T findByLogin(final String name) {
        return userRepository.findByLoginIgnoreCase(name);
    }

    @Override
    public void save(final T user) {
        userRepository.save(user);
    }

    @Override
    public void update(final T user) {
        save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void delete(final T user) {
        userRepository.delete(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void deleteById(final Long id) {
        userRepository.delete(id);
    }

    @Override
    public boolean isExist(final T user) {
        return Objects.nonNull(findByLogin(user.getLogin()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<T> findAllBySubscriptionTypeAndDate(final Date subDate, final Subscription.SubscriptionStatusType subType, final DateTypeOrder dateTypeOrder) {
        if (Objects.nonNull(subDate) && Objects.nonNull(subType)) {
            if (Objects.equals(dateTypeOrder, DateTypeOrder.BEFORE)) {
                return userRepository.findBySubscriptionTypeAndDateBefore(subDate, subType);
            } else if (Objects.equals(dateTypeOrder, DateTypeOrder.AFTER)) {
                return userRepository.findBySubscriptionTypeAndDateAfter(subDate, subType);
            }
        } else {
            if (Objects.nonNull(subDate)) {
                return this.findAllBySubscriptionDate(subDate, dateTypeOrder);
            } else if (Objects.nonNull(subType)) {
                return this.findAllBySubscriptionType(subType);
            }
        }
        return this.findAll();
    }

    @Override
    public List<T> findAllBySubscriptionDate(final Date subDate, final DateTypeOrder dateTypeOrder) {
        if (Objects.equals(dateTypeOrder, DateTypeOrder.BEFORE)) {
            return userRepository.findByDateBefore(subDate);
        } else if (Objects.equals(dateTypeOrder, DateTypeOrder.AFTER)) {
            return userRepository.findByDateAfter(subDate);
        }
        return new ArrayList<>();
    }

    @Override
    public List<T> findAllBySubscriptionType(final Subscription.SubscriptionStatusType subType) {
        return userRepository.findBySubscriptionType(subType);
    }

    @Override
    public void merge(final T itemTo, final T itemFrom) {
        itemTo.setAge(itemFrom.getAge());
        itemTo.setModifiedAt(new Date());
        itemTo.setModifiedBy(itemFrom.getModifiedBy());
        itemTo.setRating(itemFrom.getRating());
        itemTo.setStatus(itemFrom.getStatus());
        itemTo.setSubOrders(itemFrom.getSubOrders());
        update(itemTo);
    }

    @Override
    public void delete(final List<? extends T> items) {
        userRepository.delete(items);
    }

    @Override
    public List<T> findBySubscriptionId(final Long subscriptionId) {
        return userRepository.findBySubscriptionId(subscriptionId);
    }
}
