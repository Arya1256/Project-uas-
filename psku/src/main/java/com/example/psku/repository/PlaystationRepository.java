package com.example.psku.repository;

import com.example.psku.model.Playstation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaystationRepository extends JpaRepository<Playstation, Long> {
    Playstation findFirstByVariantAndAvailableTrue(String variant);
}
