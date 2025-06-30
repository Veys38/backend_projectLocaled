package com.projet.localed.services.ad.impls;

import com.projet.localed.entities.Ad;
import com.projet.localed.exceptions.ad.AdNotFoundException;
import com.projet.localed.repositories.AdRepository;
import com.projet.localed.services.ad.AdService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;

    @Override
    public List<Ad> getAll() {
        return adRepository.findAll();
    }

    @Override
    public Ad getById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(AdNotFoundException::new);
    }

    @Override
    public Ad create(Ad ad) {
        return adRepository.save(ad);
    }

    @Override
    public void update(Long id, Ad ad) {
        Ad existing = getById(id);
        existing.setTitle(ad.getTitle());
        existing.setDescription(ad.getDescription());
        existing.setPrice(ad.getPrice());
        existing.setExpirationDate(ad.getExpirationDate());
        existing.setAvailable(ad.isAvailable());
        existing.setHighlighted(ad.isHighlighted());
        existing.setHighlightStartDate(ad.getHighlightStartDate());
        existing.setHighlightEndDate(ad.getHighlightEndDate());
        existing.setCategory(ad.getCategory());
        adRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        adRepository.deleteById(id);
    }
}
