package com.example.project.repository;

import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:27 PM on 10/6/2022
 * @project Project
 */
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);

    @Query("select u from users u where u.email=?1")
    Optional<User> findByEmail(String email);

}
