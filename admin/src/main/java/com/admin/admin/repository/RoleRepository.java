package com.admin.admin.repository;

import com.admin.admin.entities.users.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {

    List<Role> findByAuthority(String authority);
}
