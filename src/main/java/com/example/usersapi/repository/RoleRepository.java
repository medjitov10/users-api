package com.example.usersapi.repository;

import com.example.usersapi.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * RoleRepository interface extends CrudRepository to perform CRUD operations on Role entity
 */

public interface RoleRepository extends CrudRepository<Role, Long> {
}
