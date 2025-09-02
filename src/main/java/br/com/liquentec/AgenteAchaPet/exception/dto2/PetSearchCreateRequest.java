package br.com.liquentec.AgenteAchaPet.exception.dto2;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

// A.1 — Request “composita” de criação
public record PetSearchCreateRequest(
        @Valid @NotNull(message = "os dados da pessoa são obrigatórios") PersonCreate person,

        @Valid @NotNull(message = "os dados do pet são obrigatórios") PetCreate pet,

        @Valid @NotNull(message = "os dados da busca são obrigatórios") SearchCreate search) {
}
