package com.example.usersapi.model;

import java.util.List;

/**
 * Jason Web Token model to create a Token when user signs up and logs in
 * JWT has a username
 * @author David
 * @author Osman
 *
 */

public class JwtResponse {

    private String jwt;
    private String username;

    /**
     * @param jwt constructor with jwt as argument
     */
    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }

    /**
     * constructor with jwt and username as arguments
     */
    public JwtResponse(List<String> list) {
    	this.jwt = list.get(0);
    	this.username = list.get(1);
    }

    /**
     * getters and setters
     */
    public String getToken() {
        return this.jwt;
    }
    
    public String getusername() {
    	return username;
    }
    
}