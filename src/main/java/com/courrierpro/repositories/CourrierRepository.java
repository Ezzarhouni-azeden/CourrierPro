package com.courrierpro.repositories;

import com.courrierpro.entities.Courrier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourrierRepository extends JpaRepository<Courrier,Long> {
}
