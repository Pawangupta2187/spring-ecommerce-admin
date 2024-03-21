package com.admin.admin.security;


import com.admin.admin.entities.users.Admin;
import com.admin.admin.entities.users.Role;
import com.admin.admin.repository.AdminRepository;
import com.admin.admin.repository.RoleRepository;
import com.admin.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("new role ");
        if(roleRepository.count()==0)
        {
            System.out.println("new role generated");
            Role role1=new Role();
            Role role2=new Role();
            Role role3=new Role();
            role1.setAuthority("ROLE_CUSTOMER");
            role2.setAuthority("ROLE_ADMIN");
            role3.setAuthority("ROLE_SELLER");
            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);
        }
        if(adminRepository.count()<1){

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


            Admin admin = new Admin();
            admin.setFirstName("pawan");

            admin.setMiddleName("kk");
            admin.setLastName("Gupta");
           admin.setEmailId("pg445486@gmil");
            admin.setIsActive(true);
            admin.setIsDelete(false);
           //admin.setPassword("Pawan@1234");
            admin.setContactnumber("9718490833");

           admin.setPassword(passwordEncoder.encode("Pawan@1234"));

            Set<Role> roles=new HashSet<>();
//            System.out.println(">>>>"+roleRepository.findByAuthority("ROLE_ADMIN").get(1));
//         Role role =roleRepository.findByAuthority("ROLE_ADMIN").get(0);
//System.out.println(role);
        Role role=new Role();
        role.setAuthority("ROLE_ADMIN");
        roles.add(role);
        admin.setRoles(roles);
        adminRepository.save(admin);

        }
    }
}
