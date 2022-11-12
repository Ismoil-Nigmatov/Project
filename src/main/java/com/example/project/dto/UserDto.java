package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 3:56 PM on 10/27/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @Size(min = 3,message = "At least 3 elements.")
    @Column(nullable = false)
    private String firstName;

    @Size(min = 3,message = "At least 3 elements.")
    @Column(nullable = false)
    private String lastName;

    @Email(message = "Email should be valid! ")
    @Column(nullable = false)
    private String email;

    @Size(min = 8,message = "Password should not be less than 8 ")
    @Column(nullable = false)
    private String password;

    @Transient
    private String secondPassword;

    @NotNull(message = "This field  is required")
    @Column(nullable = false)
    private String phoneNumber;

    private String companyName;


    private String captcha;

    private String hiddenCaptcha;

    private String realCaptcha;
}
