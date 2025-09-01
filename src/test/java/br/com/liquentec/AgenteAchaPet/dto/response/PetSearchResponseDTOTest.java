package br.com.liquentec.AgenteAchaPet.dto.response;


import br.com.liquentec.AgenteAchaPet.model.Role;
import br.com.liquentec.AgenteAchaPet.model.SearchRole;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetSearchResponseDTOTest {

    private Validator validator;
    private PetSearchResponseDTO dto;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        dto = new PetSearchResponseDTO();
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        dto.setId(1L);
        dto.setPetId(2L);
        dto.setPersonId(3L);
        dto.setPetName("Bidu");
        dto.setPersonName("Maria");
        dto.setReporterRole(Role.TUTOR);
        dto.setEmail("maria@email.com");
        dto.setPhone("11999999999");
        dto.setName("Bidu");
        dto.setColor("preto");
        dto.setBreed("SRD");
        dto.setAge(3);
        dto.setSpecialNeed("Cego de um olho");
        dto.setDisappearanceDate(LocalDateTime.now());
        dto.setLocation("Rua das Flores, 123");
        dto.setPhoto(new byte[] { 1, 2, 3 });
        dto.setAdditionalNotes("Muito dócil");
        dto.setSlug("bidu-maria-2024");

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getPetId());
        assertEquals(3L, dto.getPersonId());
        assertEquals("Bidu", dto.getPetName());
        assertEquals("Maria", dto.getPersonName());
        assertEquals(Role.TUTOR, dto.getReporterRole());
        assertEquals("maria@email.com", dto.getEmail());
        // ... (pode testar os demais getters)
    }

    @Test
    void testValidFields() {
        dto.setId(1L);
        dto.setPetId(2L);
        dto.setPersonId(3L);
        dto.setPetName("Bidu");
        dto.setPersonName("Maria");
        dto.setReporterRole(Role.TUTOR);
        dto.setEmail("maria@email.com");

        Set<ConstraintViolation<PetSearchResponseDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidRequiredFields() {
        // Não preenche nenhum campo obrigatório
        Set<ConstraintViolation<PetSearchResponseDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("id")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("petId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("personId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("petName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("personName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("reporterRole")));
    }

    @Test
    void testInvalidEmail() {
        dto.setId(1L);
        dto.setPetId(2L);
        dto.setPersonId(3L);
        dto.setPetName("Bidu");
        dto.setPersonName("Maria");
        dto.setReporterRole(Role.TUTOR);
        dto.setEmail("invalid-email");

        Set<ConstraintViolation<PetSearchResponseDTO>> violations = validator.validate(dto);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }
}
