package br.com.liquentec.AgenteAchaPet.mapper;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import br.com.liquentec.AgenteAchaPet.dto.PetDTO;
import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;

public class PetMapperTest {

    private final PetMapper mapper = Mappers.getMapper(PetMapper.class);

    private byte[] image;
    private String imageBase64;

    @BeforeEach
    void setup(){

        image = new byte[] { 1, 2, 3 }; 
        imageBase64 = Base64.getEncoder().encodeToString(image);       
    }

    @Test
    void testToDto(){

        // 1. Cria o Person e seta o ID
        Person person = new Person();
        person.setId(1L);

        Pet pet = new Pet();

        pet.setId(1L);
        pet.setName("Toto");
        pet.setAge(1);
        pet.setBreed("Boxer");
        pet.setColor("Caramelo");
        pet.setPhoto(image);
        pet.setPerson(person);

        PetDTO dto = mapper.toDTO(pet);

        assertNotNull(dto);
        assertNotNull(pet.getPerson());

        assertArrayEquals(image, pet.getPhoto());

        assertEquals(1L, dto.getId());
        assertEquals("Toto", dto.getName());
        assertEquals(1, dto.getAge());
        assertEquals("Boxer", dto.getBreed());
        assertEquals("Caramelo", dto.getColor());
        assertEquals(imageBase64, dto.getImageBase64());
        assertEquals(1L, dto.getPersonId());

        // O seguinte assert é redundante, mas útil para garantir a integridade do
        // objeto original:
        assertArrayEquals(image, pet.getPhoto());
        assertNotNull(pet.getPerson());
        assertEquals(1L, pet.getPerson().getId());
    }

    @Test
    void testToEntity(){        

        PetDTO dto = new PetDTO();

        dto.setId(1L);
        dto.setName("Toto");
        dto.setAge(2);
        dto.setBreed("Boxer");
        dto.setColor("Marrom");
        dto.setImageBase64(imageBase64);
        dto.setPersonId(1L);

        // mapeamento para entidade
        Pet pet = PetMapper.toEntity(dto);       
 
        assertNotNull(pet);
        assertNotNull(pet.getPerson());

        assertArrayEquals(image, pet.getPhoto());

        assertEquals(1L, pet.getId());
        assertEquals("Toto", pet.getName());
        assertEquals(2, pet.getAge());
        assertEquals("Boxer", pet.getBreed());
        assertEquals("Marrom", pet.getColor());        
        assertEquals(1L, pet.getPerson().getId());

        
    }
            
}
