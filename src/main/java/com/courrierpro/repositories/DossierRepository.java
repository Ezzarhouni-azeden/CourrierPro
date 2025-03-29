package com.courrierpro.repositories;

import com.courrierpro.entities.Dossier;
import com.courrierpro.entitiesDTO.DossierDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepository extends JpaRepository<Dossier, Long> {}

