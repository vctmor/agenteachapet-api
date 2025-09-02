package br.com.liquentec.AgenteAchaPet.exception.dto2;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// A.2 — Pessoa (sem id/role na criação)
public record PersonCreate(
        @NotBlank(message = "o nome é obrigatório") String name,
        @NotBlank(message = "o e-mail é obrigatório") @Email(message = "e-mail inválido") String email,
        @NotBlank(message = "o telefone é obrigatório") String phone) {
}