package com.example.usersapi.service;

import com.example.usersapi.config.JwtUtil;
import com.example.usersapi.exception.EmailInvalidException;
import com.example.usersapi.exception.EntityNotFoundException;
import com.example.usersapi.model.Profile;
import com.example.usersapi.model.Role;
import com.example.usersapi.model.User;
import com.example.usersapi.repository.ProfileRepository;
import com.example.usersapi.repository.RoleRepository;
import com.example.usersapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

/**
 * UserServiceImpl to maintain a separation of concerns between our layers
 * This class will house all the business logic to format and return data from CRUD operations
 */

@Service
public class UserServiceImpl implements UserService {

    /**
     * Autowired - allows Spring to resolve and inject the collaborating bean into this class.
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    JwtUtil jwtUtil;

    /**
     *Create a new bean of type PasswordEncoder to encode the user's password
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * This method gets all users and saves them into a list of users
     * @return a datatype that implements the Iterable interface. In this case a list of users.
     */
    @Override
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    /**
     * This method signs up a new user
     * The password is encoded before saving to database
     * The user's role is added (defaulted to user)
     * @return null.
     */
    @Override
    public List<String> signUp(User user) throws Exception {
        if (!isValidEmail(user.getEmail())) {
            throw new EmailInvalidException("Email validation");
        }
        user.setPassword(encoder().encode(user.getPassword()));
        Role role = roleRepository.findById(1l).orElse(new Role("ROLE_USER"));

        roleRepository.save(role);

        user.addRole(role);
        if(userRepository.save(user) != null) {
            return Arrays.asList( jwtUtil.generateToken(user.getUsername()), user.getUsername());
        } else {
            throw new Exception("Something went wrong. Try again.");
        }
    }

    private boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    /**
     * This method logs in a registered user so that they can interact with our website
     * @param user takes in the user's information to be authenticated
     * @return a list of null.
     */
    @Override
    public List<String> logIn(User user) throws CredentialException {
        User savedUser = userRepository.findByUsername(user.getUsername());
        if( savedUser != null && encoder().matches(user.getPassword(), savedUser.getPassword())) {
            return Arrays.asList( jwtUtil.generateToken(user.getUsername()), savedUser.getUsername());
        } else {
            throw new CredentialException("Invalid email or password");
        }
    }


    /**
     * This method is used to create a user profile
     * @param profile take in profile information from user
     * @param username used to retrieve the user
     * @return Profile created.
     */
    @Override
    public Profile createProfile(Profile profile, String username) throws EntityNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user.getProfile() != null) {
            profile.setId(user.getProfile().getId());
        } else {
            throw new EntityNotFoundException("User does not exist");
        }
        profile.setUser(user);
        profileRepository.save(profile);
        return user.getProfile();
    }

    /**
     * This method is used to get a user's profile
     * @param username used to used to retrieve the user
     * @return user Profile.
     */
    @Override
    public Profile getProfile(String username) {
        User user = userRepository.findByUsername(username);
        return user.getProfile();
    }

}
