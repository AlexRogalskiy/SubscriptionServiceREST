package com.wildbeeslabs.rest.model;

import java.util.Objects;

/**
 *
 * User REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
public class User {

    private long id;

    private String login;

    private int age;

    private double rating;

    private UserStatusType status;

    public static enum UserStatusType {
        ACTIVE, BLOCKED, UNVERIFIED
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public UserStatusType getStatus() {
        return status;
    }

    public void setStatus(UserStatusType status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (Double.doubleToLongBits(this.rating) != Double.doubleToLongBits(other.rating)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return this.status == other.status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.login);
        hash = 97 * hash + this.age;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.rating) ^ (Double.doubleToLongBits(this.rating) >>> 32));
        hash = 97 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("User {id: %d, login: %s, age: %d, rating: %f, status: %s}", this.id, this.login, this.age, this.rating, this.status);
    }
}
