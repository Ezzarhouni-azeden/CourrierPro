package com.courrierpro.entitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierReponseDTO extends CourrierDepartDTO {
    private Long idCourrierArrivee;
}