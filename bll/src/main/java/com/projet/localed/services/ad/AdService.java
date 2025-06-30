package com.projet.localed.services.ad;

import com.projet.localed.entities.Ad;

import java.util.List;

public interface AdService {
    List<Ad> getAll();
    Ad getById(Long id);
    Ad create(Ad ad);
    void update(Long id, Ad ad);
    void delete(Long id);
}