package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:55 PM on 11/15/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    @NotNull
    private String fromLanguage;

    @NotNull
    private String targetLanguage;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phone;

    private MultipartFile[] files;
}
