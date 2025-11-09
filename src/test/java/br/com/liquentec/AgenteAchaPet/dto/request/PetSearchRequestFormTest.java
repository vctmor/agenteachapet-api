package br.com.liquentec.AgenteAchaPet.dto.request;

import br.com.liquentec.AgenteAchaPet.model.Role;
import br.com.liquentec.AgenteAchaPet.model.SearchRole;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PetSearchRequestFormTest {

    private Validator validator;
    private SearchCreate form;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        form = new SearchCreate();
    }

    @Test
    void testNoArgsConstructor() {
        assertNull(form.getPetId());
        assertNull(form.getPersonId());
        assertNull(form.getReporterRole());
        assertNull(form.getDisappearanceDate());
        assertNull(form.getSpecialNeed());
        assertNull(form.getLocation());
        assertNull(form.getAdditionalNotes());
    }

    @Test
    void testSettersAndGetters() {
        form.setPetId(1L);
        form.setPersonId(2L);
        form.setReporterRole(Role.TUTOR);
        LocalDateTime date = LocalDateTime.now();
        form.setDisappearanceDate(date);
        form.setSpecialNeed("Precisa de remédio");
        form.setLocation("Parque Central");
        form.setAdditionalNotes("Última vez visto com coleira vermelha");

        assertEquals(1L, form.getPetId());
        assertEquals(2L, form.getPersonId());
        assertEquals(Role.TUTOR, form.getReporterRole());
        assertEquals(date, form.getDisappearanceDate());
        assertEquals("Precisa de remédio", form.getSpecialNeed());
        assertEquals("Parque Central", form.getLocation());
        assertEquals("Última vez visto com coleira vermelha", form.getAdditionalNotes());
    }

    @Test
    void testInvalidRequiredFields() {
        // Todos os campos obrigatórios nulos ou vazios
        form.setPetId(null);
        form.setPersonId(null);
        form.setReporterRole(null);
        form.setDisappearanceDate(null);
        form.setLocation("");

        Set<ConstraintViolation<SearchCreate>> violations = validator.validate(form);

        assertEquals(5, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("petId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("personId")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("reporterRole")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("disappearanceDate")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("location")));
    }

    @Test
    void testValidFields() {
        form.setPetId(1L);
        form.setPersonId(2L);
        form.setReporterRole(Role.TUTOR);
        form.setDisappearanceDate(LocalDateTime.now());
        form.setLocation("Rua das Flores, 100");
        form.setSpecialNeed("Nenhuma");
        form.setAdditionalNotes("Usava coleira azul");

        Set<ConstraintViolation<SearchCreate>> violations = validator.validate(form);

        assertTrue(violations.isEmpty());
    }
}
