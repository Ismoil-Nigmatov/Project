package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 4:13 PM on 12/6/2022
 * @project Project
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfilePhotoDTO {
    @NotNull
    private String email;

    @NotNull
    private MultipartFile file;
}
