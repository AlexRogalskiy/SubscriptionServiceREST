package com.wildbeeslabs.rest.service;

import com.wildbeeslabs.rest.model.User;
import com.wildbeeslabs.rest.service.interfaces.UserService;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

/**
 *
 * User REST Application Service implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Service("userService")
public class UserServiceImpl implements UserService<User> {

    private List<User> users;

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(long id) {
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getLogin().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }

    @Override
    public void deleteById(long id) {
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
            User user = iterator.next();
            if (Objects.equals(user.getId(), id)) {
                iterator.remove();
            }
        }
    }

    @Override
    public boolean isExist(User user) {
        return Objects.nonNull(findByName(user.getLogin()));
    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}
