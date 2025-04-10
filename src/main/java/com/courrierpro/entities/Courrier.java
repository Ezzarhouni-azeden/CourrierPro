package com.courrierpro.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Utilisation de JOINED pour l'héritage
@Table(name ="courrier")
public class Courrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourrier;
    private String expediteur;
    private String destination;
    private String objet;
    private String divers;
    private boolean valide;

    @ManyToOne
    @JoinColumn(name = "charge_id")  // Clé étrangère pointant vers la table User
    private User charge;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private Dossier dossier;


    @OneToMany(mappedBy = "courrier", cascade = CascadeType.ALL)
    private List<PieceJointe> piecesJointes;


}