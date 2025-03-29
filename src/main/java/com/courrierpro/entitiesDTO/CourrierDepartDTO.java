package com.courrierpro.entitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierDepartDTO extends CourrierDTO {
    private String dateDepart;
    private boolean estReponse;
}