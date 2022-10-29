package com.example.project.entity;

import com.example.project.entity.templete.AbsEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
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
@Where(clause = "deleted=false")
@SQLDelete(sql = "update users set deleted=true,status=false where id=?")
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

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
    @OneToOne
    private Role role;
    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled=true;

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
