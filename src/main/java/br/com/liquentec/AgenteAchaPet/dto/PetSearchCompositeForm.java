package br.com.liquentec.AgenteAchaPet.dto;


import br.com.liquentec.AgenteAchaPet.dto.request.PetSearchRequestForm;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class PetSearchCompositeForm {

    
    private PersonDTO person;

    @Valid
    private PetDTO pet;

    // @Valid
    private PetSearchRequestForm search;

}