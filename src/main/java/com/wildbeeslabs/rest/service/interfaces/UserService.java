package com.wildbeeslabs.rest.service.interfaces;

import com.wildbeeslabs.rest.model.User;

/**
 *
 * User REST Application Service declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
public interface UserService<T extends User> extends BaseService<T> {

    /**
     * Get user entity by login
     *
     * @param login - user login
     * @return user entity
     */
    T findByLogin(final String login);
}
