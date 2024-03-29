package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:48 PM on 11/15/2022
 * @project Project
 */

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin(origins = {"*"},maxAge = 4800,allowCredentials = "false")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("{fromLanguage},{targetLanguage},{name},{email},{phone}")
    public ResponseEntity<?> saveOrder(MultipartFile[] files,@PathVariable String fromLanguage,@PathVariable String targetLanguage,@PathVariable String name,@PathVariable String email,@PathVariable String phone){
            ApiResponse<?> response=orderService.save(files,fromLanguage,targetLanguage,name,email,phone);
            return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getOrders(){
        ApiResponse<?> response = orderService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<?> getUserOrders(@RequestParam String email){
        ApiResponse<?> all = orderService.getAll(email);
        return ResponseEntity.ok(all);
    }
}
