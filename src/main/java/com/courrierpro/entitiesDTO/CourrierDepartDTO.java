package com.courrierpro.entitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierDepartDTO extends CourrierDTO {
    private String dateDepart;
    private boolean estReponse;

    public CourrierDepartDTO(Long idCourrier, String expediteur, String destination, String objet, String divers, 
                           boolean valide, Long chargeId, Long dossierId, List<PieceJointeDTO> piecesJointes,
                           String dateDepart, boolean estReponse) {
        super(idCourrier, expediteur, destination, objet, divers, valide, chargeId, dossierId, piecesJointes);
        this.dateDepart = dateDepart;
        this.estReponse = estReponse;
    }
}