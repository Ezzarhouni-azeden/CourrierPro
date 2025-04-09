package com.courrierpro.services;

import com.courrierpro.entitiesDTO.ReunionDTO;
import com.courrierpro.entities.Reunion;
import java.util.List;

public interface ReunionService {
    ReunionDTO planifierReunion(ReunionDTO reunionDTO);
    ReunionDTO obtenirReunionParId(Long id);
    List<ReunionDTO> obtenirToutesLesReunions();
    ReunionDTO mettreAJourReunion(Long id, ReunionDTO reunionDTO);
    void supprimerReunion(Long id);
}
