package br.com.liquentec.AgenteAchaPet.controller;

import br.com.liquentec.AgenteAchaPet.model.*;
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository;
import br.com.liquentec.AgenteAchaPet.service.PetSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PetSearchController.class)
@ContextConfiguration(classes = PetSearchController.class)
class PetSearchControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetSearchService service;

    @MockitoBean
    private PetSearchRepository petSearchRepository;

    @Test
    void shouldReturnPetSearchBySlug() throws Exception {
        // Criando Pet
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Lola");
        pet.setBreed("SRD");
        pet.setColor("preta");
        pet.setAge(3);

        // Criando Person
        Person person = new Person();
        person.setId(2L);
        person.setName("Jonas");
        person.setEmail("a@b.com");
        person.setPhone("11999990000");

        // Criando PetSearch
        PetSearch petSearch = new PetSearch();
        petSearch.setId(42L);
        petSearch.setSlug("lola-8f3a2c");
        petSearch.setPet(pet);
        petSearch.setRegisteredBy(person);
        petSearch.setReporterRole(Role.TUTOR);
        petSearch.setDisappearanceDate(LocalDateTime.of(2025, 10, 10, 14, 30));
        petSearch.setLocation("Vila Sônia");
        petSearch.setSpecialNeed("medicação");
        petSearch.setAdditionalNotes("assustada");

        when(petSearchRepository.findBySlug("lola-8f3a2c")).thenReturn(Optional.of(petSearch));

        mockMvc.perform(get("/api/v1/lola-8f3a2c")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("lola-8f3a2c"))
                .andExpect(jsonPath("$.pet.name").value("Lola"))
                .andExpect(jsonPath("$.pet.breed").value("SRD"))
                .andExpect(jsonPath("$.pet.color").value("preta"))
                .andExpect(jsonPath("$.pet.age").value(3))
                .andExpect(jsonPath("$.registeredBy.name").value("Jonas"))
                .andExpect(jsonPath("$.registeredBy.email").value("a@b.com"))
                .andExpect(jsonPath("$.registeredBy.phone").value("11999990000"))
                .andExpect(jsonPath("$.reporterRole").value("GUARDIAN"))
                .andExpect(jsonPath("$.location").value("Vila Sônia"))
                .andExpect(jsonPath("$.specialNeed").value("medicação"))
                .andExpect(jsonPath("$.additionalNotes").value("assustada"));
    }
}
