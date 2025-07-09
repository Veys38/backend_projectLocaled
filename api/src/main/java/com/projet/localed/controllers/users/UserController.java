package com.projet.localed.controllers.users;

import com.projet.localed.entities.User;
import com.projet.localed.mappers.UserMapper;
import com.projet.localed.models.users.UserDTO;
import com.projet.localed.models.users.UserForm;
import com.projet.localed.services.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Lister tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste retournée avec succès")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAll() {
        return userService.getAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Operation(summary = "Obtenir un utilisateur par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UserDTO getById(@PathVariable Long id) {
        return userMapper.toDTO(userService.getById(id));
    }

    @Operation(summary = "Créer un nouvel utilisateur")
    @ApiResponse(responseCode = "201", description = "Utilisateur créé")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserForm form) {
        User user = userMapper.fromForm(form);
        userService.create(user);
    }

    @Operation(summary = "Mettre à jour un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mise à jour réussie"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody @Valid UserForm form) {
        User user = userMapper.fromForm(form);
        userService.update(id, user);
    }

    @Operation(summary = "Supprimer un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Suppression réussie"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }


}
