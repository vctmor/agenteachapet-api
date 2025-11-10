package br.com.liquentec.AgenteAchaPet.controller

import br.com.liquentec.AgenteAchaPet.dto.response.CartazDTO
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
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(PetSearchController::class)
@AutoConfigureMockMvc(addFilters = false)
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

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var petSearchService: PetSearchService

    @Test
    fun `register deve criar cartaz e retornar 201`() {
        // JSON coerente com o que o controller espera em @RequestPart("data")
        val jsonData = """
            {
              "person": {
                "name": "Alice",
                "email": "alice@example.com",
                "phone": "11999999999",
                "role": "TUTOR"
              },
              "pet": {
                "name": "Rex",
                "breed": "SRD",
                "color": "PRETO",
                "ageCategory": "ADULTO",
                "size": "MEDIO",
                "species": "CACHORRO"
              },
              "search": {
                "location": "Praça XYZ",
                "disappearanceDate": "2025-10-29T10:30:00",
                "additionalNotes": "Sumiu após fogos",
                "specialNeed": "Nenhuma"
              }
            }
        """.trimIndent()

        val dataPart = MockMultipartFile(
            "data",
            "",
            MediaType.APPLICATION_JSON_VALUE,
            jsonData.toByteArray()
        )

        val photoPart = MockMultipartFile(
            "photo",
            "pet.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "fake-image".toByteArray()
        )

        val dto = CartazDTO().apply {
            slug = "rex-123"
            // se CartazDTO tiver id, pode preencher aqui também
            // id = 123L
        }

        Mockito.`when`(petSearchService.registerFullSearch(any(), any()))
            .thenReturn(dto)

        mockMvc.perform(
            multipart("/api/v1/pet-searches")
                .file(dataPart)
                .file(photoPart)
                .characterEncoding("UTF-8")
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.slug").value("rex-123"))
    }

    @Test
    fun `listAll deve retornar 200 com lista de cartazes`() {
        val a = CartazDTO().apply { slug = "cartaz-1" }
        val b = CartazDTO().apply { slug = "cartaz-2" }

        Mockito.`when`(petSearchService.listAll())
            .thenReturn(listOf(a, b))

        mockMvc.perform(get("/api/v1/pet-searches"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].slug").value("cartaz-1"))
            .andExpect(jsonPath("$[1].slug").value("cartaz-2"))
    }

    @Test
    fun `getPhoto deve retornar bytes da imagem`() {
        val bytes = byteArrayOf(1, 2, 3)

        Mockito.`when`(petSearchService.getPhotoById(10L))
            .thenReturn(bytes)

        mockMvc.perform(get("/api/v1/pet-searches/{id}/photo", 10L))
            .andExpect(status().isOk)
            .andExpect(header().string("Content-Length", bytes.size.toString()))
            .andExpect(content().bytes(bytes))
    }

    @Test
    fun `getBySlug deve retornar cartaz quando encontrado`() {
        val dto = CartazDTO().apply {
            slug = "rex-999"
        }

        Mockito.`when`(petSearchService.getCartazBySlug("rex-999"))
            .thenReturn(dto)

        mockMvc.perform(get("/api/v1/pet-searches/{slug}", "rex-999"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.slug").value("rex-999"))
    }
}
