package com.projet.localed.mappers;

import com.projet.localed.entities.User;
import com.projet.localed.models.users.UserDTO;
import com.projet.localed.models.users.UserForm;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }

    public User fromForm(UserForm form) {
        User user = new User();
        user.setUsername(form.username());
        user.setEmail(form.email());
        user.setPassword(form.password());
        user.setFirstName(form.firstName());
        user.setLastName(form.lastName());
        user.setPhoneNumber(form.phoneNumber());
        user.setRole(form.role());
        user.setStatus(form.status());
        return user;
    }
}
