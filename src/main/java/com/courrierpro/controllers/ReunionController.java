package com.courrierpro.controllers;

import com.courrierpro.entitiesDTO.ReunionDTO;
import com.courrierpro.services.ReunionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reunions")
@Tag(name = "Réunions", description = "API pour la gestion des réunions")
public class ReunionController {

    private final ReunionService reunionService;

    public ReunionController(ReunionService reunionService) {
        this.reunionService = reunionService;
    }

    @Operation(summary = "Planifier une réunion", description = "Ajoute une nouvelle réunion")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réunion planifiée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/planifier")
    public ResponseEntity<ReunionDTO> planifierReunion(@RequestBody ReunionDTO reunionDTO) {
        ReunionDTO nouvelleReunion = reunionService.planifierReunion(reunionDTO);
        return ResponseEntity.ok(nouvelleReunion);
    }

    @Operation(summary = "Obtenir une réunion par ID", description = "Retourne une réunion spécifique par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réunion trouvée"),
            @ApiResponse(responseCode = "404", description = "Réunion non trouvée")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReunionDTO> obtenirReunionParId(@PathVariable Long id) {
        ReunionDTO reunion = reunionService.obtenirReunionParId(id);
        return ResponseEntity.ok(reunion);
    }

    @Operation(summary = "Lister toutes les réunions", description = "Retourne la liste de toutes les réunions")
    @ApiResponse(responseCode = "200", description = "Liste des réunions récupérée")
    @GetMapping("/")
    public ResponseEntity<List<ReunionDTO>> obtenirToutesLesReunions() {
        return ResponseEntity.ok(reunionService.obtenirToutesLesReunions());
    }

    @Operation(summary = "Mettre à jour une réunion", description = "Modifie une réunion existante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réunion mise à jour"),
            @ApiResponse(responseCode = "404", description = "Réunion non trouvée")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReunionDTO> mettreAJourReunion(@PathVariable Long id, @RequestBody ReunionDTO reunionDTO) {
        return ResponseEntity.ok(reunionService.mettreAJourReunion(id, reunionDTO));
    }

    @Operation(summary = "Supprimer une réunion", description = "Supprime une réunion par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Réunion supprimée"),
            @ApiResponse(responseCode = "404", description = "Réunion non trouvée")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerReunion(@PathVariable Long id) {
        reunionService.supprimerReunion(id);
        return ResponseEntity.noContent().build();
    }
}
