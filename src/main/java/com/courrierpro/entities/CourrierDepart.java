package com.courrierpro.entities;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierDepart extends Courrier {
    private String dateDepart;
    private boolean estReponse;
}