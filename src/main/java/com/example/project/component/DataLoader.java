package com.example.project.component;

import com.example.project.entity.Role;
import com.example.project.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")){
            roleRepository.save(new Role(1L, false, true, "ADMIN"));
            roleRepository.save(new Role(2L,false,true,"USER"));
        }
    }
}
