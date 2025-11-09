package br.com.liquentec.AgenteAchaPet.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PetSearchTest {

    private PetSearch petSearch;

    @BeforeEach
    void setup(){

        petSearch = new PetSearch();
    }

    @Test 
    void shouldSetAndGetBasicAttributes(){

        Pet pet = new Pet();
        pet.setName("Rex");

        Person person = new Person();
        person.setName("Ana");

        LocalDateTime disappearanceDate = LocalDateTime.of(2025, 7, 23, 15, 30);
       

        petSearch.setId(1L);
        petSearch.setPet(pet);
        petSearch.setRegisteredBy(person);
        petSearch.setSpecialNeed("coisas");
        petSearch.setReporterRole(Role.TUTOR);
        petSearch.setDisappearanceDate(disappearanceDate);
        petSearch.setLocation("Vila Sônia");
        petSearch.setSlug("rex-123");
        petSearch.setAdditionalNotes("oi");

        assertEquals(pet, petSearch.getPet());
        assertEquals(person, petSearch.getRegisteredBy());
        assertEquals(Role.TUTOR, petSearch.getReporterRole());
        assertEquals(disappearanceDate, petSearch.getDisappearanceDate());
        assertEquals("Vila Sônia", petSearch.getLocation());
        assertEquals("rex-123", petSearch.getSlug());
        assertEquals("coisas", petSearch.getSpecialNeed());
        assertEquals("oi", petSearch.getAdditionalNotes());

    }

    @Test
    void shouldCreatePetSearchUsingBuilder() {
        Pet pet = new Pet();
        pet.setName("Rex");

        Person person = new Person();
        person.setName("Ana");


        LocalDateTime disappearanceDate = LocalDateTime.of(2025, 7, 23, 15, 30);

        PetSearch petSearch = PetSearch.builder()
                .id(10L)
                .pet(pet)
                .registeredBy(person)
                .reporterRole(Role.TUTOR)
                .disappearanceDate(disappearanceDate)
                .location("Vila Sônia")
                .slug("rex-123")
                .specialNeed("oi")
                .additionalNotes("Last seen near the park.")
                .build();

        assertEquals(10L, petSearch.getId());
        assertEquals(pet, petSearch.getPet());
        assertEquals(person, petSearch.getRegisteredBy());
        assertEquals(Role.TUTOR, petSearch.getReporterRole());
        assertEquals(disappearanceDate, petSearch.getDisappearanceDate());
        assertEquals("Vila Sônia", petSearch.getLocation());
        assertEquals("rex-123", petSearch.getSlug());
        assertEquals("oi", petSearch.getSpecialNeed());
        assertEquals("Last seen near the park.", petSearch.getAdditionalNotes());
    }



}
