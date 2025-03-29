package com.courrierpro.entitiesDTO;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierDTO {
    private Long idCourrier;
    private String expediteur;
    private String destination;
    private String objet;
    private String divers;
    private boolean valide;
    private Long chargeId;
    private Long dossierId;
    private List<PieceJointeDTO> piecesJointes;
}