package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.OrderDTO;
import com.example.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:48 PM on 11/15/2022
 * @project Project
 */

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestPart(required = false) List<MultipartFile> file, @RequestPart("data") OrderDTO orderDTO){
        ApiResponse response=orderService.save(file,orderDTO);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadPhoto(@PathVariable Long id) throws MalformedURLException {
       return orderService.download(id);
    }
}
