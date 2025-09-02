package br.com.liquentec.AgenteAchaPet.exception.dto2;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// A.4 — Busca: papel do relator, quando/onde, notas e necessidade especial opcional
public record SearchCreate(
        @NotNull(message = "o papel do relator é obrigatório") ReporterRole reporterRole,
        @NotNull(message = "a data do desaparecimento é obrigatória") LocalDateTime disappearanceDate,
        @NotBlank(message = "a localização é obrigatória") String location,
        String specialNeedDescription, // opcional
        String additionalNotes // opcional
) {
}
