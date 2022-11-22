package com.example.project.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:38 PM on 11/15/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String fromLanguage;

    private String targetLanguage;

    @OneToMany
    private List<AttachmentContent> attachmentContent;
}
