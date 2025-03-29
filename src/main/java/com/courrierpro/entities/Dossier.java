package com.courrierpro.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDossier;
    private String nomDossier;
    private String description;
    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private List<Courrier> courriers;
}