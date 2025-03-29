package com.courrierpro.entitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierArriveeDTO extends CourrierDTO {
    private String dateArrivee;
    private String dateLimiteSortie;
    private String statut;
}