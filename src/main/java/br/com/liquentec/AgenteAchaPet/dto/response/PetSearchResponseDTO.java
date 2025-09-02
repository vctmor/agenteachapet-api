package br.com.liquentec.AgenteAchaPet.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.liquentec.AgenteAchaPet.model.Role;
import br.com.liquentec.AgenteAchaPet.model.SearchRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetSearchResponseDTO {

    // @NotNull
    private Long id;

    // @NotNull
    private Long petId;

    // @NotNull
    private Long personId;

    @NotBlank
    private String petName;

    @NotBlank
    private String personName;

    @NotNull
    private Role reporterRole;

    @Email
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