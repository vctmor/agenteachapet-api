package br.com.liquentec.AgenteAchaPet.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    void testCreatAndReadAttributes(){

        Person person = new Person();

        person.setId(1L);
        person.setName("Maria");
        person.setEmail("maria@email.com");
        person.setPhone("11965498733");
        person.setRole(Role.ADVISER);

        assertEquals(1L, person.getId());
        assertEquals("Maria", person.getName());
        assertEquals("maria@email.com", person.getEmail());
        assertEquals("11965498733", person.getPhone());
        assertEquals(Role.ADVISER, person.getRole());

    }
}
