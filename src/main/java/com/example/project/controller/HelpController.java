package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.entity.Support;
import com.example.project.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:09 PM on 12/1/2022
 * @project Project
 */
@RestController
@RequestMapping("/help")
@RequiredArgsConstructor
public class HelpController {

    private final SupportService supportService;

    @PostMapping
    public ResponseEntity<?> contactUs(@RequestBody Support support){
        ApiResponse<?> response= supportService.contact(support);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.CONFLICT:HttpStatus.OK).body(response);
    }
}
