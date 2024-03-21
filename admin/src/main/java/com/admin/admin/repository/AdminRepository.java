package com.admin.admin.repository;

import com.admin.admin.entities.users.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin,Long> {

    //Admin findByName(String name);
//    List<Admin> findAdminByemailId(String email);
}
