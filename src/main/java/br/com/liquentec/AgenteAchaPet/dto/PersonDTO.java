package br.com.liquentec.AgenteAchaPet.dto;

import br.com.liquentec.AgenteAchaPet.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    private Long id;

    
    private String name;

    
    private String email;

    
    private String phone;

    
    private Role role;
}
    

