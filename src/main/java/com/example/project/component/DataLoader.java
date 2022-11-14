package com.example.project.component;

import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 12:15 PM on 10/8/2022
 * @project Project
 */

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Value("${spring.sql.init.mode}")
    String mode;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args){
        if (mode.equals("always")){
            Role admin = roleRepository.save(new Role(1L,"ADMIN",true));
            roleRepository.save(new Role(2L,"USER",true));

            userRepository.save(new User(1L,"Ismoil","Nigmatov","ismoilnigmatov98@gmail.com",passwordEncoder.encode("ismoil180"),"+998950585566","Napa",admin,true,true,true,true,new Timestamp(System.currentTimeMillis()),null,null));
        }
    }
}
