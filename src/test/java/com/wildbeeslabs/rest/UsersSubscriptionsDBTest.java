package com.wildbeeslabs.rest;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.model.UserSubOrder;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * User/UserSubOrder/Subscription DB Entity Test client
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class UsersSubscriptionsDBTest {

    private static void init(final Session session) {
        session.beginTransaction();

        Subscription groupAdmin = new Subscription();
        groupAdmin.setExpireAt(new Date());
        groupAdmin.setName("Administrator Group");
        groupAdmin.setType(Subscription.SubscriptionStatusType.PREMIUM);

        Subscription groupGuest = new Subscription();
        groupGuest.setExpireAt(new Date());
        groupGuest.setName("Guest Group");
        groupGuest.setType(Subscription.SubscriptionStatusType.STANDARD);

        User user1 = new User();
        user1.setAge(56);
        user1.setLogin("Tom");
        user1.setRating(24.3);
        user1.setStatus(User.UserStatusType.ACTIVE);

        User user2 = new User();
        user2.setAge(65);
        user2.setLogin("Mary");
        user2.setRating(43.3);
        user2.setStatus(User.UserStatusType.ACTIVE);

        UserSubOrder userSubOrder1 = new UserSubOrder();
        userSubOrder1.setCreatedAt(new Date());
        userSubOrder1.setExpireAt(null);
        userSubOrder1.setSubscription(groupAdmin);
        userSubOrder1.setUser(user1);

        UserSubOrder userSubOrder2 = new UserSubOrder();
        userSubOrder2.setCreatedAt(new Date());
        userSubOrder2.setExpireAt(null);
        userSubOrder2.setSubscription(groupGuest);
        userSubOrder2.setUser(user1);

        UserSubOrder userSubOrder3 = new UserSubOrder();
        userSubOrder3.setCreatedAt(new Date());
        userSubOrder3.setExpireAt(null);
        userSubOrder3.setSubscription(groupAdmin);
        userSubOrder3.setUser(user2);

        user1.addSubscription(userSubOrder1);
        user1.addSubscription(userSubOrder2);
        user2.addSubscription(userSubOrder3);

        groupAdmin.addUserOrder(userSubOrder1);
        groupAdmin.addUserOrder(userSubOrder3);
        groupGuest.addUserOrder(userSubOrder2);

        session.save(groupAdmin);
        session.save(groupGuest);
        session.save(user1);
        session.save(user2);
        session.save(userSubOrder1);
        session.save(userSubOrder2);
        session.save(userSubOrder3);

        session.getTransaction().commit();
    }

    public static void main(String[] args) {
        /*Configuration configuration = new Configuration().configure();

        ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
        registry.applySettings(configuration.getProperties());

        ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        init(sessionFactory.openSession());*/
    }
}
