package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:53 PM on 11/1/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordDto {
    @NotNull
    @Size(min = 8,message = "Minimum 8 elements!")
    private String password;
    @NotNull
    @Size(min = 8,message = "Minimum 8 elements!")
    private String confirmedPassword;
}
