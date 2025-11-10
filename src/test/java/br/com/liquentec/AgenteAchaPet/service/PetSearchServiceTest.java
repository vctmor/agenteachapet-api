package br.com.liquentec.AgenteAchaPet.service;

import br.com.liquentec.AgenteAchaPet.dto.PersonCreate;
import br.com.liquentec.AgenteAchaPet.dto.PetCreate;
import br.com.liquentec.AgenteAchaPet.dto.PetSearchCreateRequest;
import br.com.liquentec.AgenteAchaPet.dto.request.SearchCreate;
import br.com.liquentec.AgenteAchaPet.dto.response.CartazDTO;
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
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        service = new PetSearchService(
                petSearchRepository,
                petRepository,
                personRepository,
                petSearchMapper,
                personMapper);
    }

    @Test
    void getCartazBySlug_shouldMapEntityToDTO() {
        // arrange: monta Person, Pet e PetSearch manualmente
        Person reporter = new Person();
        reporter.setId(1L);
        reporter.setName("Jonas");
        reporter.setEmail("a@b.com");
        reporter.setPhone("11999990000");
        reporter.setRole(Role.REPORTER);

        Pet pet = new Pet();
        pet.setId(42L);
        pet.setName("Lola");
        pet.setPerson(reporter);

        PetSearch ps = new PetSearch();
        ps.setId(1L);
        ps.setPet(pet);
        ps.setRegisteredBy(reporter);
        ps.setSlug("lola-8f3a2c");

        // monta CartazDTO "na mão", sem builder
        CartazDTO.PetInfo petInfo = new CartazDTO.PetInfo();
        petInfo.setId(42L);
        petInfo.setPetName("Lola");

        CartazDTO.ReporterInfo reporterInfo = new CartazDTO.ReporterInfo();
        reporterInfo.setName("Jonas");
        reporterInfo.setPhone("11999990000");

        CartazDTO.SightingInfo sightingInfo = new CartazDTO.SightingInfo();
        sightingInfo.setLastSeenPlace("Vila Sônia");

        CartazDTO dto = new CartazDTO();
        dto.setSlug("lola-8f3a2c");
        dto.setPet(petInfo);
        dto.setReporter(reporterInfo);
        dto.setSighting(sightingInfo);

        when(petSearchRepository.findDetailedBySlug("lola-8f3a2c")).thenReturn(Optional.of(ps));
        when(petSearchMapper.toCartazDto(ps)).thenReturn(dto);

        // act
        CartazDTO out = service.getCartazBySlug("lola-8f3a2c");

        // assert
        assertNotNull(out);
        assertEquals("lola-8f3a2c", out.getSlug());
        assertNotNull(out.getPet());
        assertEquals(42L, out.getPet().getId());
        assertEquals("Lola", out.getPet().getPetName());
        verify(petSearchRepository).findDetailedBySlug("lola-8f3a2c");
        verify(petSearchMapper).toCartazDto(ps);
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

            CartazDTO result = service.registerFullSearch(form, mock(MultipartFile.class));

            assertThat(result).isEqualTo(dto);
        }
    }

    @Test
    void registerFullSearch_shouldThrowWhenEmailExists() throws IOException {

        PetSearchCreateRequest form = new PetSearchCreateRequest();
        PersonCreate personDTO = new PersonCreate();

        personDTO.setEmail("exists@email.com");
        form.setPerson(personDTO);

        Person mapped = new Person();
        when(personMapper.toEntity(personDTO)).thenReturn(mapped);
        when(personRepository.existsByEmail("exists@email.com")).thenReturn(true);

        assertThatThrownBy(() -> service.registerFullSearch(form, null))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Email já cadastrado");

        verify(personRepository).existsByEmail("exists@email.com");
        verify(personRepository, never()).save(any());
        verify(petRepository, never()).save(any());
        verify(petSearchRepository, never()).save(any());
    }

    @Test
    void registerFullSearch_shouldThrowEntityCreationExceptionWhenSaveReturnsNull() throws IOException {

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
    void registerFullSearch_shouldThrowMapperExceptionWhenMapperReturnsNull() throws IOException {

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

        when(petSearchRepository.findAllDetailed()).thenReturn(entities);
        when(petSearchMapper.toCartazDtoList(entities)).thenReturn(dtos);

        List<CartazDTO> result = service.listAll();

        assertThat(result).isEqualTo(dtos);
        verify(petSearchRepository).findAllDetailed();
        verify(petSearchMapper).toCartazDtoList(entities);
    }
}
