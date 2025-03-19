package com.courrierpro.services;

import com.courrierpro.entities.Dossier;
import com.courrierpro.repositories.DossierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DossierService {
    @Autowired
    private DossierRepository dossierRepository;

    public List<Dossier> getAllDossiers() {
        return dossierRepository.findAll();
    }

    public Optional<Dossier> getDossierById(Long id) {
        return dossierRepository.findById(id);
    }

    public Dossier createDossier(Dossier dossier) {
        return dossierRepository.save(dossier);
    }

    public Dossier updateDossier(Long id, Dossier dossierDetails) {
        return dossierRepository.findById(id).map(dossier -> {
            dossier.setNomDossier(dossierDetails.getNomDossier());
            dossier.setDescription(dossierDetails.getDescription());
            return dossierRepository.save(dossier);
        }).orElseThrow(() -> new RuntimeException("Dossier non trouvé"));
    }

    public void deleteDossier(Long id) {
        dossierRepository.deleteById(id);
    }
}
