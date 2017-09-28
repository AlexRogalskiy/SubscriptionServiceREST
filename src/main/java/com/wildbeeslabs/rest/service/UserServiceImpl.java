package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.repository.UserRepository;
import com.wildbeeslabs.rest.service.interfaces.IUserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
public class UserServiceImpl<T extends User> extends JpaBaseServiceImpl<T, Long, UserRepository<T>> implements IUserService<T> {

    @Override
    public Optional<T> findByLogin(final String name) {
        return getRepository().findByLoginIgnoreCase(name);
    }

    @Override
    public boolean isExist(final T user) {
        return findByLogin(user.getLogin()).isPresent();
    }

    @Override
    public List<? extends T> findAllBySubscriptionStatusAndDate(final Date subDate, final SubscriptionStatusInfo.SubscriptionStatusType subStatus, final DateTypeOrder dateTypeOrder) {
        if (Objects.nonNull(subDate)) {
            return getRepository().findByOptionalSubscriptionStatusAndDate(subDate, subStatus, dateTypeOrder.name());
        } else if (Objects.nonNull(subStatus)) {
            return this.findAllBySubscriptionStatus(subStatus);
        }
        return this.findAll();
    }

    @Override
    public List<? extends T> findAllBySubscriptionDateBetween(final Date startSubDate, final Date endSubDate) {
        return getRepository().findByDateBetween(startSubDate, endSubDate);
    }

    @Override
    public List<? extends T> findAllBySubscriptionDate(final Date subDate, final DateTypeOrder dateTypeOrder) {
        return getRepository().findByDate(subDate, dateTypeOrder.name());
    }

    @Override
    public List<? extends T> findAllBySubscriptionStatus(final SubscriptionStatusInfo.SubscriptionStatusType subStatus) {
        return getRepository().findBySubscriptionStatus(subStatus);
    }

    @Override
    public List<? extends T> findBySubscriptionId(final Long subscriptionId) {
        return getRepository().findBySubscriptionId(subscriptionId);
    }

    @Override
    public List<? extends T> findByStatus(final User.UserStatusType status) {
        return getRepository().findByStatus(status);
    }
}
