package com.courrierpro.controllers;

import com.courrierpro.entitiesDTO.DossierDTO;
import com.courrierpro.services.DossierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
@Tag(name = "Dossiers", description = "Gestion des dossiers")
public class DossierController {

    private final DossierService dossierService;

    public DossierController(DossierService dossierService) {
        this.dossierService = dossierService;
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau dossier")
    public ResponseEntity<DossierDTO> createDossier(@RequestBody DossierDTO dossierDTO) {
        return ResponseEntity.ok(dossierService.createDossier(dossierDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un dossier par son ID")
    public ResponseEntity<DossierDTO> getDossierById(@PathVariable Long id) {
        return ResponseEntity.ok(dossierService.getDossierById(id));
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les dossiers")
    public ResponseEntity<List<DossierDTO>> getAllDossiers() {
        return ResponseEntity.ok(dossierService.getAllDossiers());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un dossier")
    public ResponseEntity<DossierDTO> updateDossier(@PathVariable Long id, @RequestBody DossierDTO dossierDTO) {
        return ResponseEntity.ok(dossierService.updateDossier(id, dossierDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un dossier")
    public ResponseEntity<Void> deleteDossier(@PathVariable Long id) {
        dossierService.deleteDossier(id);
        return ResponseEntity.noContent().build();
    }
}
