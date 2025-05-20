package com.example.psku.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Playstation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String variant; // PS3, PS4, PS5
    private boolean available;
    private double hargaSewa; // harga sewa per jam

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getVariant() { return variant; }
    public void setVariant(String variant) { this.variant = variant; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public double getHargaSewa() { return hargaSewa; }
    public void setHargaSewa(double hargaSewa) { this.hargaSewa = hargaSewa; }
}
