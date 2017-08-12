package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.repositories.UserRepository;
import com.wildbeeslabs.rest.service.interfaces.UserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<T> findAll() {
        return userRepository.findAll();
    }

    @Override
    public T findById(final Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public T findByLogin(final String name) {
        return userRepository.findByLogin(name);
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
    public void deleteById(final Long id) {
        userRepository.delete(id);
    }

    @Override
    public boolean isExist(final T user) {
        return Objects.nonNull(findByLogin(user.getLogin()));
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<T> findBySubscriptionTypeAndDateBefore(final Date subDate, final Subscription.SubscriptionType subType) {
        return userRepository.findBySubscriptionTypeAndDateBefore(subDate, subType);
    }

    @Override
    public List<T> findBySubscriptionTypeAndDateAfter(final Date subDate, final Subscription.SubscriptionType subType) {
        return userRepository.findBySubscriptionTypeAndDateAfter(subDate, subType);
    }

    @Override
    public List<T> findByDateBefore(final Date subDate) {
        return userRepository.findByDateBefore(subDate);
    }

    @Override
    public List<T> findByDateAfter(final Date subDate) {
        return userRepository.findByDateAfter(subDate);
    }
}
