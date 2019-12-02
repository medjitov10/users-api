package com.example.usersapi.service;

import com.example.usersapi.exception.EntityNotFoundException;
import com.example.usersapi.model.Profile;
import com.example.usersapi.model.User;
import org.springframework.http.HttpStatus;

import javax.security.auth.login.CredentialException;
import java.util.List;

/**
 * interface UserService to create proper MVC layers
 * classes that implement the UserService interface are required to implement these methods
 */
public interface UserService {
    /**
     * This method gets all users and saves them into a list of users
     * @return a datatype that implements the Iterable interface. In this case a list of users.
     */

    public Iterable<User> listUsers();

    /**
     * This method signs up a new user
     * @param user takes in the user information in the form of a user object to save to database
     * @return a list of users.
     */
    public List<String> signUp(User user) throws Exception;

    /**
     * This method logs in a registered user so that they can interact with our website
     * @param user takes in the user's information to be authenticated
     * @return a list of users.
     */
    public List<String> logIn(User user) throws CredentialException;

    /**
     * This method is used to create a user profile
     * @param profile take in profile information from user
     * @param tokenHeader used to authenticate the user
     * @return Profile created.
     */
    public Profile createProfile(Profile profile, String tokenHeader) throws EntityNotFoundException;

    /**
     * This method is used to get a user's profile
     * @param token used to authenticate the user
     * @return user Profile.
     */
    public Profile getProfile(String token);


    /**
     * This method is used to update a user profile
     * @param profile to take in profile information that the user wants to change
     * @param tokenHeader used to authenticate the user
     * @return updated Profile.
     */

//    public User searchById(long id);
//
//    public Iterable<User> searchByName(String name);
//
//    public HttpStatus deleteUser(long id);
//
//    public HttpStatus createUser(User user);
//
//    public HttpStatus updateUser(long id, User userRequest);
}
