package br.com.liquentec.AgenteAchaPet.dto.request;

import java.time.LocalDateTime;

import br.com.liquentec.AgenteAchaPet.model.SearchRole;
import br.com.liquentec.AgenteAchaPet.model.SpecialNeed;
import lombok.Data;

@Data
public class PetSearchRequestForm {
    private Long petId;
    private Long personId;
    private SearchRole reporterRole;
    private LocalDateTime disappearanceDate;
    private SpecialNeed specialNeed;
    private String location;
    private String additionalNotes;
}