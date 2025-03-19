package com.courrierpro.repositories;

import com.courrierpro.entities.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierRepository extends JpaRepository<Dossier, Long> {}

