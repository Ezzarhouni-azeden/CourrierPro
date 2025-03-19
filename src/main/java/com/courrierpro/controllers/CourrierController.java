package com.courrierpro.controllers;

import com.courrierpro.entitiesDTO.CourrierDTO;
import com.courrierpro.entitiesDTO.PieceJointeDTO;
import com.courrierpro.services.CourrierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courriers")
@Tag(name = "Courriers", description = "API de gestion des courriers")  // Documentation Swagger
public class CourrierController {

    @Autowired
    private CourrierService courrierService;

    // Récupérer tous les courriers
    @GetMapping
    @Operation(summary = "Obtenir la liste des courriers", description = "Retourne tous les courriers enregistrés")
    public List<CourrierDTO> getAllCourriers() {
        return courrierService.getAllCourriers();
    }

    // Récupérer un courrier par son ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un courrier par ID", description = "Retourne les détails d'un courrier spécifique")
    public ResponseEntity<CourrierDTO> getCourrierById(@PathVariable Long id) {
        Optional<CourrierDTO> courrier = courrierService.getCourrierById(id);
        return courrier.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un courrier
    @PostMapping
    @PreAuthorize("hasAuthority('SECRETAIRE') or hasAuthority('CHEF_SERVICE')")
    @Operation(summary = "Créer un courrier", description = "Permet aux secrétaires et chefs de service de créer un nouveau courrier")
    public ResponseEntity<CourrierDTO> createCourrier(@RequestBody CourrierDTO courrierDTO) {
        return ResponseEntity.ok(courrierService.createCourrier(courrierDTO));
    }

    // Mettre à jour un courrier
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Operation(summary = "Mettre à jour un courrier", description = "Permet aux chefs de service de modifier un courrier existant")
    public ResponseEntity<CourrierDTO> updateCourrier(@PathVariable Long id, @RequestBody CourrierDTO courrierDTO) {
        return ResponseEntity.ok(courrierService.updateCourrier(id, courrierDTO));
    }

    // Supprimer un courrier
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Operation(summary = "Supprimer un courrier", description = "Permet aux chefs de service de supprimer un courrier")
    public ResponseEntity<Void> deleteCourrier(@PathVariable Long id) {
        courrierService.deleteCourrier(id);
        return ResponseEntity.noContent().build();
    }

    // Affecter un courrier à un utilisateur ayant le rôle CHARGE
    @PutMapping("/{id}/affecter/{userId}")
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Operation(summary = "Affecter un courrier à un CHARGE", description = "Attribue un courrier à un utilisateur ayant le rôle CHARGE")
    public ResponseEntity<CourrierDTO> affecterCourrier(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(courrierService.affecterCourrier(id, userId));
    }

    // Valider un courrier affecté à un CHARGE
    @PutMapping("/{id}/valider")
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Operation(summary = "Valider un courrier", description = "Permet aux chefs de service de valider un courrier après affectation")
    public ResponseEntity<CourrierDTO> validerCourrier(@PathVariable Long id) {
        return ResponseEntity.ok(courrierService.validerCourrier(id));
    }

    // Rejeter un courrier
    @PutMapping("/{id}/rejeter")
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Operation(summary = "Rejeter un courrier", description = "Permet aux chefs de service de rejeter un courrier")
    public ResponseEntity<CourrierDTO> rejeterCourrier(@PathVariable Long id) {
        return ResponseEntity.ok(courrierService.rejeterCourrier(id));
    }

    // 📌 Ajouter une pièce jointe à un courrier
    @PostMapping("/{courrierId}/piece-jointe")
    @Operation(summary = "Ajouter une pièce jointe à un courrier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pièce jointe ajoutée avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier non trouvé")
    })
    public ResponseEntity<PieceJointeDTO> ajouterPieceJointe(
            @PathVariable Long courrierId,
            @RequestParam("fichier") MultipartFile fichier) {
        try {
            PieceJointeDTO pieceJointe = courrierService.ajouterPieceJointe(courrierId, fichier);
            return ResponseEntity.ok(pieceJointe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 📌 Télécharger une pièce jointe
    @GetMapping("/piece-jointe/{id}")
    @Operation(summary = "Télécharger une pièce jointe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichier téléchargé avec succès"),
            @ApiResponse(responseCode = "404", description = "Fichier non trouvé")
    })
    public ResponseEntity<byte[]> telechargerPieceJointe(@PathVariable Long id) {
        byte[] fichier = courrierService.telechargerPieceJointe(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fichier.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(fichier);
    }
}
