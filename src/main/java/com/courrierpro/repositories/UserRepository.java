package com.courrierpro.repositories;

import com.courrierpro.entities.User;
import com.courrierpro.entitiesDTO.UtilisateurDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);}
