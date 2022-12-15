package com.example.project.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author "ISMOIL NIGMATOV"
 * @created 2:52 PM on 10/6/2022
 * @project Project
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SQLDelete(sql = "update users set enabled=false where id=?")
@Entity(name = "users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NotNull(message = "This field  is required")
    @Column(nullable = false)
    private String phoneNumber;
    @OneToOne
    private Role role;
    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled=true;

    private byte[] photo;

    private String photoContentType;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false,updatable = false)
    private Date createdAt=new Date();

    private String oneTimePassword;

    private Date otpRequestedTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
