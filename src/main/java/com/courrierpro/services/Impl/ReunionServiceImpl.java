package com.courrierpro.services.Impl;

import com.courrierpro.services.ReunionService;
import com.courrierpro.entitiesDTO.ReunionDTO;
import com.courrierpro.entities.Reunion;
import com.courrierpro.repositories.ReunionRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ReunionServiceImpl implements ReunionService {
    private final ReunionRepository reunionRepository;

    public ReunionServiceImpl(ReunionRepository reunionRepository) {
        this.reunionRepository = reunionRepository;
    }

    @Override
    public ReunionDTO planifierReunion(ReunionDTO reunionDTO) {
        Reunion reunion = new Reunion();
        reunion.setSujet(reunionDTO.getSujet());
        reunion.setDate(reunionDTO.getDate());
        reunion.setHeure(reunionDTO.getHeure());
        reunion.setDescription(reunionDTO.getDescription());

        reunion = reunionRepository.save(reunion);
        return convertToDTO(reunion);
    }

    @Override
    public ReunionDTO obtenirReunionParId(Long id) {
        Reunion reunion = reunionRepository.findById(id).orElseThrow(() -> new RuntimeException("Réunion non trouvée"));
        return convertToDTO(reunion);
    }

    @Override
    public List<ReunionDTO> obtenirToutesLesReunions() {

        return StreamSupport.stream(reunionRepository.findAll().spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReunionDTO mettreAJourReunion(Long id, ReunionDTO reunionDTO) {
        Reunion reunion = reunionRepository.findById(id).orElseThrow(() -> new RuntimeException("Réunion non trouvée"));
        reunion.setSujet(reunionDTO.getSujet());
        reunion.setDate(reunionDTO.getDate());
        reunion.setHeure(reunionDTO.getHeure());
        reunion.setDescription(reunionDTO.getDescription());

        reunion = reunionRepository.save(reunion);
        return convertToDTO(reunion);
    }

    @Override
    public void supprimerReunion(Long id) {
        Reunion reunion = reunionRepository.findById(id).orElseThrow(() -> new RuntimeException("Réunion non trouvée"));
        reunionRepository.delete(reunion);
    }

    // Méthode de conversion Entité -> DTO
    private ReunionDTO convertToDTO(Reunion reunion) {
        ReunionDTO dto = new ReunionDTO();
        dto.setId(reunion.getId());
        dto.setSujet(reunion.getSujet());
        dto.setDate(reunion.getDate());
        dto.setHeure(reunion.getHeure());
        dto.setDescription(reunion.getDescription());
        return dto;
    }
}
