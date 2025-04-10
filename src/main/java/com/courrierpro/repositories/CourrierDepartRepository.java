package com.courrierpro.repositories;

import com.courrierpro.entities.CourrierDepart;
import com.courrierpro.entitiesDTO.CourrierDepartDTO;
import org.springframework.data.repository.CrudRepository;

public interface CourrierDepartRepository extends CrudRepository<CourrierDepart, Long> {
}
