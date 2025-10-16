package br.com.liquentec.AgenteAchaPet.service;

import br.com.liquentec.AgenteAchaPet.dto.PersonCreate;
import br.com.liquentec.AgenteAchaPet.dto.PetCreate;
import br.com.liquentec.AgenteAchaPet.dto.PetSearchCreateRequest;
import br.com.liquentec.AgenteAchaPet.dto.request.SearchCreate;
import br.com.liquentec.AgenteAchaPet.dto.response.CartazDTO;
import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.exception.BusinessException;
import br.com.liquentec.AgenteAchaPet.exception.EntityCreationException;
import br.com.liquentec.AgenteAchaPet.exception.MapperException;
import br.com.liquentec.AgenteAchaPet.mapper.PersonMapper;
import br.com.liquentec.AgenteAchaPet.mapper.PetMapper;
import br.com.liquentec.AgenteAchaPet.mapper.PetSearchMapper;
import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.model.Role;
import br.com.liquentec.AgenteAchaPet.repository.PersonRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository;
import br.com.liquentec.AgenteAchaPet.suport.EntityBuilders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PetSearchServiceTest {

    private PetSearchRepository petSearchRepository;
    private PetRepository petRepository;
    private PersonRepository personRepository;
    private PetSearchMapper petSearchMapper;
    private PersonMapper personMapper;
    private PetSearchService service;

    @BeforeEach
    void setUp() {

        petSearchRepository = mock(PetSearchRepository.class);
        petRepository = mock(PetRepository.class);
        personRepository = mock(PersonRepository.class);
        petSearchMapper = mock(PetSearchMapper.class);
        personMapper = mock(PersonMapper.class);

        service = new PetSearchService(petSearchRepository, petRepository, personRepository, petSearchMapper,
                personMapper);
    }

    @Test
  void getCartazBySlug_shouldMapEntityToDTO_andEnrichPhotoUrl() {
    // arrange
    Person r = EntityBuilders.person("Jonas", "a@b.com");
    Pet p = EntityBuilders.pet(r, "Lola");
    PetSearch ps = EntityBuilders.petSearch(p, r, "lola-8f3a2c");
    ps.setId(1L);

    CartazDTO dto = CartazDTO.builder()
      .slug("lola-8f3a2c")
      .pet(CartazDTO.PetInfo.builder().id(42L).petName("Lola").build())
      .reporter(CartazDTO.ReporterInfo.builder().name("Jonas").phone("11999990000").build())
      .sighting(CartazDTO.SightingInfo.builder().lastSeenPlace("Vila Sônia").build())
      .build();

    Mockito.when(petSearchRepository.findDetailedBySlug("lola-8f3a2c")).thenReturn(Optional.of(ps));
    Mockito.when(petSearchMapper.toCartazDto(ps)).thenReturn(dto);

    // act
    CartazDTO out = service.getCartazBySlug("lola-8f3a2c");

    // assert
    assertNotNull(out);
    assertEquals("lola-8f3a2c", out.getSlug());
    assertNotNull(out.getPet().getPhotoUrl(), "photoUrl deve ser preenchida no service");
    assertTrue(out.getPet().getPhotoUrl().contains("/api/v1/pet-searches/1/photo"));
    Mockito.verify(petSearchRepository).findDetailedBySlug("lola-8f3a2c");
    Mockito.verify(petSearchMapper).toCartazDto(ps);
  }

     @Test
    void registerFullSearch_shouldSavePersonPetAndSearch() throws IOException {

        PetSearchCreateRequest form = new PetSearchCreateRequest();
        PersonCreate personCreate = new PersonCreate();
        PetCreate petCreate = new PetCreate();

        personCreate.setEmail("test@email.com");
        personCreate.setName("Alice");

        petCreate.setName("Rex");

        form.setPerson(personCreate);
        form.setPet(petCreate);

        SearchCreate searchCreate = new SearchCreate();

        searchCreate.setLocation("SP");
        searchCreate.setReporterRole(Role.TUTOR);
        searchCreate.setDisappearanceDate(LocalDateTime.now());

        form.setSearch(searchCreate);

        Person personEntity = new Person();
        Pet petEntity = new Pet();
        PetSearch savedSearch = new PetSearch();
        CartazDTO dto = new CartazDTO();

        petEntity.setName("Rex");

        when(personRepository.existsByEmail("test@email.com")).thenReturn(false);
        when(personMapper.toEntity(personCreate)).thenReturn(personEntity);
        when(personRepository.save(personEntity)).thenReturn(personEntity);

        try (MockedStatic<PetMapper> mocked = mockStatic(PetMapper.class)) {
            mocked.when(() -> PetMapper.toEntity(any())).thenReturn(petEntity);
            when(petRepository.save(petEntity)).thenReturn(petEntity);
            when(petSearchRepository.save(any(PetSearch.class))).thenReturn(savedSearch);
            when(petSearchMapper.toCartazDto(savedSearch)).thenReturn(dto);

            PetSearchResponseDTO result = service.registerFullSearch(form, mock(MultipartFile.class));

            assertThat(result).isEqualTo(dto);
        }
    }

    @Test
    void registerFullSearch_shouldThrowWhenEmailExists() {

        PetSearchCreateRequest form = new PetSearchCreateRequest();
        PersonCreate personDTO = new PersonCreate();

        personDTO.setEmail("exists@email.com");
        form.setPerson(personDTO);

        when(personRepository.existsByEmail("exists@email.com")).thenReturn(true);

        assertThatThrownBy(() -> service.registerFullSearch(form, null))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Email já cadastrado");
    }

    @Test
    void registerFullSearch_shouldThrowEntityCreationExceptionWhenSaveReturnsNull() {

        PetSearchCreateRequest form = new PetSearchCreateRequest();

        form.setPerson(new PersonCreate());
        form.setPet(new PetCreate());
        form.setSearch(new SearchCreate());

        when(personRepository.existsByEmail(null)).thenReturn(false);
        when(personMapper.toEntity(any())).thenReturn(new Person());
        when(personRepository.save(any())).thenReturn(new Person());
        try (MockedStatic<PetMapper> mocked = mockStatic(PetMapper.class)) {
            mocked.when(() -> PetMapper.toEntity(any())).thenReturn(new Pet());
            when(petRepository.save(any())).thenReturn(new Pet());
            when(petSearchRepository.save(any())).thenReturn(null);

            assertThatThrownBy(() -> service.registerFullSearch(form, null))
                    .isInstanceOf(EntityCreationException.class)
                    .hasMessage("Falha ao salvar busca");
        }
    }
    
    @Test
    void registerFullSearch_shouldThrowMapperExceptionWhenMapperReturnsNull() {

        PetSearchCreateRequest form = new PetSearchCreateRequest();

        form.setPerson(new PersonCreate());
        form.setPet(new PetCreate());
        form.setSearch(new SearchCreate());

        when(personRepository.existsByEmail(null)).thenReturn(false);
        when(personMapper.toEntity(any())).thenReturn(new Person());
        when(personRepository.save(any())).thenReturn(new Person());
        
        try (MockedStatic<PetMapper> mocked = mockStatic(PetMapper.class)) {
            mocked.when(() -> PetMapper.toEntity(any())).thenReturn(new Pet());
            when(petRepository.save(any())).thenReturn(new Pet());
            when(petSearchRepository.save(any())).thenReturn(new PetSearch());
            when(petSearchMapper.toCartazDto(any())).thenReturn(null);

            assertThatThrownBy(() -> service.registerFullSearch(form, null))
                    .isInstanceOf(MapperException.class)
                    .hasMessage("Falha ao mapear busca para DTO");
        }
    }

    @Test
    void register_shouldSaveSearchWhenPetAndPersonExist() throws IOException {

        SearchCreate form = new SearchCreate();

        form.setPetId(1L);
        form.setPersonId(2L);

        Pet pet = new Pet();
        Person person = new Person();
        PetSearch savedSearch = new PetSearch();
        CartazDTO dto = new CartazDTO();

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(personRepository.findById(2L)).thenReturn(Optional.of(person));
        when(petSearchRepository.save(any())).thenReturn(savedSearch);
        when(petSearchMapper.toCartazDto(savedSearch)).thenReturn(dto);

        PetSearchResponseDTO result = service.register(form, null);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void register_shouldThrowWhenPetNotFound() {
        SearchCreate form = new SearchCreate();
        form.setPetId(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.register(form, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pet not found");
    }

    @Test
    void register_shouldThrowWhenPersonNotFound() {
        SearchCreate form = new SearchCreate();
        form.setPetId(1L);
        form.setPersonId(2L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(new Pet()));
        when(personRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.register(form, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Person not found");
    }

    @Test
    void getPhotoById_shouldReturnPhotoWhenExists() {
        byte[] photo = { 1, 2, 3 };
        Pet pet = new Pet();
        pet.setPhoto(photo);
        PetSearch search = new PetSearch();
        search.setPet(pet);

        when(petSearchRepository.findById(1L)).thenReturn(Optional.of(search));

        assertThat(service.getPhotoById(1L)).isEqualTo(photo);
    }

    @Test
    void getPhotoById_shouldThrowWhenNoPhoto() {
        Pet pet = new Pet();
        pet.setPhoto(null);
        PetSearch search = new PetSearch();
        search.setPet(pet);

        when(petSearchRepository.findById(1L)).thenReturn(Optional.of(search));

        assertThatThrownBy(() -> service.getPhotoById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No photo available for this record");
    }


    @Test
    void getPhotoById_shouldThrowWhenPetSearchNotFound() {
        when(petSearchRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getPhotoById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("PetSearch not found");
    }

    @Test
    void listAll_shouldReturnMappedList() {
        List<PetSearch> entities = Arrays.asList(new PetSearch(), new PetSearch());
        List<CartazDTO> dtos = Arrays.asList(new CartazDTO(), new CartazDTO());

        when(petSearchRepository.findAll()).thenReturn(entities);
        when(petSearchMapper.toCartazDto(entities)).thenReturn(dtos);

        List<PetSearchResponseDTO> result = service.listAll();

        assertThat(result).isEqualTo(dtos);
    }

}
