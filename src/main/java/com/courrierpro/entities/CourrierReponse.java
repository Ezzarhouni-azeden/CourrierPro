package com.courrierpro.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("REPONSE")
public class CourrierReponse extends CourrierDepart{
    private Long idCourrierArrivee;
    private String dateReponse;

}

