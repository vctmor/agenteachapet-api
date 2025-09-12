package br.com.liquentec.AgenteAchaPet.controller;

import br.com.liquentec.AgenteAchaPet.dto.PersonCreate;
import br.com.liquentec.AgenteAchaPet.dto.PersonWithPetsDTO;
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository;
import br.com.liquentec.AgenteAchaPet.service.PersonService;
import br.com.liquentec.AgenteAchaPet.service.PetSearchService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(addFilters = false) // evita SecurityAutoConfiguration no slice
@Import(PersonControllerTest.TestConfig.class) // registra o mock de PersonService
class PersonControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PersonService personService;

    @TestConfiguration
    static class TestConfig {

        @Bean PersonService personService() {return Mockito.mock(PersonService.class);}
        @Bean PetSearchService petSearchService() { return Mockito.mock(PetSearchService.class);}
        @Bean PetSearchRepository petSearchRepository() { return Mockito.mock(PetSearchRepository.class);}
    }

    @Test
    void addPersonOnly_shouldReturnCreatedPerson() throws Exception {
        // Arrange
        PersonCreate request = new PersonCreate();
        
        request.setName("Alice");
        request.setEmail("alice@email.com");

        Mockito.when(personService.personAdd(any(PersonCreate.class)))
               .thenAnswer(inv -> inv.getArgument(0)); // ecoa o mesmo DTO

        // Act + Assert
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.name").value("Alice"))
               .andExpect(jsonPath("$.email").value("alice@email.com"));
    }

    @Test
    void addPersonAndPets_shouldReturnCreatedPerson() throws Exception {
        // Arrange
        PersonWithPetsDTO withPetsDTO = new PersonWithPetsDTO();

        PersonCreate response = new PersonCreate();
        response.setName("Bob");
        response.setEmail("bob@email.com");

        Mockito.when(personService.addPersonWithPets(any(PersonWithPetsDTO.class), any()))
               .thenReturn(response);

        MockMultipartFile dtoPart = new MockMultipartFile(
                "personWithPetsDTO",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(withPetsDTO)
        );

        MockMultipartFile imagePart = new MockMultipartFile(
                "image",
                "pet.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake image content".getBytes()
        );

        // Act + Assert
        mockMvc.perform(multipart("/api/withPets")
                        .file(dtoPart)
                        .file(imagePart))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name").value("Bob"))
               .andExpect(jsonPath("$.email").value("bob@email.com"));
    }
}
