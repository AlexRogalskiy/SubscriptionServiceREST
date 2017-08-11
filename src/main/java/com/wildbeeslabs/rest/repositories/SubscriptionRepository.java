package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * Get subscription entity by name
     *
     * @param name - subscription name
     * @return subscription entity
     */
    T findByName(final String name);

    /**
     * Get list of subscription entities by name pattern
     *
     * @param name - subscription name pattern
     * @return list of subscription entities
     */
    List<T> findByNameLike(final String name);

    /**
     * Get list of subscription entities by type
     *
     * @param type - subscription type
     * @return list of subscription entities
     */
    List<T> findByType(final Subscription.SubscriptionType type);
}
