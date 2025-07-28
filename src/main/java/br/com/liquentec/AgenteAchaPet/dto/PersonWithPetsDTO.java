package br.com.liquentec.AgenteAchaPet.dto;

import java.util.List;

import br.com.liquentec.AgenteAchaPet.model.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonWithPetsDTO {

    @NotBlank(message = "o nome é obrigatório")
    private String name;

    @NotBlank(message = "o e-mail é obrigatório")
    @Email(message = "e-mail inválido")
    private String email;

    @NotBlank(message = "o telefone é obrigatório")
    private String phone;

    @NotNull(message = "o papel é obrigatório")
    private Role role;

    @Valid
    @NotNull(message = "o pet é obrigatório")
    private List<PetDTO> pets;

}
