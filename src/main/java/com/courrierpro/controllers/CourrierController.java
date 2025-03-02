package com.courrierpro.controllers;

import com.courrierpro.entities.Courrier;
import com.courrierpro.services.CourrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courriers")
@CrossOrigin("*") // Permet d'autoriser les requêtes depuis d'autres domaines
public class CourrierController {

    @Autowired
    private CourrierService courrierService;

    // Ajouter un courrier
    @PostMapping
    public Courrier ajouterCourrier(@RequestBody Courrier courrier) {
        return courrierService.ajouterCourrier(courrier);
    }

    // Obtenir tous les courriers
    @GetMapping
    public List<Courrier> obtenirTousLesCourriers() {
        return courrierService.obtenirTousLesCourriers();
    }

    // Obtenir un courrier par ID
    @GetMapping("/{id}")
    public Optional<Courrier> obtenirCourrierParId(@PathVariable Long id) {
        return courrierService.obtenirCourrierParId(id);
    }

    // Mettre à jour un courrier
    @PutMapping("/{id}")
    public Courrier mettreAJourCourrier(@PathVariable Long id, @RequestBody Courrier courrier) {
        return courrierService.mettreAJourCourrier(id, courrier);
    }

    // Supprimer un courrier
    @DeleteMapping("/{id}")
    public void supprimerCourrier(@PathVariable Long id) {
        courrierService.supprimerCourrier(id);
    }
}
