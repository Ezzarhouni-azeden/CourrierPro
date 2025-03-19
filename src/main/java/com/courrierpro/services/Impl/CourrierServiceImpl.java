package com.courrierpro.services.Impl;

import com.courrierpro.entities.Courrier;
import com.courrierpro.entities.PieceJointe;
import com.courrierpro.entities.Role;
import com.courrierpro.entities.User;
import com.courrierpro.entitiesDTO.CourrierDTO;
import com.courrierpro.entitiesDTO.PieceJointeDTO;
import com.courrierpro.repositories.CourrierRepository;
import com.courrierpro.repositories.PieceJointeRepository;
import com.courrierpro.repositories.UserRepository;
import com.courrierpro.services.CourrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourrierServiceImpl implements CourrierService {

    @Autowired
    private CourrierRepository courrierRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PieceJointeRepository pieceJointeRepository;

    @Override
    public List<CourrierDTO> getAllCourriers() {
        return courrierRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CourrierDTO> getCourrierById(Long id) {
        return courrierRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    @Transactional
    public CourrierDTO createCourrier(CourrierDTO courrierDTO) {
        Courrier courrier = new Courrier();
        courrier.setExpediteur(courrierDTO.getExpediteur());
        courrier.setDestination(courrierDTO.getDestination());
        courrier.setObjet(courrierDTO.getObjet());
        courrier.setCharge(null);
        courrier.setValide(false);
        return convertToDTO(courrierRepository.save(courrier));
    }

    @Override
    @Transactional
    public CourrierDTO updateCourrier(Long id, CourrierDTO courrierDetails) {
        return courrierRepository.findById(id).map(courrier -> {
            courrier.setExpediteur(courrierDetails.getExpediteur());
            courrier.setDestination(courrierDetails.getDestination());
            courrier.setObjet(courrierDetails.getObjet());
            return convertToDTO(courrierRepository.save(courrier));
        }).orElseThrow(() -> new RuntimeException("Courrier non trouvé"));
    }

    @Override
    @Transactional
    public void deleteCourrier(Long id) {
        courrierRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Transactional
    public CourrierDTO affecterCourrier(Long idCourrier, Long idUser) {
        Courrier courrier = courrierRepository.findById(idCourrier)
                .orElseThrow(() -> new RuntimeException("Courrier non trouvé"));

        User user = userRepository.findById(Math.toIntExact(idUser))
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!user.getRole().equals(Role.CHARGE)) {
            throw new RuntimeException("L'utilisateur n'a pas le rôle CHARGE !");
        }

        courrier.setCharge(user);
        return convertToDTO(courrierRepository.save(courrier));
    }

    @Override
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Transactional
    public CourrierDTO validerCourrier(Long id) {
        return courrierRepository.findById(id).map(courrier -> {
            if (courrier.getCharge() == null) {
                throw new RuntimeException("Le courrier doit être affecté avant validation");
            }
            courrier.setValide(true);
            return convertToDTO(courrierRepository.save(courrier));
        }).orElseThrow(() -> new RuntimeException("Courrier non trouvé"));
    }

    @Override
    @PreAuthorize("hasAuthority('CHEF_SERVICE')")
    @Transactional
    public CourrierDTO rejeterCourrier(Long id) {
        return courrierRepository.findById(id).map(courrier -> {
            courrier.setValide(false);
            return convertToDTO(courrierRepository.save(courrier));
        }).orElseThrow(() -> new RuntimeException("Courrier non trouvé"));
    }

    private CourrierDTO convertToDTO(Courrier courrier) {
        return new CourrierDTO(
                courrier.getIdCourrier(),
                courrier.getExpediteur(),
                courrier.getDestination(),
                courrier.getObjet(),
                courrier.getCharge() != null ?
                        courrier.getCharge().getFirstname() + " " + courrier.getCharge().getLastname()
                        : null
        );
    }

    /**
     * Ajoute une pièce jointe à un courrier.
     */
    @Override
    public PieceJointeDTO ajouterPieceJointe(Long courrierId, MultipartFile fichier) throws IOException {
        Courrier courrier = courrierRepository.findById(courrierId)
                .orElseThrow(() -> new RuntimeException("Courrier non trouvé"));

        PieceJointe pieceJointe = new PieceJointe();
        pieceJointe.setNomFichier(fichier.getOriginalFilename());
        pieceJointe.setContenu(fichier.getBytes());
        pieceJointe.setCourrier(courrier);

        pieceJointeRepository.save(pieceJointe);

        String fileUrl = "https://localhost:8443/api/courriers/piece-jointe/" + pieceJointe.getId();
        return new PieceJointeDTO(pieceJointe.getId(), pieceJointe.getNomFichier(), fileUrl);
    }

    // Télécharger un fichier PDF
    @Override
    public byte[] telechargerPieceJointe(Long id) {
        PieceJointe pieceJointe = pieceJointeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pièce jointe non trouvée"));
        return pieceJointe.getContenu();
    }
}
