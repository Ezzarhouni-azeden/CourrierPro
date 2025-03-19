package com.courrierpro.entitiesDTO;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierDTO {
    private Long idCourrier;
    private String expediteur;
    private String destination;
    private String objet;
    private String responsable;
}