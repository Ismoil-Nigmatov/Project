package com.example.project.repository;

import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:27 PM on 10/6/2022
 * @project Project
 */
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
