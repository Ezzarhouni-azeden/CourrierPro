package com.courrierpro.services;

import com.courrierpro.entities.Courrier;
import com.courrierpro.repositories.CourrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourrierService {

    @Autowired
    private CourrierRepository courrierRepository;

    // Ajouter un courrier
    public Courrier ajouterCourrier(Courrier courrier) {
        return courrierRepository.save(courrier);
    }

    // Obtenir tous les courriers
    public List<Courrier> obtenirTousLesCourriers() {
        return courrierRepository.findAll();
    }

    // Obtenir un courrier par ID
    public Optional<Courrier> obtenirCourrierParId(Long id) {
        return courrierRepository.findById(id);
    }

    // Mettre à jour un courrier
    public Courrier mettreAJourCourrier(Long id, Courrier detailsCourrier) {
        return courrierRepository.findById(id).map(courrier -> {
            courrier.setReference(detailsCourrier.getReference());
            courrier.setExpediteur(detailsCourrier.getExpediteur());
            courrier.setDestinataire(detailsCourrier.getDestinataire());
            courrier.setDateEnvoi(detailsCourrier.getDateEnvoi());
            courrier.setStatut(detailsCourrier.getStatut());
            courrier.setDescription(detailsCourrier.getDescription());
            return courrierRepository.save(courrier);
        }).orElseThrow(() -> new RuntimeException("Courrier non trouvé"));
    }

    // Supprimer un courrier
    public void supprimerCourrier(Long id) {
        courrierRepository.deleteById(id);
    }
}
