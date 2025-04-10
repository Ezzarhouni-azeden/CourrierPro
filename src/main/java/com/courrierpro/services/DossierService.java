package com.courrierpro.services;

import com.courrierpro.entitiesDTO.DossierDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DossierService {
    DossierDTO createDossier(DossierDTO dossierDTO);
    DossierDTO getDossierById(Long id);
    List<DossierDTO> getAllDossiers();
    DossierDTO updateDossier(Long id, DossierDTO dossierDTO);
    void deleteDossier(Long id);
}
