package br.com.liquentec.dto;

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
    private Long personId;
    private String imageBase64;

    // public PetDTO(Long id, String name, String color, String breed, Integer age,
    // Long personId) {
    // this.id = id;
    // this.name = name;
    // this.color = color;
    // this.breed = breed;
    // this.age = age;
    // this.personId = personId;
    // }

}
