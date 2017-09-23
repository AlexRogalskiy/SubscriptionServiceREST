package com.wildbeeslabs.rest.service;

//import com.wildbeeslabs.api.rest.common.service.JpaBaseServiceImpl;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;
import com.wildbeeslabs.rest.model.UserSubOrderId;
import com.wildbeeslabs.rest.repository.UserSubOrderRepository;
import com.wildbeeslabs.rest.service.interfaces.IUserSubOrderService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
public class UserSubOrderServiceImpl<T extends UserSubOrder> extends JpaBaseServiceImpl<T, UserSubOrderId, UserSubOrderRepository<T>> implements IUserSubOrderService<T> {

//    @Override
//    public T findById(final UserSubOrderId id) {
//        return getRepository().findById(id);
//    }
    //@Override
    //public void deleteById(final UserSubOrderId id) {
    //    userSubOrderRepository.deleteById(id);
    //}
    @Override
    public boolean isExist(final T userSubOrder) {
        return Objects.nonNull(findById(userSubOrder.getId()));
    }

    @Override
    public List<T> findByUser(final User user) {
        return getRepository().findByUser(user);
    }

    @Override
    public List<T> findBySubscription(final Subscription subscription) {
        return getRepository().findBySubscription(subscription);
    }

//    @Override
//    public void merge(final T itemTo, final T itemFrom) {
//        itemFrom.setId(itemTo.getId());
//        update(itemFrom);
//    }
    @Override
    public List<T> findBySubscribedAtBetween(final Date dateFrom, final Date dateTo) {
        return getRepository().findBySubscribedAtBetween(dateFrom, dateTo);
    }

    @Override
    public T findByUserAndSubscription(final User user, final Subscription subscription) {
        final UserSubOrder order = new UserSubOrder();
        order.setSubscription(subscription);
        order.setUser(user);
        return this.findById(order.getId());
    }
}
