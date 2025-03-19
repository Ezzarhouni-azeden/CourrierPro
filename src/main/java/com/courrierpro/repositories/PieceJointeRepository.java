package com.courrierpro.repositories;

import com.courrierpro.entities.PieceJointe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceJointeRepository extends JpaRepository<PieceJointe, Long> {
}
