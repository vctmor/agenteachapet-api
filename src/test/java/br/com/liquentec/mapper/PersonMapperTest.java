package br.com.liquentec.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.Role;
import br.com.liquentec.dto.PersonWithPetsDTO;

public class PersonMapperTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    void testToWithPetsDto(){

         // Arrange
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Totó");
        pet.setColor("Preto");
        pet.setBreed("Vira-lata");
        pet.setAge(2);
 
        Person person = new Person();
        person.setId(1L);
        person.setName("Maria");
        person.setEmail("maria@email.com");
        person.setPhone("11999999999");
        person.setRole(Role.TUTOR);
        person.setPets(List.of(pet));
        
        PersonWithPetsDTO pWithPetsDTO;
          // Act
        PersonWithPetsDTO pwpDto = PersonMapper.INSTANCE.pwpToEntity(pWithPetsDTO);

        // Assert
        assertNotNull(pwpDto);
        assertEquals("Maria", pwpDto.getName());
        assertEquals("maria@email.com", pwpDto.getEmail());
        assertEquals("11999999999", pwpDto.getPhone());
        assertEquals(Role.TUTOR, pwpDto.getRole());

        assertNotNull(pwpDto.getPets());
        assertEquals(1, pwpDto.getPets().size());
        assertEquals("Totó", pwpDto.getPets().get(0).getName());
    }


}
