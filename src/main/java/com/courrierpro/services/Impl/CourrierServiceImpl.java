package com.courrierpro.services.Impl;

import com.courrierpro.entities.*;
import com.courrierpro.entitiesDTO.*;
import com.courrierpro.repositories.CourrierRepository;
import com.courrierpro.repositories.PieceJointeRepository;
import com.courrierpro.repositories.UserRepository;
import com.courrierpro.services.CourrierService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourrierServiceImpl implements CourrierService {

    private final CourrierRepository courrierRepository;
    private final UserRepository userRepository;
    private final PieceJointeRepository pieceJointeRepository;

    public CourrierServiceImpl(CourrierRepository courrierRepository, UserRepository userRepository, PieceJointeRepository pieceJointeRepository) {
        this.courrierRepository = courrierRepository;
        this.userRepository = userRepository;
        this.pieceJointeRepository = pieceJointeRepository;
    }

    // MÉTHODES POUR COURRIER ARRIVÉE
    @Override
    public CourrierArriveeDTO createCourrierArrivee(CourrierArriveeDTO courrierArriveeDTO) {
        return courrierArriveeDTO; // Implémentation manquante
    }

    @Override
    public CourrierArriveeDTO updateCourrierArrivee(Long id, CourrierArriveeDTO courrierArriveeDTO) {
        courrierArriveeDTO.setIdCourrier(id);
        return courrierArriveeDTO;
    }

    @Override
    public List<CourrierArriveeDTO> getAllCourrierArrivee() {
        return List.of(); // Retourne une liste vide pour éviter NullPointerException
    }

    @Override
    public CourrierArriveeDTO getCourrierArriveeById(Long id) {
        return null; // Implémentation manquante
    }

    @Override
    public void deleteCourrierArrivee(Long id) {
        courrierRepository.deleteById(id);
    }

    // MÉTHODES POUR COURRIER DÉPART
    @Override
    public CourrierDepartDTO createCourrierDepart(CourrierDepartDTO courrierDepartDTO) {
        return courrierDepartDTO;
    }

    @Override
    public CourrierDepartDTO updateCourrierDepart(Long id, CourrierDepartDTO courrierDepartDTO) {
        courrierDepartDTO.setIdCourrier(id);
        return courrierDepartDTO;
    }

    @Override
    public List<CourrierDepartDTO> getAllCourrierDepart() {
        return List.of();
    }

    @Override
    public CourrierDepartDTO getCourrierDepartById(Long id) {
        return null;
    }

    @Override
    public void deleteCourrierDepart(Long id) {
        courrierRepository.deleteById(id);
    }

    // MÉTHODES POUR COURRIER RÉPONSE
    @Override
    public CourrierReponseDTO createCourrierReponse(CourrierReponseDTO courrierReponseDTO) {
        return courrierReponseDTO;
    }

    @Override
    public CourrierReponseDTO updateCourrierReponse(Long id, CourrierReponseDTO courrierReponseDTO) {
        courrierReponseDTO.setIdCourrier(id);
        return courrierReponseDTO;
    }

    @Override
    public List<CourrierReponseDTO> getAllCourrierReponse() {
        return List.of();
    }

    @Override
    public CourrierReponseDTO getCourrierReponseById(Long id) {
        return null;
    }

    @Override
    public void deleteCourrierReponse(Long id) {
        courrierRepository.deleteById(id);
    }

    // AFFECTATION & VALIDATION DES COURRIERS
    @Override
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Transactional
    public CourrierDTO affecterCourrier(Long idCourrier, Long idUser) {
        Courrier courrier = courrierRepository.findById(idCourrier)
                .orElseThrow(() -> new EntityNotFoundException("Courrier non trouvé"));

        User user = userRepository.findById(idUser.intValue())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        if (!user.getRole().equals(Role.CHARGE)) {
            throw new IllegalArgumentException("L'utilisateur n'a pas le rôle CHARGE !");
        }

        courrier.setCharge(user);
        return convertToDTO(courrierRepository.save(courrier));
    }

    @Override
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Transactional
    public CourrierDTO validerCourrier(Long id) {
        Courrier courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courrier non trouvé"));

        if (courrier.getCharge() == null) {
            throw new IllegalStateException("Le courrier doit être affecté avant validation");
        }

        courrier.setValide(true);
        return convertToDTO(courrierRepository.save(courrier));
    }

    @Override
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Transactional
    public CourrierDTO rejeterCourrier(Long id) {
        Courrier courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courrier non trouvé"));

        courrier.setValide(false);
        return convertToDTO(courrierRepository.save(courrier));
    }

    // GESTION DES PIÈCES JOINTES
    @Override
    public PieceJointeDTO ajouterPieceJointe(Long courrierId, MultipartFile fichier) throws IOException {
        Courrier courrier = courrierRepository.findById(courrierId)
                .orElseThrow(() -> new EntityNotFoundException("Courrier non trouvé"));

        PieceJointe pieceJointe = new PieceJointe();
        pieceJointe.setNomFichier(fichier.getOriginalFilename());
        pieceJointe.setContenu(fichier.getBytes());
        pieceJointe.setCourrier(courrier);

        pieceJointe = pieceJointeRepository.save(pieceJointe);

        return new PieceJointeDTO(pieceJointe.getId(), pieceJointe.getNomFichier(),
                "https://localhost:8443/api/courriers/piece-jointe/" + pieceJointe.getId(), null, pieceJointe.getCourrier().getIdCourrier());
    }

    @Override
    public byte[] telechargerPieceJointe(Long id) {
        PieceJointe pieceJointe = pieceJointeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pièce jointe non trouvée"));
        return pieceJointe.getContenu();
    }

    // MÉTHODES UTILITAIRES POUR CONVERTIR EN DTO
    private CourrierDTO convertToDTO(Courrier courrier) {
        return new CourrierDTO(
                courrier.getIdCourrier(),
                courrier.getExpediteur(),
                courrier.getDestination(),
                courrier.getObjet(),
                courrier.getDivers(),
                courrier.isValide(),
                courrier.getCharge() != null ? courrier.getCharge().getId().longValue() : null,
                courrier.getDossier() != null ? courrier.getDossier().getIdDossier() : null,
                courrier.getPiecesJointes().stream().map(this::convertPieceJointeToDTO).collect(Collectors.toList())
        );
    }

    private PieceJointeDTO convertPieceJointeToDTO(PieceJointe pieceJointe) {
        return new PieceJointeDTO(pieceJointe.getId(), pieceJointe.getNomFichier(),
                "https://localhost:8443/api/courriers/piece-jointe/" + pieceJointe.getId(), null,
                pieceJointe.getCourrier().getIdCourrier());
    }
}
