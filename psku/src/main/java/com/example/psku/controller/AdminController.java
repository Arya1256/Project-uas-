package com.example.psku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.psku.service.PlaystationService;
import com.example.psku.service.RentalRequestService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PlaystationService psService;
    @Autowired
    private RentalRequestService rentalService;

    private Map<String, String> users = new ConcurrentHashMap<>();

    // Inisialisasi user default admin
    {
        users.put("admin", "admin");
    }

    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/admin/login?error";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("psList", psService.listAll());
        model.addAttribute("requests", rentalService.listAll());
        return "admin-dashboard";
    }

    @PostMapping("/update-ps-status")
    public String updatePS(@RequestParam Long psId, @RequestParam boolean status) {
        psService.updateAvailability(psId, status);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/approve-request")
    public String approve(@RequestParam Long requestId) {
        rentalService.approveRequest(requestId);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit-ps")
    public String editPS(@RequestParam Long id, Model model) {
        model.addAttribute("psEdit", psService.getById(id));
        model.addAttribute("psList", psService.listAll());
        model.addAttribute("requests", rentalService.listAll());
        return "admin-dashboard";
    }

    @PostMapping("/save-ps")
    public String savePS(@RequestParam(required = false) Long id,
                         @RequestParam String model,
                         @RequestParam String variant,
                         @RequestParam double hargaSewa,
                         @RequestParam boolean available) {
        psService.saveOrUpdate(id, model, variant, hargaSewa, available);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete-ps")
    public String deletePS(@RequestParam Long id) {
        psService.delete(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "admin-register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        users.put(username, password);
        return "redirect:/admin/login";
    }
}


