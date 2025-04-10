package com.courrierpro.entitiesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierReponseDTO extends CourrierDepartDTO {
    private Long idCourrierArrivee;
    private String dateReponse;

    public CourrierReponseDTO(Long idCourrier, String expediteur, String destination, String objet, String divers,
                              boolean valide, Long chargeId, Long dossierId, List<PieceJointeDTO> piecesJointes,
                              String dateDepart, boolean estReponse, Long idCourrierArrivee,String dateReponse) {
        super(idCourrier, expediteur, destination, objet, divers, valide, chargeId, dossierId, piecesJointes,
                dateDepart, estReponse);
        this.idCourrierArrivee = idCourrierArrivee;
        this.dateReponse = dateReponse;
    }
}