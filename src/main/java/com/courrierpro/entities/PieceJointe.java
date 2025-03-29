package com.courrierpro.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pieces_jointes")
public class PieceJointe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomFichier;
    private String url;

    @Lob  // Stocker en base de données en tant que large object (LOB)
    private byte[] contenu;

    @ManyToOne
    @JoinColumn(name = "courrier_id")
    private Courrier courrier;
}
