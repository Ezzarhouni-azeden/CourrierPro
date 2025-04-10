package com.courrierpro.entitiesDTO;

import com.courrierpro.entities.Statut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourrierArriveeDTO extends CourrierDTO {
    private String dateArrivee;
    private String dateLimiteSortie;
    private String statut;

    public CourrierArriveeDTO(Long idCourrier, String expediteur, String destination, String objet, String divers, boolean valide, Long chargeId, Long dossierId, List<PieceJointeDTO> piecesJointes, String dateArrivee, String dateLimiteSortie, String statut) {
        super(idCourrier, expediteur, destination, objet, divers, valide, chargeId, dossierId, piecesJointes);
        this.dateArrivee = dateArrivee;
        this.dateLimiteSortie = dateLimiteSortie;
        this.statut = statut;
    }



}