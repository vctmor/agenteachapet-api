package br.com.liquentec.AgenteAchaPet.mapper;

import br.com.liquentec.AgenteAchaPet.dto.PersonDTO;
import br.com.liquentec.AgenteAchaPet.dto.PersonWithPetsDTO;
import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Role;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {

  private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

  @Test
  void testToDto() {
    Person person = new Person();
    person.setId(1L);
    person.setName("Alice");
    person.setEmail("alice@email.com");
    person.setPhone("11999999999");
    person.setRole(Role.TUTOR);

    PersonDTO dto = mapper.toDto(person);

    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("Alice", dto.getName());
    assertEquals("alice@email.com", dto.getEmail());
    assertEquals("11999999999", dto.getPhone());
    assertEquals(Role.TUTOR, dto.getRole());
  }

  @Test
  void testToEntity() {
    PersonDTO dto = new PersonDTO();
    dto.setId(2L);
    dto.setName("Bob");
    dto.setEmail("bob@email.com");
    dto.setPhone("11988887777");
    dto.setRole(Role.SENTINEL);

    Person person = mapper.toEntity(dto);

    assertNotNull(person);
    assertEquals(2L, person.getId());
    assertEquals("Bob", person.getName());
    assertEquals("bob@email.com", person.getEmail());
    assertEquals("11988887777", person.getPhone());
    assertEquals(Role.SENTINEL, person.getRole());
  }

  @Test
  void testPwpToEntity() {
    PersonWithPetsDTO dto = new PersonWithPetsDTO();
    dto.setName("Carol");
    dto.setEmail("carol@email.com");
    dto.setPhone("11977776666");
    dto.setRole(Role.BASTIAN);

    Person person = mapper.pwpToEntity(dto);

    assertNotNull(person);
    assertNull(person.getId()); // ID não vem nesse DTO
    assertEquals("Carol", person.getName());
    assertEquals("carol@email.com", person.getEmail());
    assertEquals("11977776666", person.getPhone());
    assertEquals(Role.BASTIAN, person.getRole());
  }
}
