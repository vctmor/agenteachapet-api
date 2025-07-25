package br.com.liquentec.AgenteAchaPet.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.liquentec.AgenteAchaPet.model.SearchRole;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetSearchResponseDTO {
    private Long id;
    private Long petId;
    private Long personId;
    private String petName;
    private String personName;
    private SearchRole reporterRole;
    private String email;
    private String phone;
    private String name;
    private String color;
    private String breed;
    private Integer age;
    private String specialNeed;
    private LocalDateTime disappearanceDate;
    private String location;    
    private byte[] photo;
    private String additionalNotes;
    private String slug;
}
