package com.example.psku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.psku.model.Playstation;
import com.example.psku.repository.PlaystationRepository;

@Service
public class PlaystationService {
    @Autowired
    private PlaystationRepository repository;

    public List<Playstation> listAll() {
        return repository.findAll();
    }

    public void updateAvailability(Long id, boolean status) {
        Playstation ps = repository.findById(id).orElseThrow();
        ps.setAvailable(status);
        repository.save(ps);
    }

    public Playstation getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void saveOrUpdate(Long id, String model, String variant, double hargaSewa, boolean available) {
        Playstation ps;
        if (id != null) {
            ps = repository.findById(id).orElse(new Playstation());
            ps.setId(id);
        } else {
            ps = new Playstation();
        }
        ps.setModel(model);
        ps.setVariant(variant);
        ps.setHargaSewa(hargaSewa);
        ps.setAvailable(available);
        repository.save(ps);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Playstation findAvailableByVariant(String variant) {
        return repository.findFirstByVariantAndAvailableTrue(variant);
    }
}

