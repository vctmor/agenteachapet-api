package br.com.liquentec.AgenteAchaPet.dto.response;

import java.time.LocalDateTime;

import br.com.liquentec.AgenteAchaPet.model.SearchRole;
import lombok.Data;

@Data
public class PetSearchResponseDTO {
    private Long id;
    private Long petId;
    private Long personId;
    private String petName;
    private String personName;
    private SearchRole reporterRole;
    private LocalDateTime disappearanceDate;
    private String location;
    private byte[] photo;
    private String additionalNotes;
}
