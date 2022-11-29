package com.example.project.repository;

import com.example.project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:49 PM on 11/15/2022
 * @project Project
 */
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByEmail(String email);
}
