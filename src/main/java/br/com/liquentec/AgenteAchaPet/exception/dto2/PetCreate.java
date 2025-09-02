package br.com.liquentec.AgenteAchaPet.exception.dto2;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// A.3 — Pet (sem id / sem base64 porque a foto vai no Multipart)
public record PetCreate(
        @NotBlank(message = "o nome é obrigatório") String name,
        @NotBlank(message = "a cor é obrigatória") String color,
        @NotBlank(message = "a raça é obrigatória") String breed,
        @NotNull(message = "a idade é obrigatória") Integer age) {
}
