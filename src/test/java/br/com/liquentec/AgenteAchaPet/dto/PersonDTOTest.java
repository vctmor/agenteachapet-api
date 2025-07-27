package br.com.liquentec.AgenteAchaPet.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.liquentec.AgenteAchaPet.model.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PersonDTOTest {

    private Validator validator;

    private PersonDTO dto;

    @BeforeEach
    void setup(){

        dto = new PersonDTO();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator(); 
    }

    @Test
    void testAllArgsConstructorAndGetters(){

        PersonDTO dto = new PersonDTO(
            1L, "Maria", "maria@email.com", "11999999999", Role.BASTIAN);

        assertEquals(1l, dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals( "maria@email.com",dto.getEmail());
        assertEquals("11999999999", dto.getPhone());
        assertEquals(Role.BASTIAN, dto.getRole());
    }

    @Test
    void testSetters(){

      
        dto.setId(2L);
        dto.setName("J");
        dto.setEmail("a@b");
        dto.setPhone("33");
        dto.setRole(Role.RESCUER);
        
        assertEquals(2L, dto.getId());
        assertEquals("J", dto.getName());
        assertEquals("a@b", dto.getEmail());
        assertEquals("33", dto.getPhone());
        assertEquals(Role.RESCUER, dto.getRole());
    }

    @Test
    void testNoArgsConstructor() {

               assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getEmail());
        assertNull(dto.getPhone());
        assertNull(dto.getRole());
    }

    @Test
    void testInvalidRequiredFields(){

        dto.setId(1L);
        dto.setName("");
        dto.setEmail("a_b");
        dto.setPhone("");
        dto.setRole(null);

        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(dto);

        assertEquals(4, violations.size());

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                .toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("role")));

    }

    @Test
    void testValidFields(){

        dto.setId(1L);
        dto.setName("Maria");
        dto.setEmail("maria@email.com");
        dto.setPhone("11912345678");
        dto.setRole(Role.TUTOR);

        Set<ConstraintViolation<PersonDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }


}
