package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * Subscription REST Application storage repository
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@Repository
public interface SubscriptionRepository<T extends Subscription> extends JpaRepository<T, Long> {

    T findByName(final String name);

    T findByUserId(final Long userId);

}
