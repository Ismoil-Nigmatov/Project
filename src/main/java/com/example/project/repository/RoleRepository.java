package com.example.project.repository;

import com.example.project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 12:12 PM on 10/8/2022
 * @project Project
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
}
