package br.com.liquentec.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.com.liquentec.AgenteAchaPet.model.Role;

public class PersonDTOTest {

    @Test
    void testAllArgsConstructorAndGetters(){

        PersonDTO dto = new PersonDTO(
            1L, "Maria", "maria@email.com", "11999999999", Role.ADVISER);

        assertEquals(1l, dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals( "maria@email.com",dto.getEmail());
        assertEquals("11999999999", dto.getPhone());
        assertEquals(Role.ADVISER, dto.getRole());
    }

    @Test
    void testSetters(){

        PersonDTO dto = new PersonDTO();

        dto.setId(2L);
        dto.setName("J");
        dto.setEmail("a@b");
        dto.setPhone("33");
        dto.setRole(Role.KEEPER);
        
        assertEquals(2L, dto.getId());
        assertEquals("J", dto.getName());
        assertEquals("a@b", dto.getEmail());
        assertEquals("33", dto.getPhone());
        assertEquals(Role.KEEPER, dto.getRole());
    }

    @Test
    void testNoArgsConstructor() {
        PersonDTO dto = new PersonDTO();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getEmail());
        assertNull(dto.getPhone());
        assertNull(dto.getRole());
    }
}
