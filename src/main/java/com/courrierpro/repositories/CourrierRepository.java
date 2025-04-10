package com.courrierpro.repositories;

import com.courrierpro.entities.Courrier;
import com.courrierpro.entities.CourrierArrivee;
import com.courrierpro.entitiesDTO.CourrierDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourrierRepository extends JpaRepository<Courrier,Long> {
}
