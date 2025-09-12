package br.com.liquentec.AgenteAchaPet.dto;


import br.com.liquentec.AgenteAchaPet.dto.request.SearchCreate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetSearchCreateRequest {

    @Valid
    @NotNull(message = "os dados da pessoa são obrigatórios")
    private PersonCreate person;

    @Valid
    @NotNull(message = "os dados do pet são obrigatórios")
    private PetCreate pet;

    @Valid
    @NotNull(message = "os dados da busca são obrigatórios")
    private SearchCreate search;

}