package com.example.usersapi.repository;

import com.example.usersapi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository interface extends CrudRepository to perform CRUD operations on User entity
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * find a user by the user's username
     * @param username
     * @return a user object
     */
    public User findByUsername(String username);
}
