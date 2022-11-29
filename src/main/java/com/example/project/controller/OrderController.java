package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.OrderDTO;
import com.example.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:48 PM on 11/15/2022
 * @project Project
 */

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"*"},maxAge = 4800,allowCredentials = "false")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody OrderDTO orderDTO){
            ApiResponse<?> response=orderService.save(orderDTO);
            return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getOrders(){
        ApiResponse<?> response=orderService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getOrder(@PathVariable String email){
        ApiResponse<?> response=orderService.getOne(email);
        return ResponseEntity.ok(response);
    }

}
