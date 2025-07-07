package com.projet.localed.services.users;

import com.projet.localed.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    User create(User user);
    void update(Long id, User user);
    void delete(Long id);
    void verifyEmail(String token);

}
