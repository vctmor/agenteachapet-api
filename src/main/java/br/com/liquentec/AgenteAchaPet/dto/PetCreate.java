package br.com.liquentec.AgenteAchaPet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetCreate {

    public PetCreate(long l, String string, String string2, String string3, String string4, Object object, long m) {
        //TODO Auto-generated constructor stub
    }

    private Long id;

    @NotBlank(message = "o nome é obrigatório")
    private String name;

    @NotBlank(message = "a cor é obrigatória")
    private String color;

    @NotBlank(message = "a raça é obrigatória")
    private String breed;

    @NotNull(message = "a idade é obrigatória")
    private Integer age;

    private String imageBase64;

    // @NotNull(message = "o id da pessoa é obritatório")
    private Long personId;


}
