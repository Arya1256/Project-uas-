package com.example.psku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.psku.model.Playstation;
import com.example.psku.model.RentalRequest;
import com.example.psku.service.PlaystationService;
import com.example.psku.service.RentalRequestService;

import java.time.LocalDateTime;

@Controller
public class HomeController {
    @Autowired
    private PlaystationService psService;
    @Autowired
    private RentalRequestService rentalService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("psList", psService.listAll());
        model.addAttribute("rentalRequest", new RentalRequest());
        return "home";
    }

    @PostMapping("/submit-rental")
    public String submitRental(@ModelAttribute RentalRequest request, @RequestParam String variant, Model model) {
        // Cari PS yang tersedia dengan varian yang dipilih user
        Playstation ps = psService.findAvailableByVariant(variant);
        if (ps == null) {
            // Tidak ada PS tersedia, tampilkan pesan error
            model.addAttribute("psList", psService.listAll());
            model.addAttribute("rentalRequest", new RentalRequest());
            model.addAttribute("error", "Maaf, tidak ada PS varian " + variant + " yang tersedia.");
            return "home";
        }
        request.setPsId(ps.getId());
        if ("opentime".equals(request.getTipeSewa())) {
            request.setDurasi(0);
            request.setWaktuMulai(LocalDateTime.now());
        }
        rentalService.saveRequest(request);
        return "redirect:/nota/" + request.getId();
    }

    @GetMapping("/nota/{id}")
    public String nota(@PathVariable Long id, Model model) {
        RentalRequest rental = rentalService.getById(id);
        Playstation ps = psService.getById(rental.getPsId());
        model.addAttribute("rental", rental);
        model.addAttribute("ps", ps);
        return "nota";
    }
}

