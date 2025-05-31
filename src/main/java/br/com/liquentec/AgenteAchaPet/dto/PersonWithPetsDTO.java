package br.com.liquentec.AgenteAchaPet.dto;

import java.util.List;

import br.com.liquentec.AgenteAchaPet.model.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonWithPetsDTO {

    private String name;
    private String email;
    private String phone;
    private Role role;

    private List<PetDTO> pets;

}
