package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * User REST Application storage repository
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@Repository
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    T findByName(final String name);

}
