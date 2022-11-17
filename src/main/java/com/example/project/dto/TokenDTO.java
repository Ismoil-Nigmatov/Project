package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 6:13 PM on 11/17/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TokenDTO {
    private String token;
    private String email;
}
