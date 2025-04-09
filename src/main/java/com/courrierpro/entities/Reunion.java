package com.courrierpro.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reunion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Le sujet de la réunion
    private String sujet;

    // Date de la réunion
    private LocalDate date;

    // Heure de la réunion
    private LocalTime heure;



    // Description ou détails de la réunion
    @Lob
    private String description;
}
