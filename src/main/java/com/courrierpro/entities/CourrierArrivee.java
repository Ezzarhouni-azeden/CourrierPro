package com.courrierpro.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierArrivee extends Courrier {
    private String dateArrivee;
    private String dateLimiteSortie;
    private String statut;
}