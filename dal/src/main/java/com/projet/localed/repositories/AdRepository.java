package com.projet.localed.repositories;

import com.projet.localed.entities.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long> {
}
