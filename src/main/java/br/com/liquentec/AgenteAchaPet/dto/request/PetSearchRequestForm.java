package br.com.liquentec.AgenteAchaPet.dto.request;

import java.time.LocalDateTime;

import br.com.liquentec.AgenteAchaPet.model.SearchRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetSearchRequestForm {

//    @NotNull(message = "o id do pet é obrigatório")

    private Long petId;

    // @NotNull(message = "o id da pessoa é obrigatório")
    private Long personId;

    @NotNull(message = "o papel do relator é obrigatório")
    private SearchRole reporterRole;

    @NotNull(message = "a data do desaparecimento é obrigatória")
    private LocalDateTime disappearanceDate;

    private String specialNeed; // opcional

    @NotBlank(message = "a localização é obrigatória")
    private String location;

    private String additionalNotes; // opcional
}