package com.courrierpro.services;

import com.courrierpro.entitiesDTO.CourrierDTO;
import com.courrierpro.entitiesDTO.PieceJointeDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CourrierService {
    List<CourrierDTO> getAllCourriers();
    Optional<CourrierDTO> getCourrierById(Long id);
    CourrierDTO createCourrier(CourrierDTO courrierDTO);
    CourrierDTO updateCourrier(Long id, CourrierDTO courrierDetails);
    void deleteCourrier(Long id);
    CourrierDTO affecterCourrier(Long id, Long userId);
    CourrierDTO validerCourrier(Long id);
    CourrierDTO rejeterCourrier(Long id);
    // Gestion des pièces jointes
    PieceJointeDTO ajouterPieceJointe(Long courrierId, MultipartFile fichier) throws IOException;
    byte[] telechargerPieceJointe(Long id);

}
