package com.example.usersapi.repository;

import com.example.usersapi.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
