package com.wildbeeslabs.rest;

import com.wildbeeslabs.rest.model.Subscription;
import com.wildbeeslabs.rest.model.User;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * User/Subscription database Test client
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class UsersSubscriptionsTest {

    public static void main(String[] args) {
        // loads configuration and mappings
        Configuration configuration = new Configuration().configure();
        ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
        registry.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = registry.buildServiceRegistry();

        // builds a session factory from the service registry
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        // obtains the session
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Subscription groupAdmin = new Subscription();
        groupAdmin.setExpireAt(new Date());
        groupAdmin.setName("Administrator Group");
        groupAdmin.setType(Subscription.SubscriptionType.PREMIUM);

        Subscription groupGuest = new Subscription();
        groupGuest.setExpireAt(new Date());
        groupGuest.setName("Guest Group");
        groupGuest.setType(Subscription.SubscriptionType.STANDARD);

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

        groupAdmin.addUser(user1);
        groupAdmin.addUser(user2);
        groupGuest.addUser(user1);

        user1.addSubscription(groupAdmin);
        user2.addSubscription(groupAdmin);
        user1.addSubscription(groupGuest);

        session.save(groupAdmin);
        session.save(groupGuest);

        session.getTransaction().commit();
        session.close();
    }
}
