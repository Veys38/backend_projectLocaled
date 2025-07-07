package com.projet.localed.controllers.users;

import com.projet.localed.entities.User;
import com.projet.localed.mappers.UserMapper;
import com.projet.localed.models.users.UserDTO;
import com.projet.localed.models.users.UserForm;
import com.projet.localed.services.users.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAll() {
        return userService.getAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UserDTO getById(@PathVariable Long id) {
        return userMapper.toDTO(userService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserForm form) {
        User user = userMapper.fromForm(form);
        userService.create(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody @Valid UserForm form) {
        User user = userMapper.fromForm(form);
        userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }


}
