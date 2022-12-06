package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @OneToMany(fetch = FetchType.EAGER)
    private List<AttachmentContent> attachmentContent;

    @Column(nullable = false,updatable = false)
    private LocalDate date=LocalDate.now();

    @Column(nullable = false,updatable = false)
    private LocalTime time=LocalTime.now();
}
