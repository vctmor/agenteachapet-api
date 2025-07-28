package br.com.liquentec.AgenteAchaPet.dto;


import br.com.liquentec.AgenteAchaPet.dto.request.PetSearchRequestForm;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PetSearchCompositeFormTest {

    private Validator validator;
    private PetSearchCompositeForm form;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        form = new PetSearchCompositeForm();
    }

    @Test
    void testNoArgsConstructor() {
        assertNull(form.getPerson());
        assertNull(form.getPet());
        assertNull(form.getSearch());
    }

    @Test
    void testSettersAndGetters() {
        PersonDTO person = new PersonDTO(1L, "Maria", "maria@email.com", "11999999999",
                br.com.liquentec.AgenteAchaPet.model.Role.TUTOR);
        PetDTO pet = new PetDTO(1L, "Bidu", "preto", "SRD", "2", null, 1L);
        PetSearchRequestForm search = new PetSearchRequestForm(); // preencha se houver obrigatórios

        form.setPerson(person);
        form.setPet(pet);
        form.setSearch(search);

        assertEquals(person, form.getPerson());
        assertEquals(pet, form.getPet());
        assertEquals(search, form.getSearch());
    }

    @Test
    void testInvalidRequiredFields() {
        // Não setar nada, todos são nulos
        Set<ConstraintViolation<PetSearchCompositeForm>> violations = validator.validate(form);

        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("person")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("pet")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("search")));
    }

    @Test
    void testValidFields() {
        PersonDTO person = new PersonDTO(1L, "Maria", "maria@email.com", "11999999999",
                br.com.liquentec.AgenteAchaPet.model.Role.TUTOR);
        PetDTO pet = new PetDTO();
        pet.setId(1L);
        pet.setName("Bidu");
        pet.setColor("preto");
        pet.setBreed("SRD");
        pet.setAge(2); // ou 2 se for Integer
        pet.setImageBase64(null);
        pet.setPersonId(1L);

        PetSearchRequestForm search = new PetSearchRequestForm();
        // preencha campos obrigatórios do search, se houver

        form.setPerson(person);
        form.setPet(pet);
        form.setSearch(search);

        Set<ConstraintViolation<PetSearchCompositeForm>> violations = validator.validate(form);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<PetSearchCompositeForm> v : violations) {
                System.out.println("Campo: " + v.getPropertyPath() + " | Mensagem: " + v.getMessage());
            }
        }

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidPerson() {
        PersonDTO person = new PersonDTO(); // inválido
        PetDTO pet = new PetDTO(1L, "Bidu", "preto", "SRD", "2", null, 1L);
        PetSearchRequestForm search = new PetSearchRequestForm();

        form.setPerson(person);
        form.setPet(pet);
        form.setSearch(search);

        Set<ConstraintViolation<PetSearchCompositeForm>> violations = validator.validate(form);

        // Espera erro de validação recursiva em person
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("person")));
    }
}
