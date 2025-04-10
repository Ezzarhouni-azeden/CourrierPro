package com.courrierpro.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("ARRIVEE")
public class CourrierArrivee extends Courrier {
    private String dateArrivee;
    private String dateLimiteSortie;
    @Enumerated(EnumType.STRING)
    private Statut statut;
}