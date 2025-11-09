package br.com.liquentec.AgenteAchaPet.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PetDTOTest {

    private Validator validator;

    private PetCreate petCreate;

    @BeforeEach
    void setup(){

        petCreate = new PetCreate();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldSetAndGetBasicAttributes(){

        petCreate.setId(1L);
        petCreate.setName("Tuco");
        petCreate.setAge(1);
        petCreate.setBreed("poodle");
        petCreate.setColor("bege");
        petCreate.setImageBase64("img");
        petCreate.setPersonId(1L);

        assertEquals(1L, petCreate.getId());
        assertEquals("Tuco", petCreate.getName());
        assertEquals(1, petCreate.getAge());
        assertEquals("poodle", petCreate.getBreed());
        
        assertEquals("bege", petCreate.getColor());
        assertEquals("img", petCreate.getImageBase64());
        assertEquals(1, petCreate.getPersonId());       

   }

   @Test
   void testInvalidRequiredFields(){

        petCreate.setId(1l);
        petCreate.setName("");
        petCreate.setAge(null);
        petCreate.setBreed("");
        petCreate.setColor("");
        petCreate.setImageBase64(null);
        petCreate.setPersonId(null);

        Set<ConstraintViolation<PetCreate>> violations = validator.validate(petCreate);

        assertEquals(5, violations.size());

        // Verifica se existe alguma violação de validação no campo "name"
        boolean nameFieldHasError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name"));

        // O teste só passo se o campo "name" tiver alguma violação
        assertTrue(nameFieldHasError);

        boolean ageFieldHasError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("age"));

        assertTrue(ageFieldHasError);

        boolean breedFieldHasError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("breed"));

        assertTrue(breedFieldHasError);

        boolean agedFieldHasError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("age"));

        assertTrue(agedFieldHasError);

        boolean personIdFieldHasError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("personId"));

        assertTrue(personIdFieldHasError);

    }
}
