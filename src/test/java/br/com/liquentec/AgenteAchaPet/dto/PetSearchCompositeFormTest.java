package br.com.liquentec.AgenteAchaPet.dto;


import br.com.liquentec.AgenteAchaPet.dto.request.SearchCreate;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PetSearchCompositeFormTest {

    private Validator validator;
    private PetSearchCreateRequest form;

    @BeforeEach
    void setup() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        form = new PetSearchCreateRequest();
    }

    @Test
    void testNoArgsConstructor() {
        assertNull(form.getPerson());
        assertNull(form.getPet());
        assertNull(form.getSearch());
    }

    @Test
    void testSettersAndGetters() {
        PersonCreate person = new PersonCreate(1L, "Maria", "maria@email.com", "11999999999",
                br.com.liquentec.AgenteAchaPet.model.Role.TUTOR);
        PetCreate pet = new PetCreate(1L, "Bidu", "preto", "SRD", "2", null, 1L);
        SearchCreate search = new SearchCreate(); // preencha se houver obrigatórios

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
        Set<ConstraintViolation<PetSearchCreateRequest>> violations = validator.validate(form);

        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("person")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("pet")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("search")));
    }

    @Test
    void testValidFields() {
        
        PersonCreate person = new PersonCreate(1L, "Maria", "maria@email.com", "11999999999",
                br.com.liquentec.AgenteAchaPet.model.Role.TUTOR);
        PetCreate pet = new PetCreate();
        pet.setId(1L);
        pet.setName("Bidu");
        pet.setColor("preto");
        pet.setBreed("SRD");
        pet.setAge(2); // ou 2 se for Integer
        pet.setImageBase64(null);
        pet.setPersonId(1L);

        SearchCreate search = new SearchCreate();
        // preencha campos obrigatórios do search, se houver

        search.setReporterRole(br.com.liquentec.AgenteAchaPet.model.Role.TUTOR);
        search.setDisappearanceDate(LocalDateTime.now());
        search.setLocation("SP");

        form.setPerson(person);
        form.setPet(pet);
        form.setSearch(search);

        Set<ConstraintViolation<PetSearchCreateRequest>> violations = validator.validate(form);

        if (!violations.isEmpty()) {

            for (ConstraintViolation<PetSearchCreateRequest> v : violations) {
                System.out.println("Campo: " + v.getPropertyPath() + " | Mensagem: " + v.getMessage());
            }
        }

        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidPerson() {
        PersonCreate person = new PersonCreate(); // inválido
        PetCreate pet = new PetCreate(1L, "Bidu", "preto", "SRD", "2", null, 1L);
        SearchCreate search = new SearchCreate();

        form.setPerson(person);
        form.setPet(pet);
        form.setSearch(search);

        Set<ConstraintViolation<PetSearchCreateRequest>> violations = validator.validate(form);

        // Espera erro de validação recursiva em person
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("person")));
    }
}
