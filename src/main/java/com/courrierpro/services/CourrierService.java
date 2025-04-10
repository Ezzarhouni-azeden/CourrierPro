package com.courrierpro.services;

import com.courrierpro.entities.Statut;
import com.courrierpro.entitiesDTO.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourrierService {

    // Courrier Arrivée
    CourrierArriveeDTO createCourrierArrivee(CourrierArriveeDTO courrierArriveeDTO);
    CourrierArriveeDTO updateCourrierArrivee(Long id, CourrierArriveeDTO courrierArriveeDTO);
    List<CourrierArriveeDTO> getAllCourrierArrivee();
    CourrierArriveeDTO getCourrierArriveeById(Long id);
    void deleteCourrierArrivee(Long id);

    // Courrier Départ
    CourrierDepartDTO createCourrierDepart(CourrierDepartDTO courrierDepartDTO);
    CourrierDepartDTO updateCourrierDepart(Long id, CourrierDepartDTO courrierDepartDTO);
    List<CourrierDepartDTO> getAllCourrierDepart();
    CourrierDepartDTO getCourrierDepartById(Long id);
    void deleteCourrierDepart(Long id);

    // Courrier Réponse
    CourrierReponseDTO createCourrierReponse(CourrierReponseDTO courrierReponseDTO);
    CourrierReponseDTO updateCourrierReponse(Long id, CourrierReponseDTO courrierReponseDTO);
    List<CourrierReponseDTO> getAllCourrierReponse();
    CourrierReponseDTO getCourrierReponseById(Long id);
    void deleteCourrierReponse(Long id);


    //List<CourrierDTO> getAllCourriers();
    //Optional<CourrierDTO> getCourrierById(Long id);
    //CourrierDTO createCourrier(CourrierDTO courrierDTO);
    //CourrierDTO updateCourrier(Long id, CourrierDTO courrierDetails);
    //void deleteCourrier(Long id);
    CourrierDTO affecterCourrier(Long id, Long userId);
    CourrierDTO validerCourrier(Long id);
    CourrierDTO rejeterCourrier(Long id);
    // Méthodes pour les courriers arrivés
    CourrierDTO changerStatutCourrier(Long id, Statut nouveauStatut);


    // Gestion des pièces jointes
    PieceJointeDTO ajouterPieceJointe(Long courrierId, MultipartFile fichier) throws IOException;
    byte[] telechargerPieceJointe(Long id);

}
