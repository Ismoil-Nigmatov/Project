package com.example.project.entity;

import com.example.project.entity.templete.AbsEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 11:51 PM on 10/31/2022
 * @project Project
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToOne(targetEntity = User.class,fetch = FetchType.EAGER)
    private User user;

    public ConfirmationToken(User user) {
        this.confirmationToken = UUID.randomUUID().toString();
        this.createdAt = new Date();
        this.user = user;
    }
}
