package br.com.liquentec.AgenteAchaPet.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

    private Person person;

    @BeforeEach
    void setup(){

        person = new Person();
    }

    @Test
    void shouldSetAndGetBasicAttributes(){
      
        person.setId(1L);
        person.setName("Maria");
        person.setEmail("maria@email.com");
        person.setPhone("11965498733");
        person.setRole(Role.TUTOR);

        assertEquals(1L, person.getId());
        assertEquals("Maria", person.getName());
        assertEquals("maria@email.com", person.getEmail());
        assertEquals("11965498733", person.getPhone());
        assertEquals(Role.TUTOR, person.getRole());
    }

    @Test
    void shouldReturnNullWhenPetsNotSet() {

        assertNull(person.getPets());
    }

    @Test
    void shouldReturnEmptyListWhenPetsSetEmpty(){

        person.setPets(List.of());
        assertNotNull(person.getPets());
        assertTrue(person.getPets().isEmpty());
    }

    @Test
    void shouldAllowAddingPets(){

        Pet pet = new Pet();

        pet.setName("Rex");
        person.setPets(List.of(pet));

        assertNotNull(person.getPets());
        assertEquals(1, person.getPets().size());
        assertEquals("Rex", person.getPets().get(0).getName());

    }

}
