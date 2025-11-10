package br.com.liquentec.AgenteAchaPet.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import br.com.liquentec.AgenteAchaPet.dto.response.CartazDTO;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetSearchMapperTest {

    @Autowired
    private PetSearchMapper mapper;

    private PetSearch petSearch;
    private LocalDateTime disappearanceDate;

    @BeforeEach
    void setUp() {
        // Pet
        Pet pet = new Pet();
        pet.setId(1001L);
        pet.setName("Rex");
        pet.setColor("Preto");
        pet.setBreed("SRD");
        pet.setAge(3);

        // Person / registeredBy
        Person person = new Person();
        person.setId(101L);
        person.setName("Alice");
        person.setPhone("11999999999");
        person.setEmail("alice@example.com");
        person.setRole(Role.TUTOR);

        disappearanceDate = LocalDateTime.of(2023, 1, 15, 10, 30);

        petSearch = PetSearch.builder()
                .id(999L)
                .pet(pet)
                .registeredBy(person)
                .reporterRole(Role.TUTOR)
                .disappearanceDate(disappearanceDate)
                .location("São Paulo")
                .specialNeed("Precisa de remédio")
                .additionalNotes("Assustado com fogos")
                .slug("rex-1001")
                .build();
    }

    @Test
    void shouldMapPetSearchToCartazDto() {
        // act
        CartazDTO dto = mapper.toCartazDto(petSearch);

        // assert
        assertNotNull(dto);

        // slug
        assertEquals("rex-1001", dto.getSlug());

        // Pet
        assertNotNull(dto.getPet());
        assertEquals(1001L, dto.getPet().getId());
        assertEquals("Rex", dto.getPet().getPetName());
        assertEquals("Preto", dto.getPet().getColor());
        assertEquals("SRD", dto.getPet().getBreed());
        assertEquals(3, dto.getPet().getAge());

        // photoUrl preenchido no @AfterMapping
        assertNotNull(dto.getPet().getPhotoUrl());
        assertTrue(dto.getPet().getPhotoUrl().contains("/api/v1/pet-searches/"));
        assertTrue(dto.getPet().getPhotoUrl().contains("/photo"));

        // Reporter
        assertNotNull(dto.getReporter());
        assertEquals("Alice", dto.getReporter().getName());
        assertEquals("11999999999", dto.getReporter().getPhone());
        assertEquals("alice@example.com", dto.getReporter().getEmail());
        assertEquals(Role.TUTOR, dto.getReporter().getRole());

        // Sighting
        assertNotNull(dto.getSighting());
        assertEquals("São Paulo", dto.getSighting().getLastSeenPlace());
        assertEquals(disappearanceDate, dto.getSighting().getLastSeenAt());
        assertEquals("Assustado com fogos", dto.getSighting().getNotes());
        assertEquals("Precisa de remédio", dto.getSighting().getSpecialNeed());
    }

    @Test
    void shouldMapListOfPetSearchToCartazDtoList() {
        List<CartazDTO> list = mapper.toCartazDtoList(List.of(petSearch));

        assertNotNull(list);
        assertEquals(1, list.size());

        CartazDTO dto = list.get(0);
        assertEquals("rex-1001", dto.getSlug());
        assertEquals("Rex", dto.getPet().getPetName());
    }
}
