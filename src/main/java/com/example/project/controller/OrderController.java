package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.OrderDTO;
import com.example.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:48 PM on 11/15/2022
 * @project Project
 */

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"*"},maxAge = 4800,allowCredentials = "false")public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestPart(required = false) List<MultipartFile> file, @RequestPart("data") OrderDTO orderDTO){
        try {
            ApiResponse response=orderService.save(file,orderDTO);
            return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);
        }catch (Exception e){
            log.error(String.valueOf(e));
        }
        return ResponseEntity.badRequest().body("Error ");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadMedia(@PathVariable Long id){
       return orderService.download(id);
    }
}
