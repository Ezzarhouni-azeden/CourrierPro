package com.courrierpro.services.Impl;

import com.courrierpro.entities.Dossier;
import com.courrierpro.entitiesDTO.DossierDTO;
import com.courrierpro.repositories.DossierRepository;
import com.courrierpro.services.DossierService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DossierServiceImpl implements DossierService {
    private final DossierRepository dossierRepository;
    private final ModelMapper modelMapper;

    public DossierServiceImpl(DossierRepository dossierRepository, ModelMapper modelMapper) {
        this.dossierRepository = dossierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DossierDTO createDossier(DossierDTO dossierDTO) {
        Dossier dossier = modelMapper.map(dossierDTO, Dossier.class);
        Dossier savedDossier = dossierRepository.save(dossier);
        return modelMapper.map(savedDossier, DossierDTO.class);
    }



    @Override
    public DossierDTO getDossierById(Long id) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé"));
        return modelMapper.map(dossier, DossierDTO.class);
    }

    @Override
    public List<DossierDTO> getAllDossiers() {
        List<Dossier> dossiers = dossierRepository.findAll();
        return dossiers.stream()
                .map(dossier -> modelMapper.map(dossier, DossierDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DossierDTO updateDossier(Long id, DossierDTO dossierDTO) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dossier non trouvé"));

        dossier.setNomDossier(dossierDTO.getNomDossier());
        dossier.setDescription(dossierDTO.getDescription());

        Dossier updatedDossier = dossierRepository.save(dossier);
        return modelMapper.map(updatedDossier, DossierDTO.class);
    }

    @Override
    public void deleteDossier(Long id) {
        dossierRepository.deleteById(id);
    }
}
