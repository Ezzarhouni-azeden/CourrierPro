package com.courrierpro.repositories;

import com.courrierpro.entities.Courrier;
import com.courrierpro.entitiesDTO.CourrierDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourrierRepository extends JpaRepository<Courrier,Long> {
}
