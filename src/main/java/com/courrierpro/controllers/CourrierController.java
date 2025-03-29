package com.courrierpro.controllers;

import com.courrierpro.entitiesDTO.*;
import com.courrierpro.services.CourrierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Gestion des Courriers", description = "API de gestion des différents types de courriers (Arrivée, Départ, Réponse)")
public class CourrierController {
    @Autowired
    private CourrierService courrierService;

    // Endpoints Courrier Arrivée
    @PostMapping("/arrivee")
    @Operation(
            summary = "Créer un nouveau courrier entrant",
            description = "Ajoute un nouveau courrier entrant dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier entrant créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<CourrierArriveeDTO> createCourrierArrivee(
            @Parameter(description = "Détails du courrier entrant à créer", required = true)
            @RequestBody CourrierArriveeDTO courrierArriveeDTO) {
        CourrierArriveeDTO createdCourrier = courrierService.createCourrierArrivee(courrierArriveeDTO);
        return ResponseEntity.ok(createdCourrier);
    }

    @PutMapping("/arrivee/{id}")
    @Operation(
            summary = "Mettre à jour un courrier entrant existant",
            description = "Modifie les détails d'un courrier entrant existant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier entrant mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier entrant non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides")
    })
    public ResponseEntity<CourrierArriveeDTO> updateCourrierArrivee(
            @Parameter(description = "Identifiant du courrier entrant à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouveaux détails du courrier entrant", required = true)
            @RequestBody CourrierArriveeDTO courrierArriveeDTO) {
        CourrierArriveeDTO updatedCourrier = courrierService.updateCourrierArrivee(id, courrierArriveeDTO);
        return ResponseEntity.ok(updatedCourrier);
    }

    @GetMapping("/arrivee")
    @Operation(
            summary = "Récupérer tous les courriers entrants",
            description = "Retourne la liste de tous les courriers entrants"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des courriers entrants récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<CourrierArriveeDTO>> getAllCourrierArrivee() {
        List<CourrierArriveeDTO> courriers = courrierService.getAllCourrierArrivee();
        return ResponseEntity.ok(courriers);
    }

    @GetMapping("/arrivee/{id}")
    @Operation(
            summary = "Récupérer un courrier entrant spécifique",
            description = "Retourne les détails d'un courrier entrant par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier entrant récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier entrant non trouvé")
    })
    public ResponseEntity<CourrierArriveeDTO> getCourrierArriveeById(
            @Parameter(description = "Identifiant du courrier entrant à récupérer", required = true)
            @PathVariable Long id) {
        CourrierArriveeDTO courrier = courrierService.getCourrierArriveeById(id);
        return ResponseEntity.ok(courrier);
    }

    @DeleteMapping("/arrivee/{id}")
    @Operation(
            summary = "Supprimer un courrier entrant",
            description = "Supprime un courrier entrant spécifique du système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Courrier entrant supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier entrant non trouvé")
    })
    public ResponseEntity<Void> deleteCourrierArrivee(
            @Parameter(description = "Identifiant du courrier entrant à supprimer", required = true)
            @PathVariable Long id) {
        courrierService.deleteCourrierArrivee(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints Courrier Départ
    @PostMapping("/depart")
    @Operation(
            summary = "Créer un nouveau courrier sortant",
            description = "Ajoute un nouveau courrier sortant dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier sortant créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<CourrierDepartDTO> createCourrierDepart(
            @Parameter(description = "Détails du courrier sortant à créer", required = true)
            @RequestBody CourrierDepartDTO courrierDepartDTO) {
        CourrierDepartDTO createdCourrier = courrierService.createCourrierDepart(courrierDepartDTO);
        return ResponseEntity.ok(createdCourrier);
    }

    @PutMapping("/depart/{id}")
    @Operation(
            summary = "Mettre à jour un courrier sortant existant",
            description = "Modifie les détails d'un courrier sortant existant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier sortant mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier sortant non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides")
    })
    public ResponseEntity<CourrierDepartDTO> updateCourrierDepart(
            @Parameter(description = "Identifiant du courrier sortant à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouveaux détails du courrier sortant", required = true)
            @RequestBody CourrierDepartDTO courrierDepartDTO) {
        CourrierDepartDTO updatedCourrier = courrierService.updateCourrierDepart(id, courrierDepartDTO);
        return ResponseEntity.ok(updatedCourrier);
    }

    @GetMapping("/depart")
    @Operation(
            summary = "Récupérer tous les courriers sortants",
            description = "Retourne la liste de tous les courriers sortants"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des courriers sortants récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<CourrierDepartDTO>> getAllCourrierDepart() {
        List<CourrierDepartDTO> courriers = courrierService.getAllCourrierDepart();
        return ResponseEntity.ok(courriers);
    }

    @GetMapping("/depart/{id}")
    @Operation(
            summary = "Récupérer un courrier sortant spécifique",
            description = "Retourne les détails d'un courrier sortant par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier sortant récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier sortant non trouvé")
    })
    public ResponseEntity<CourrierDepartDTO> getCourrierDepartById(
            @Parameter(description = "Identifiant du courrier sortant à récupérer", required = true)
            @PathVariable Long id) {
        CourrierDepartDTO courrier = courrierService.getCourrierDepartById(id);
        return ResponseEntity.ok(courrier);
    }

    @DeleteMapping("/depart/{id}")
    @Operation(
            summary = "Supprimer un courrier sortant",
            description = "Supprime un courrier sortant spécifique du système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Courrier sortant supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier sortant non trouvé")
    })
    public ResponseEntity<Void> deleteCourrierDepart(
            @Parameter(description = "Identifiant du courrier sortant à supprimer", required = true)
            @PathVariable Long id) {
        courrierService.deleteCourrierDepart(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints Courrier Réponse
    @PostMapping("/reponse")
    @Operation(
            summary = "Créer un nouveau courrier de réponse",
            description = "Ajoute un nouveau courrier de réponse dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier de réponse créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<CourrierReponseDTO> createCourrierReponse(
            @Parameter(description = "Détails du courrier de réponse à créer", required = true)
            @RequestBody CourrierReponseDTO courrierReponseDTO) {
        CourrierReponseDTO createdCourrier = courrierService.createCourrierReponse(courrierReponseDTO);
        return ResponseEntity.ok(createdCourrier);
    }

    @PutMapping("/reponse/{id}")
    @Operation(
            summary = "Mettre à jour un courrier de réponse existant",
            description = "Modifie les détails d'un courrier de réponse existant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier de réponse mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier de réponse non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides")
    })
    public ResponseEntity<CourrierReponseDTO> updateCourrierReponse(
            @Parameter(description = "Identifiant du courrier de réponse à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouveaux détails du courrier de réponse", required = true)
            @RequestBody CourrierReponseDTO courrierReponseDTO) {
        CourrierReponseDTO updatedCourrier = courrierService.updateCourrierReponse(id, courrierReponseDTO);
        return ResponseEntity.ok(updatedCourrier);
    }

    @GetMapping("/reponse")
    @Operation(
            summary = "Récupérer tous les courriers de réponse",
            description = "Retourne la liste de tous les courriers de réponse"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des courriers de réponse récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<CourrierReponseDTO>> getAllCourrierReponse() {
        List<CourrierReponseDTO> courriers = courrierService.getAllCourrierReponse();
        return ResponseEntity.ok(courriers);
    }

    @GetMapping("/reponse/{id}")
    @Operation(
            summary = "Récupérer un courrier de réponse spécifique",
            description = "Retourne les détails d'un courrier de réponse par son identifiant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courrier de réponse récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier de réponse non trouvé")
    })
    public ResponseEntity<CourrierReponseDTO> getCourrierReponseById(
            @Parameter(description = "Identifiant du courrier de réponse à récupérer", required = true)
            @PathVariable Long id) {
        CourrierReponseDTO courrier = courrierService.getCourrierReponseById(id);
        return ResponseEntity.ok(courrier);
    }

    @DeleteMapping("/reponse/{id}")
    @Operation(
            summary = "Supprimer un courrier de réponse",
            description = "Supprime un courrier de réponse spécifique du système"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Courrier de réponse supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Courrier de réponse non trouvé")
    })
    public ResponseEntity<Void> deleteCourrierReponse(
            @Parameter(description = "Identifiant du courrier de réponse à supprimer", required = true)
            @PathVariable Long id) {
        courrierService.deleteCourrierReponse(id);
        return ResponseEntity.noContent().build();
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
