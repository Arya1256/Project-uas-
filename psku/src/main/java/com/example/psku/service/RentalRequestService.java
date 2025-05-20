package com.example.psku.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.psku.model.RentalRequest;
import com.example.psku.repository.RentalRequestRepository;
import com.example.psku.repository.PlaystationRepository;
import com.example.psku.model.Playstation;

@Service
public class RentalRequestService {
    @Autowired
    private RentalRequestRepository repository;

    @Autowired
    private PlaystationRepository playstationRepository;

    public List<RentalRequest> listAll() {
        return repository.findAll();
    }

    public void approveRequest(Long id) {
        RentalRequest request = repository.findById(id).orElseThrow();
        request.setApproved(true);
        repository.save(request);
    }

    public void saveRequest(RentalRequest request) {
        Playstation ps = playstationRepository.findById(request.getPsId()).orElse(null);
        if (ps != null) {
            int durasi = request.getDurasi() != null ? request.getDurasi() : 0;
            request.setTotalHarga(ps.getHargaSewa() * durasi);
        }
        repository.save(request);
    }

    public RentalRequest getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}

