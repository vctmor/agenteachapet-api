package br.com.liquentec.AgenteAchaPet.dto;

import static org.junit.jupiter.api.Assertions.*;

import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.Role;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class PersonWithPetsDTOTest {

    private Validator validator;
    private PersonWithPetsDTO dto;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        dto = new PersonWithPetsDTO();
    }

    @Test
    void testAllArgsConstructorAndGetters() {

        PetDTO pet = new PetDTO();

        pet.setName("Bidu");

        List<PetDTO> pets = Collections.singletonList(pet);

        PersonWithPetsDTO dto = new PersonWithPetsDTO(
                "Maria",
                "maria@email.com",
                "11999999999",
                Role.TUTOR,
                pets);

        assertEquals("Maria", dto.getName());
        assertEquals("maria@email.com", dto.getEmail());
        assertEquals("11999999999", dto.getPhone());
        assertEquals(Role.TUTOR, dto.getRole());
        assertNotNull(dto.getPets());
        assertEquals(1, dto.getPets().size());
        assertEquals("Bidu", dto.getPets().get(0).getName());
    }

    @Test
    void testSetters() {
        List<PetDTO> pets = new ArrayList<>();
        PetDTO pet = new PetDTO();
        pet.setName("Rex");
        pets.add(pet);

        dto.setName("João");
        dto.setEmail("joao@email.com");
        dto.setPhone("1188888888");
        dto.setRole(Role.RESCUER);
        dto.setPets(pets);

        assertEquals("João", dto.getName());
        assertEquals("joao@email.com", dto.getEmail());
        assertEquals("1188888888", dto.getPhone());
        assertEquals(Role.RESCUER, dto.getRole());
        assertNotNull(dto.getPets());
        assertEquals(1, dto.getPets().size());
        assertEquals("Rex", dto.getPets().get(0).getName());
    }

    @Test
    void testNoArgsConstructor() {
        assertNull(dto.getName());
        assertNull(dto.getEmail());
        assertNull(dto.getPhone());
        assertNull(dto.getRole());
        assertNull(dto.getPets());
    }

    @Test
    void testInvalidRequiredFields() {
        // Todos os campos nulos ou vazios
        dto.setName("");
        dto.setEmail("inválido");
        dto.setPhone("");
        dto.setRole(null);
        dto.setPets(null);

        Set<ConstraintViolation<PersonWithPetsDTO>> violations = validator.validate(dto);

        // Esperado: name, email, phone, role, pets
        assertEquals(5, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("role")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("pets")));
    }

    @Test
    void testValidFields() {
        PetDTO pet = new PetDTO();

        pet.setName("Rex");

        List<PetDTO> pets = Collections.singletonList(pet);

        dto.setName("Maria");
        dto.setEmail("maria@email.com");
        dto.setPhone("11999999999");
        dto.setRole(Role.TUTOR);
        dto.setPets(pets);

        Set<ConstraintViolation<PersonWithPetsDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testPetsInvalidInsideList() {
        // PetDTO inválido (todos os campos nulos)
        PetDTO pet = new PetDTO();
        List<PetDTO> pets = Collections.singletonList(pet);

        dto.setName("Maria");
        dto.setEmail("maria@email.com");
        dto.setPhone("11999999999");
        dto.setRole(Role.TUTOR);
        dto.setPets(pets);

        Set<ConstraintViolation<PersonWithPetsDTO>> violations = validator.validate(dto);

        // Deve conter violações dos campos internos de PetDTO
        assertFalse(violations.isEmpty());

        // Exemplo: Printando os detalhes das violações
        for (ConstraintViolation<PersonWithPetsDTO> violation : violations) {
            System.out.println("Propriedade: " + violation.getPropertyPath());
            System.out.println("Mensagem: " + violation.getMessage());
            System.out.println("---");
        }

        // Espera ao menos as violações obrigatórias do PetDTO
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("pets[0].name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("pets[0].color")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("pets[0].breed")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("pets[0].age")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("pets[0].personId")));
    }
   
}