package com.projet.localed.controllers.ad;

import com.projet.localed.entities.Ad;
import com.projet.localed.entities.Category;
import com.projet.localed.entities.User;
import com.projet.localed.mappers.AdMapper;
import com.projet.localed.models.ad.AdDTO;
import com.projet.localed.models.ad.AdForm;
import com.projet.localed.repositories.CategoryRepository;
import com.projet.localed.repositories.UserRepository;
import com.projet.localed.services.ad.AdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;
    private final AdMapper adMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<AdDTO> getAll() {
        return adService.getAll().stream()
                .map(adMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public AdDTO getById(@PathVariable Long id) {
        return adMapper.toDTO(adService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid AdForm form) {
        User user = userRepository.findById(form.userId()).orElseThrow();
        Category category = form.categoryId() != null ? categoryRepository.findById(form.categoryId()).orElse(null) : null;
        Ad ad = adMapper.fromForm(form, user, category);
        adService.create(ad);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody @Valid AdForm form) {
        User user = userRepository.findById(form.userId()).orElseThrow();
        Category category = form.categoryId() != null ? categoryRepository.findById(form.categoryId()).orElse(null) : null;
        Ad ad = adMapper.fromForm(form, user, category);
        adService.update(id, ad);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        adService.delete(id);
    }
}
