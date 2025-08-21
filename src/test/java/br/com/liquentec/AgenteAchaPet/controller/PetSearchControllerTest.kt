package br.com.liquentec.AgenteAchaPet.controller

import br.com.liquentec.AgenteAchaPet.dto.PetSearchCompositeForm
import br.com.liquentec.AgenteAchaPet.dto.PersonDTO
import br.com.liquentec.AgenteAchaPet.dto.PetDTO
import br.com.liquentec.AgenteAchaPet.dto.request.PetSearchRequestForm
import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO
import br.com.liquentec.AgenteAchaPet.model.Pet
import br.com.liquentec.AgenteAchaPet.model.PetSearch
import br.com.liquentec.AgenteAchaPet.model.Person
import br.com.liquentec.AgenteAchaPet.model.Role
import br.com.liquentec.AgenteAchaPet.model.SearchRole
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository
import br.com.liquentec.AgenteAchaPet.service.PetSearchService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@WebMvcTest(PetSearchController::class)
@AutoConfigureMockMvc(addFilters = false) // desliga segurança no slice
@Import(PetSearchControllerTest.TestConfig::class) // registra beans mockados
@ImportAutoConfiguration(
    exclude = [
        SecurityAutoConfiguration::class,
        SecurityFilterAutoConfiguration::class,
        UserDetailsServiceAutoConfiguration::class,
        OAuth2ClientAutoConfiguration::class,
        OAuth2ResourceServerAutoConfiguration::class
    ]
)
class PetSearchControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var petSearchService: PetSearchService
    @Autowired lateinit var petSearchRepository: PetSearchRepository

    @TestConfiguration(proxyBeanMethods = false)
    class TestConfig {
        @Bean fun petSearchService(): PetSearchService = Mockito.mock(PetSearchService::class.java)
        @Bean fun petSearchRepository(): PetSearchRepository = Mockito.mock(PetSearchRepository::class.java)
    }

    @Test
    fun `register should create and return dto when multipart is valid`() {
        // Monte um form MÍNIMO VÁLIDO (ajuste conforme suas validações)
        val person = PersonDTO().apply {
            name = "Alice"
            email = "alice@email.com"
            phone = "11999999999"
            role = Role.TUTOR
        }
        val pet = PetDTO().apply {
            name = "Rex"
            breed = "SRD"
            color = "Preto"
            age = 3
        }
        val search = PetSearchRequestForm().apply {
            location = "SP"
            reporterRole = SearchRole.TUTOR
            disappearanceDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        }
        val form = PetSearchCompositeForm().apply {
            this.person = person
            this.pet = pet
            this.search = search
        }

        val dataPart = MockMultipartFile(
            "data", "", MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsBytes(form)
        )
        val photoPart = MockMultipartFile(
            "photo", "pet.jpg", MediaType.IMAGE_JPEG_VALUE, "img".toByteArray()
        )

        val dto = PetSearchResponseDTO().apply {
            id = 123L
            slug = "rex-123"
        }
        Mockito.`when`(petSearchService.registerFullSearch(any(), any())).thenReturn(dto)

        mockMvc.perform(
            multipart("/pet-searches")
                .file(dataPart)
                .file(photoPart)
                .characterEncoding("UTF-8")
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(123))
            .andExpect(jsonPath("$.slug").value("rex-123"))
    }

    @Test
    fun `listAll should return ok with dto list`() {
        val a = PetSearchResponseDTO().apply { id = 1L }
        val b = PetSearchResponseDTO().apply { id = 2L }
        Mockito.`when`(petSearchService.listAll()).thenReturn(listOf(a, b))

        mockMvc.perform(get("/pet-searches"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[1].id").value(2))
    }

    @Test
    fun `getImage should return bytes with headers`() {
        val bytes = byteArrayOf(1, 2, 3)
        Mockito.`when`(petSearchService.getPhotoById(10L)).thenReturn(bytes)

        mockMvc.perform(get("/10"))
            .andExpect(status().isOk)
            .andExpect(header().string("Content-Type", MediaType.IMAGE_JPEG_VALUE))
            .andExpect(header().string("Content-Length", bytes.size.toString()))
            .andExpect(content().bytes(bytes))
    }

    @Test
    fun `getBySlug should return dto when found`() {
        // Como o controller chama PetSearchMapper.toDto(search) (estático),
        // evite NPE preenchendo os campos essenciais:
        val pet = Pet().apply { id = 77L; name = "Rex" }
        val person = Person().apply { id = 55L; name = "Alice" }
        val search = PetSearch().apply {
            id = 999L
            this.pet = pet
            registeredBy = person
            slug = "rex-999"
        }

        Mockito.`when`(petSearchRepository.findBySlug("rex-999"))
            .thenReturn(Optional.of(search))

        mockMvc.perform(get("/pet-searches/slug/{slug}", "rex-999"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(999))
            .andExpect(jsonPath("$.petId").value(77))
            .andExpect(jsonPath("$.personId").value(55))
            .andExpect(jsonPath("$.slug").value("rex-999"))
    }

    @Test
    fun `getBySlug should return 404 when not found`() {
        Mockito.`when`(petSearchRepository.findBySlug("unknown"))
            .thenReturn(Optional.empty())

        mockMvc.perform(get("/pet-searches/slug/{slug}", "unknown"))
            .andExpect(status().isNotFound)
    }
}
