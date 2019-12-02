package com.example.usersapi.repository;


import com.example.usersapi.model.Profile;
import org.springframework.data.repository.CrudRepository;

/**
 * ProfileRepository interface extends CrudRepository to perform CRUD operations on Profile entity
 */

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
