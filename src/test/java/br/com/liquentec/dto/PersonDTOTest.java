package br.com.liquentec.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
