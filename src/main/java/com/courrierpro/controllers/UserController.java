package com.courrierpro.controllers;

import com.courrierpro.entitiesDTO.UserDTO;
import com.courrierpro.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
@Tag(name = "Utilisateur", description = "API de gestion des utilisateurs")
public class UserController {

    @Autowired
    private UserService utilisateurService;

    @GetMapping
    @Operation(summary = "Récupérer tous les utilisateurs", description = "Retourne une liste de tous les utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)))
    })
    public List<UserDTO> getAllUsers() {
        return utilisateurService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un utilisateur par son ID", description = "Retourne un utilisateur spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UserDTO> getUtilisateurById(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable Integer id) {
        Optional<UserDTO> utilisateurDTO = utilisateurService.getUserById(id);
        return utilisateurDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Ajouter un nouvel utilisateur", description = "Crée un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<UserDTO> addUtilisateur(
            @Parameter(description = "Détails de l'utilisateur à créer", required = true)
            @RequestBody UserDTO utilisateurDTO) {
        UserDTO newUtilisateurDTO = utilisateurService.addUser(utilisateurDTO);
        return ResponseEntity.ok(newUtilisateurDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un utilisateur", description = "Met à jour les informations d'un utilisateur existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UserDTO> updateUtilisateur(
            @Parameter(description = "ID de l'utilisateur à mettre à jour", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Nouvelles informations de l'utilisateur", required = true)
            @RequestBody UserDTO utilisateurDTO) {
        UserDTO updatedUtilisateurDTO = utilisateurService.updateUser(id, utilisateurDTO);
        return updatedUtilisateurDTO != null ? ResponseEntity.ok(updatedUtilisateurDTO) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<String> deleteUtilisateur(
            @Parameter(description = "ID de l'utilisateur à supprimer", required = true)
            @PathVariable Integer id) {
        utilisateurService.deleteUser(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès !");
    }

    @PutMapping("/{id}/motdepasse")
    @Operation(summary = "Changer le mot de passe", description = "Change le mot de passe d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mot de passe changé avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<String> changePassword(
            @Parameter(description = "ID de l'utilisateur", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Nouveau mot de passe", required = true)
            @RequestBody String nouveauMotDePasse) {
        boolean isChanged = utilisateurService.changePassword(id, nouveauMotDePasse);
        if (isChanged) {
            return ResponseEntity.ok("Mot de passe changé avec succès");
        } else {
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        }
    }
}