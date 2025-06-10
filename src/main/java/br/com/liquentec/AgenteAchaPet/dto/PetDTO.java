package br.com.liquentec.AgenteAchaPet.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {
    private Long id;
    private String name;
    private String color;
    private String breed;
    private Integer age;
    private String imageBase64;
    private Long personId;


}
