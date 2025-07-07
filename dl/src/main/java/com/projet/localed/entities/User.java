package com.projet.localed.entities;

import com.projet.localed.BaseEntity;
import com.projet.localed.enums.UserRole;
import com.projet.localed.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<Long> implements UserDetails {

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    private String phoneNumber;

    @Embedded
    private Address address;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(unique = true)
    private String confirmationToken;

    private LocalDateTime confirmationTokenCreatedAt;

    @Column(nullable = false)
    private boolean enabled = false;

    // Les relations
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image profilPicture;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> role.name());
    }


    @Override
    public String getUsername() {
        return email; // ou username si tu veux te connecter avec le username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }

}