package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.repositories.UserRepository;
import com.wildbeeslabs.rest.service.interfaces.IUserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class UserServiceImpl<T extends User> implements IUserService<T> {

    @Autowired
    private UserRepository<T> userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(final Pageable pageable) {
        return userRepository.findAll(pageable);
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
    public void create(final T user) {
        user.setId(null);
        save(user);
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
        //return userRepository.exists(Example.of(user));
        return Objects.nonNull(findByLogin(user.getLogin()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public List<T> findAllBySubscriptionStatusAndDate(final Date subDate, final SubscriptionStatusInfo.SubscriptionStatusType subStatus, final DateTypeOrder dateTypeOrder) {
        if (Objects.nonNull(subDate)) {
            return userRepository.findByOptionalSubscriptionStatusAndDate(subDate, subStatus, dateTypeOrder.name());
        } else if (Objects.nonNull(subStatus)) {
            return this.findAllBySubscriptionStatus(subStatus);
        }
        return this.findAll();
    }

    @Override
    public List<T> findAllBySubscriptionDateBetween(final Date startSubDate, final Date endSubDate) {
        return userRepository.findByDateBetween(startSubDate, endSubDate);
    }

    @Override
    public List<T> findAllBySubscriptionDate(final Date subDate, final DateTypeOrder dateTypeOrder) {
        return userRepository.findByDate(subDate, dateTypeOrder.name());
    }

    @Override
    public List<T> findAllBySubscriptionStatus(final SubscriptionStatusInfo.SubscriptionStatusType subStatus) {
        return userRepository.findBySubscriptionStatus(subStatus);
    }

    @Override
    public void merge(final T itemTo, final T itemFrom) {
        itemFrom.setId(itemTo.getId());
        update(itemFrom);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    public void delete(final List<? extends T> items) {
        userRepository.delete(items);
    }

    @Override
    public List<T> findBySubscriptionId(final Long subscriptionId) {
        return userRepository.findBySubscriptionId(subscriptionId);
    }

    @Override
    public List<T> findByStatus(final User.UserStatusType status) {
        return userRepository.findByStatus(status);
    }
}
