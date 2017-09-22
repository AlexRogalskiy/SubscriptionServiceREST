package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.SubscriptionStatusInfo;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.repositories.UserRepository;
import com.wildbeeslabs.rest.service.interfaces.IUserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
public class UserServiceImpl<T extends User> extends BaseServiceImpl<T, UserRepository<T>> implements IUserService<T> {

    @Override
    public T findByLogin(final String name) {
        return getRepository().findByLoginIgnoreCase(name);
    }

    @Override
    public boolean isExist(final T user) {
        return Objects.nonNull(findByLogin(user.getLogin()));
    }

    @Override
    public List<T> findAllBySubscriptionStatusAndDate(final Date subDate, final SubscriptionStatusInfo.SubscriptionStatusType subStatus, final DateTypeOrder dateTypeOrder) {
        if (Objects.nonNull(subDate)) {
            return getRepository().findByOptionalSubscriptionStatusAndDate(subDate, subStatus, dateTypeOrder.name());
        } else if (Objects.nonNull(subStatus)) {
            return this.findAllBySubscriptionStatus(subStatus);
        }
        return this.findAll();
    }

    @Override
    public List<T> findAllBySubscriptionDateBetween(final Date startSubDate, final Date endSubDate) {
        return getRepository().findByDateBetween(startSubDate, endSubDate);
    }

    @Override
    public List<T> findAllBySubscriptionDate(final Date subDate, final DateTypeOrder dateTypeOrder) {
        return getRepository().findByDate(subDate, dateTypeOrder.name());
    }

    @Override
    public List<T> findAllBySubscriptionStatus(final SubscriptionStatusInfo.SubscriptionStatusType subStatus) {
        return getRepository().findBySubscriptionStatus(subStatus);
    }

    @Override
    public List<T> findBySubscriptionId(final Long subscriptionId) {
        return getRepository().findBySubscriptionId(subscriptionId);
    }

    @Override
    public List<T> findByStatus(final User.UserStatusType status) {
        return getRepository().findByStatus(status);
    }
}
