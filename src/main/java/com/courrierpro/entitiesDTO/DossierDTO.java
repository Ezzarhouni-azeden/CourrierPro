package com.courrierpro.entitiesDTO;

import com.courrierpro.entities.Courrier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierDTO {
    private Long idDossier;
    private String nomDossier;
    private String description;
    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL)
    private List<Courrier> courriers;
}
