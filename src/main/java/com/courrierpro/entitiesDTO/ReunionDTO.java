package com.courrierpro.entitiesDTO;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReunionDTO {
    private Long id;
    private String sujet;
    private LocalDate date;
    private LocalTime heure;
    private String description;
}
