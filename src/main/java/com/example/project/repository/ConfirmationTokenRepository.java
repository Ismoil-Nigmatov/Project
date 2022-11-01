package com.example.project.repository;

import com.example.project.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:09 PM on 11/1/2022
 * @project Project
 */
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    Optional<ConfirmationToken> findByConfirmationToken(String token);

}
