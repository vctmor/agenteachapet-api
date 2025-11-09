package br.com.liquentec.AgenteAchaPet.mapper;

import br.com.liquentec.AgenteAchaPet.dto.PersonCreate;
import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Role;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

class PersonMapperTest {

  private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

  private Person person;

  PersonCreate dto;

  @BeforeEach
  void setup() {

    person = new Person();
    
  }

  @Test
  void testToDto() {  

    person.setId(1L);
    person.setName("Alice");
    person.setEmail("alice@email.com");
    person.setPhone("11999999999");
    person.setRole(Role.TUTOR);   

    dto = mapper.toDto(person); // mapeando o DTO

    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("Alice", dto.getName());
    assertEquals("alice@email.com", dto.getEmail());
    assertEquals("11999999999", dto.getPhone());
    assertEquals(Role.TUTOR, dto.getRole());
  }

  @Test
  void testToEntity() {
    
    dto = mapper.toDto(person);

    dto.setId(2L);
    dto.setName("Bob");
    dto.setEmail("bob@email.com");
    dto.setPhone("11988887777");
    dto.setRole(Role.SENTINEL);

    person = mapper.toEntity(dto);
  
    assertNotNull(person);
    assertEquals(2L, person.getId());
    assertEquals("Bob", person.getName());
    assertEquals("bob@email.com", person.getEmail());
    assertEquals("11988887777", person.getPhone());
    assertEquals(Role.SENTINEL, person.getRole());
  }

  @Test
  void testPwpToEntity() {

    dto = mapper.toDto(person);

    dto.setName("Carol");
    dto.setEmail("carol@email.com");
    dto.setPhone("11977776666");
    dto.setRole(Role.BASTIAN);

    person = mapper.toEntity(dto);

    assertNotNull(person);
    assertNull(person.getId()); // ID não vem nesse DTO
    assertEquals("Carol", person.getName());
    assertEquals("carol@email.com", person.getEmail());
    assertEquals("11977776666", person.getPhone());
    assertEquals(Role.BASTIAN, person.getRole());
  }
}
