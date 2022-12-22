package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.SupportDTO;
import com.example.project.service.SupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:09 PM on 12/1/2022
 * @project Project
 */

@RestController
@RequestMapping("/help")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"},maxAge = 4800,allowCredentials = "false")
public class HelpController {

    private final SupportService supportService;

    @PostMapping
    public ResponseEntity<?> contactUs(@RequestBody SupportDTO supportDTO){
        ApiResponse<?> response= supportService.contact(supportDTO);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }

    @PostMapping("/urgent/{email}")
    public ResponseEntity<?> urgent(@PathVariable String email){
        ApiResponse<?> response = supportService.urgent(email);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.CONFLICT).body(response);
    }
}
