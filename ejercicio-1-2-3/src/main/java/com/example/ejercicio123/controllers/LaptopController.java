package com.example.ejercicio123.controllers;

import com.example.ejercicio123.entities.Laptop;
import com.example.ejercicio123.repository.LaptopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {
    private final Logger log = LoggerFactory.getLogger(LaptopController.class);
    private LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @GetMapping("/api/laptops")
    public List<Laptop> findAll() {
        log.info("GET method - /api/laptops");
        return laptopRepository.findAll();
    }

    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id) {
        log.info("GET method - /api/laptops/"+id);
        Optional<Laptop> laptop = laptopRepository.findById(id);
        if (laptop.isPresent()) {
            return ResponseEntity.ok(laptop.get());
        } else {
            log.warn("Trying to read a non existent laptop");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/laptops")
    public Laptop create(@RequestBody Laptop laptop) {
        log.info("POST method - /api/laptops");
        return laptopRepository.save(laptop);
    }

    @PutMapping("/api/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop) {
        log.info("PUT method - /api/laptops");
        if (laptop.getId() == null) {
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.badRequest().build();
        }

        if (!laptopRepository.existsById(laptop.getId())) {
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.notFound().build();
        }

        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> deleteOneById(@PathVariable Long id) {
        log.info("DELETE method - /api/laptops/"+id);
        if (!laptopRepository.existsById(id)) {
            log.warn("Trying to delete a non existent laptop");
            return ResponseEntity.notFound().build();
        }

        laptopRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiIgnore
    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll() {
        log.info("DELETE method - /api/laptops");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
