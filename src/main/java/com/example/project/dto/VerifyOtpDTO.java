package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 10:08 PM on 11/14/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerifyOtpDTO {
    @NotNull
    private String email;

    @NotNull
    private String otp;
}
