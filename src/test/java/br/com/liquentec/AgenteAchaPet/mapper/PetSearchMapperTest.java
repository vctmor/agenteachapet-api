package br.com.liquentec.AgenteAchaPet.mapper;

import br.com.liquentec.AgenteAchaPet.dto.response.CartazDTO;
import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.model.Role;
import br.com.liquentec.AgenteAchaPet.suport.EntityBuilders;
import br.com.liquentec.AgenteAchaPet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetSearchMapperTest {

    private final PetSearchMapper mapper = Mappers.getMapper(PetSearchMapper.class);

    private PetSearch petSearch;
    private Pet pet;
    private Person person;

    @BeforeEach
    void setup() {
        // Monta Pet
        pet = new Pet();
        pet.setId(101L);
        pet.setName("Rex");
        pet.setColor("Preto");
        pet.setBreed("SRD");
        pet.setAge(4);
        // pet.setPhoto(photo); // Não precisa mais do photo

        // Monta Person
        person = new Person();
        person.setId(42L);
        person.setName("Alice");
        person.setEmail("alice@email.com");
        person.setPhone("11999999999");
        // person.setRole(Role.TUTOR); // Se existir, pode setar

        // Monta PetSearch
        petSearch = new PetSearch();
        petSearch.setId(1001L);
        petSearch.setPet(pet);
        petSearch.setRegisteredBy(person);
        petSearch.setReporterRole(Role.TUTOR); // Use o enum correto
        petSearch.setSpecialNeed("Precisa de remédio");
        petSearch.setDisappearanceDate(LocalDateTime.of(2023, 1, 15, 10, 30));
        petSearch.setLocation("São Paulo");
        petSearch.setAdditionalNotes("Assustado com fogos");
        petSearch.setSlug("rex-1001");
    }

     @Test
    void shouldMapEntityToCartazDTO() {
        Person r = EntityBuilders.person("Jonas", "a@b.com");
        Pet p = EntityBuilders.pet(r, "Lola");
        PetSearch ps = EntityBuilders.petSearch(p, r, "lola-8f3a2c");

        CartazDTO dto = mapper.toCartazDto(ps);

        assertEquals("lola-8f3a2c", dto.getSlug());
        assertEquals("Lola", dto.getPet().getPetName());
        assertEquals("Jonas", dto.getReporter().getName());
        assertEquals("Vila Sônia", dto.getSighting().getLastSeenPlace());
  }

    @Test
    void testToResponseDto() {
        CartazDTO dto = mapper.toCartazDto(petSearch);

        assertNotNull(dto);
        assertEquals(1001L, dto.getPet());
        assertEquals(101L, dto.getPet());
        assertEquals(42L, dto.getPersonId());
        assertEquals("Rex", dto.getPet());
        assertEquals("SRD", dto.getBreed());
        assertEquals("Preto", dto.getColor());
        assertEquals("Alice", dto.getPersonId());
        assertEquals(Role.TUTOR, dto.getReporter());
        assertEquals(LocalDateTime.of(2023, 1, 15, 10, 30), dto.getDisappearanceDate());
        assertEquals("São Paulo", dto.getLocation());
        assertEquals("Precisa de remédio", dto.getSpecialNeed());
        assertEquals("Assustado com fogos", dto.getAdditionalNotes());
        assertEquals("rex-1001", dto.getSlug());
        // Não existe mais assertArrayEquals(photo, dto.getPhoto());
    }

    @Test
    void testToDtoStatic() {
        CartazDTO dto = PetSearchMapper.toCartazDto(petSearch);

        assertNotNull(dto);
        assertEquals(1001L, dto.getBreed());
        assertEquals(101L, dto.getPersonId());
        assertEquals(42L, dto.getPersonId());
        assertEquals("Rex", dto.getPet());
        assertEquals("SRD", dto.getBreed());
        assertEquals("Preto", dto.getColor());
        assertEquals("Alice", dto.getPersonId());
        assertEquals(Role.TUTOR, dto.getReporter());
        assertEquals(LocalDateTime.of(2023, 1, 15, 10, 30), dto.getDisappearanceDate());
        assertEquals("São Paulo", dto.getLocation());
        assertEquals("Precisa de remédio", dto.getSpecialNeed());
        assertEquals("Assustado com fogos", dto.getAdditionalNotes());
        assertEquals("rex-1001", dto.getSlug());
    }

    @Test
    void testToDoList() {
        PetSearch search2 = new PetSearch();
        search2.setId(1002L);
        search2.setPet(pet);
        search2.setRegisteredBy(person);

        List<PetSearch> list = Arrays.asList(petSearch, search2);

        List<PetSearchResponseDTO> dtoList = mapper.toDoList(list);

        assertEquals(2, dtoList.size());
        assertEquals(1001L, dtoList.get(0).getId());
        assertEquals(1002L, dtoList.get(1).getId());
    }

    @Test
    void testNullSafety() {
        // Testa campos obrigatórios nulos
        PetSearch empty = new PetSearch();
        empty.setId(null);
        empty.setPet(null);
        empty.setRegisteredBy(null);

        // Para o método estático, NullPointerException é esperado
        assertThrows(NullPointerException.class, () -> PetSearchMapper.toDto(empty));

        // Para o mapper do MapStruct, pode lançar NullPointerException ou retornar DTO
        // nulo/campos nulos,
        // então, teste ambos os comportamentos possíveis:
        try {
            PetSearchResponseDTO dto = mapper.toResponseDto(empty);
            // Se não lançou exceção, os campos principais devem estar nulos
            assertNotNull(dto);
            assertNull(dto.getId());
            assertNull(dto.getPetId());
            assertNull(dto.getPersonId());
            assertNull(dto.getPetName());
            assertNull(dto.getPersonName());
            // ... outros campos principais
        } catch (NullPointerException ex) {
            // Ok, comportamento aceitável, documentado no teste
        }
    }

}
