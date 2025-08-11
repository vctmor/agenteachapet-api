package br.com.liquentec.AgenteAchaPet.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.liquentec.AgenteAchaPet.model.Person;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PetDTOTest {

    private Validator validator;

    private PetDTO petDTO;

    @BeforeEach
    void setup(){

        petDTO = new PetDTO();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldSetAndGetBasicAttributes(){

        petDTO.setId(1L);
        petDTO.setName("Tuco");
        petDTO.setAge(1);
        petDTO.setBreed("poodle");
        petDTO.setColor("bege");
        petDTO.setImageBase64("img");
        petDTO.setPersonId(1L);

        assertEquals(1L, petDTO.getId());
        assertEquals("Tuco", petDTO.getName());
        assertEquals(1, petDTO.getAge());
        assertEquals("poodle", petDTO.getBreed());
        
        assertEquals("bege", petDTO.getColor());
        assertEquals("img", petDTO.getImageBase64());
        assertEquals(1, petDTO.getPersonId());       

   }

   @Test
   void testInvalidRequiredFields(){

        petDTO.setId(1L);
        petDTO.setName("");
        petDTO.setAge(null);
        petDTO.setBreed("");
        petDTO.setColor("");
        petDTO.setImageBase64("");
        petDTO.setPersonId(null);

        Set<ConstraintViolation<PetDTO>> violations = validator.validate(petDTO);

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
