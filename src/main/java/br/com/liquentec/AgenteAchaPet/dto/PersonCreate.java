package br.com.liquentec.AgenteAchaPet.dto;

import br.com.liquentec.AgenteAchaPet.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreate {

    private Long id;

    @NotBlank(message = "o nome é obrigatório")
    private String name;

    @NotBlank(message = "o e-mail é obrigatório")
    @Email(message = "e-mail inválido")
    private String email;

    @NotBlank(message = "o telefone é obrigatório")
    private String phone;

    // @NotNull(message = "o papel é obrigatório")
    private Role role;
}
    

