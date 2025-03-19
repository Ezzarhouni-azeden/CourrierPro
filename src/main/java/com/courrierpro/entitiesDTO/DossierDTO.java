package com.courrierpro.entitiesDTO;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierDTO {
    private Long idDossier;
    private String nomDossier;
    private String description;
}
