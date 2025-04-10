package com.courrierpro.services.Impl;

import com.courrierpro.entities.*;
import com.courrierpro.entitiesDTO.*;
import com.courrierpro.repositories.*;
import com.courrierpro.services.CourrierService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourrierServiceImpl implements CourrierService {

    private final CourrierRepository courrierRepository;
    private final UserRepository userRepository;
    private final PieceJointeRepository pieceJointeRepository;
    private final CourrierArriveeRepository courrierArriveeRepository;
    private final CourrierDepartRepository courrierDepartRepository;
    private final CourrierReponseRepository courrierReponseRepository;

    public CourrierServiceImpl(CourrierRepository courrierRepository, 
                             UserRepository userRepository, 
                             PieceJointeRepository pieceJointeRepository,
                             CourrierArriveeRepository courrierArriveeRepository,
                             CourrierDepartRepository courrierDepartRepository,
                             CourrierReponseRepository courrierReponseRepository) {
        this.courrierRepository = courrierRepository;
        this.userRepository = userRepository;
        this.pieceJointeRepository = pieceJointeRepository;
        this.courrierArriveeRepository = courrierArriveeRepository;
        this.courrierDepartRepository = courrierDepartRepository;
        this.courrierReponseRepository = courrierReponseRepository;
    }

    // MÉTHODES POUR COURRIER ARRIVÉE
    @Override
    @Transactional
    public CourrierArriveeDTO createCourrierArrivee(CourrierArriveeDTO courrierArriveeDTO) {
        CourrierArrivee courrierArrivee = new CourrierArrivee();
        mapCourrierArriveeDTOToEntity(courrierArriveeDTO, courrierArrivee);
        courrierArrivee = courrierArriveeRepository.save(courrierArrivee);
        return mapCourrierArriveeEntityToDTO(courrierArrivee);
    }

    @Override
    @Transactional
    public CourrierArriveeDTO updateCourrierArrivee(Long id, CourrierArriveeDTO courrierArriveeDTO) {
        CourrierArrivee courrierArrivee = courrierArriveeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courrier arrivée non trouvé"));
        
        mapCourrierArriveeDTOToEntity(courrierArriveeDTO, courrierArrivee);
        courrierArrivee = courrierArriveeRepository.save(courrierArrivee);
        return mapCourrierArriveeEntityToDTO(courrierArrivee);
    }

    @Override
    public List<CourrierArriveeDTO> getAllCourrierArrivee() {
        List<CourrierArrivee> courriers = (List<CourrierArrivee>) courrierArriveeRepository.findAll();
        return courriers != null ? 
                courriers.stream()
                        .map(this::mapCourrierArriveeEntityToDTO)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }

    @Override
    public CourrierArriveeDTO getCourrierArriveeById(Long id) {
        return courrierArriveeRepository.findById(id)
                .map(this::mapCourrierArriveeEntityToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Courrier arrivée non trouvé"));
    }

    @Override
    @Transactional
    public void deleteCourrierArrivee(Long id) {
        if (!courrierArriveeRepository.existsById(id)) {
            throw new EntityNotFoundException("Courrier arrivée non trouvé");
        }
        courrierArriveeRepository.deleteById(id);
    }

    // MÉTHODES POUR COURRIER DÉPART
    @Override
    @Transactional
    public CourrierDepartDTO createCourrierDepart(CourrierDepartDTO courrierDepartDTO) {
        CourrierDepart courrierDepart = new CourrierDepart();
        mapCourrierDepartDTOToEntity(courrierDepartDTO, courrierDepart);
        courrierDepart = courrierDepartRepository.save(courrierDepart);
        return mapCourrierDepartEntityToDTO(courrierDepart);
    }

    @Override
    @Transactional
    public CourrierDepartDTO updateCourrierDepart(Long id, CourrierDepartDTO courrierDepartDTO) {
        CourrierDepart courrierDepart = courrierDepartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courrier départ non trouvé"));
        
        mapCourrierDepartDTOToEntity(courrierDepartDTO, courrierDepart);
        courrierDepart = courrierDepartRepository.save(courrierDepart);
        return mapCourrierDepartEntityToDTO(courrierDepart);
    }

    @Override
    public List<CourrierDepartDTO> getAllCourrierDepart() {
        List<CourrierDepart> courriers = (List<CourrierDepart>) courrierDepartRepository.findAll();
        return courriers != null ?
                courriers.stream()
                        .map(this::mapCourrierDepartEntityToDTO)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }

    @Override
    public CourrierDepartDTO getCourrierDepartById(Long id) {
        return courrierDepartRepository.findById(id)
                .map(this::mapCourrierDepartEntityToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Courrier départ non trouvé"));
    }

    @Override
    @Transactional
    public void deleteCourrierDepart(Long id) {
        if (!courrierDepartRepository.existsById(id)) {
            throw new EntityNotFoundException("Courrier départ non trouvé");
        }
        courrierDepartRepository.deleteById(id);
    }

    // MÉTHODES POUR COURRIER RÉPONSE
    @Override
    @Transactional
    public CourrierReponseDTO createCourrierReponse(CourrierReponseDTO courrierReponseDTO) {
        CourrierReponse courrierReponse = new CourrierReponse();
        mapCourrierReponseDTOToEntity(courrierReponseDTO, courrierReponse);
        courrierReponse = courrierReponseRepository.save(courrierReponse);
        return mapCourrierReponseEntityToDTO(courrierReponse);
    }

    @Override
    @Transactional
    public CourrierReponseDTO updateCourrierReponse(Long id, CourrierReponseDTO courrierReponseDTO) {
        CourrierReponse courrierReponse = courrierReponseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courrier réponse non trouvé"));
        
        mapCourrierReponseDTOToEntity(courrierReponseDTO, courrierReponse);
        courrierReponse = courrierReponseRepository.save(courrierReponse);
        return mapCourrierReponseEntityToDTO(courrierReponse);
    }

    @Override
    public List<CourrierReponseDTO> getAllCourrierReponse() {
        List<CourrierReponse> courriers = (List<CourrierReponse>) courrierReponseRepository.findAll();
        return courriers != null ?
                courriers.stream()
                        .map(this::mapCourrierReponseEntityToDTO)
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }

    @Override
    public CourrierReponseDTO getCourrierReponseById(Long id) {
        return courrierReponseRepository.findById(id)
                .map(this::mapCourrierReponseEntityToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Courrier réponse non trouvé"));
    }

    @Override
    @Transactional
    public void deleteCourrierReponse(Long id) {
        if (!courrierReponseRepository.existsById(id)) {
            throw new EntityNotFoundException("Courrier réponse non trouvé");
        }
        courrierReponseRepository.deleteById(id);
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

    @Override
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Transactional
    public CourrierDTO changerStatutCourrier(Long id, Statut nouveauStatut) {
        Courrier courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Courrier non trouvé"));

        if (courrier instanceof CourrierArrivee) {
            ((CourrierArrivee) courrier).setStatut(nouveauStatut);
        } else {
            throw new IllegalArgumentException("Ce courrier ne peut pas avoir de statut modifié");
        }

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

    // MÉTHODES UTILITAIRES POUR LA CONVERSION ENTRE ENTITÉS ET DTO
    private void mapCourrierArriveeDTOToEntity(CourrierArriveeDTO dto, CourrierArrivee entity) {
        entity.setIdCourrier(dto.getIdCourrier());
        entity.setExpediteur(dto.getExpediteur());
        entity.setDestination(dto.getDestination());
        entity.setObjet(dto.getObjet());
        entity.setDivers(dto.getDivers());
        entity.setValide(dto.isValide());
        entity.setDateArrivee(dto.getDateArrivee());
        entity.setDateLimiteSortie(dto.getDateLimiteSortie());
        entity.setStatut(Statut.valueOf(dto.getStatut()));
        
        if (dto.getChargeId() != null) {
            User charge = userRepository.findById(dto.getChargeId().intValue())
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
            entity.setCharge(charge);
        }
    }

    private CourrierArriveeDTO mapCourrierArriveeEntityToDTO(CourrierArrivee entity) {
        List<PieceJointeDTO> piecesJointesDTO = entity.getPiecesJointes() != null ?
                entity.getPiecesJointes().stream()
                        .map(this::convertPieceJointeToDTO)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        return new CourrierArriveeDTO(
                entity.getIdCourrier(),
                entity.getExpediteur(),
                entity.getDestination(),
                entity.getObjet(),
                entity.getDivers(),
                entity.isValide(),
                entity.getCharge() != null ? entity.getCharge().getId().longValue() : null,
                entity.getDossier() != null ? entity.getDossier().getIdDossier() : null,
                piecesJointesDTO,
                entity.getDateArrivee(),
                entity.getDateLimiteSortie(),
                entity.getStatut() != null ? entity.getStatut().name() : null
        );
    }

    private void mapCourrierDepartDTOToEntity(CourrierDepartDTO dto, CourrierDepart entity) {
        entity.setIdCourrier(dto.getIdCourrier());
        entity.setExpediteur(dto.getExpediteur());
        entity.setDestination(dto.getDestination());
        entity.setObjet(dto.getObjet());
        entity.setDivers(dto.getDivers());
        entity.setValide(dto.isValide());
        entity.setDateDepart(dto.getDateDepart());
        entity.setEstReponse(dto.isEstReponse());
        
        if (dto.getChargeId() != null) {
            User charge = userRepository.findById(dto.getChargeId().intValue())
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
            entity.setCharge(charge);
        }
    }

    private CourrierDepartDTO mapCourrierDepartEntityToDTO(CourrierDepart entity) {
        List<PieceJointeDTO> piecesJointesDTO = entity.getPiecesJointes() != null ?
                entity.getPiecesJointes().stream()
                        .map(this::convertPieceJointeToDTO)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        return new CourrierDepartDTO(
                entity.getIdCourrier(),
                entity.getExpediteur(),
                entity.getDestination(),
                entity.getObjet(),
                entity.getDivers(),
                entity.isValide(),
                entity.getCharge() != null ? entity.getCharge().getId().longValue() : null,
                entity.getDossier() != null ? entity.getDossier().getIdDossier() : null,
                piecesJointesDTO,
                entity.getDateDepart(),
                entity.isEstReponse()
        );
    }

    private void mapCourrierReponseDTOToEntity(CourrierReponseDTO dto, CourrierReponse entity) {
        mapCourrierDepartDTOToEntity(dto, entity);
        entity.setIdCourrierArrivee(dto.getIdCourrierArrivee());
    }

    private CourrierReponseDTO mapCourrierReponseEntityToDTO(CourrierReponse entity) {
        CourrierDepartDTO departDTO = mapCourrierDepartEntityToDTO(entity);
        return new CourrierReponseDTO(
                departDTO.getIdCourrier(),
                departDTO.getExpediteur(),
                departDTO.getDestination(),
                departDTO.getObjet(),
                departDTO.getDivers(),
                departDTO.isValide(),
                departDTO.getChargeId(),
                departDTO.getDossierId(),
                departDTO.getPiecesJointes(),
                departDTO.getDateDepart(),
                departDTO.isEstReponse(),
                entity.getIdCourrierArrivee(),
                entity.getDateReponse()
        );
    }
}
