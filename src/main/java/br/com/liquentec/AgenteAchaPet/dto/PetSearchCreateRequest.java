package br.com.liquentec.AgenteAchaPet.dto;


import br.com.liquentec.AgenteAchaPet.dto.request.PetSearchRequestForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetSearchCreateRequest {

    @Valid
    @NotNull(message = "os dados da pessoa são obrigatórios")
    private PersonDTO person;

    @Valid
    @NotNull(message = "os dados do pet são obrigatórios")
    private PetDTO pet;

    @Valid
    @NotNull(message = "os dados da busca são obrigatórios")
    private PetSearchRequestForm search;

}