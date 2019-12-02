package com.example.usersapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Profile entity to represent profiles in our data model. A Profile saves a user's additionalEmail, address and mobile fields.
 * We'll be able to perform CRUD operations on the profiles table. {@link com.example.usersapi.repository.ProfileRepository}
 *
 * @author David
 * @author Osman
 *
 * create table in postgres named Profile
 */

@Entity
@Table(name="profile")
public class Profile {
    /**
     * @param id - create column named id and auto generate it's value using hibernate
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String additionalEmail;

    @Column
    private String address;

    @Column
    private String mobile;

    /**
     * @param user - map a one to one relationship with user
     */
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "user_profile_id")
    private User user;

    /**
     * default constructor
     */
    public Profile() {
    }

    /**
     * getters and setters
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdditionalEmail() {
        return additionalEmail;
    }

    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
