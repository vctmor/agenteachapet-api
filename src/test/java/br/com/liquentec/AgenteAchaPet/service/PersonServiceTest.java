package br.com.liquentec.AgenteAchaPet.service;

import br.com.liquentec.AgenteAchaPet.dto.PersonDTO;
import br.com.liquentec.AgenteAchaPet.dto.PersonWithPetsDTO;
import br.com.liquentec.AgenteAchaPet.dto.PetDTO;
import br.com.liquentec.AgenteAchaPet.exception.BusinessException;
import br.com.liquentec.AgenteAchaPet.mapper.PersonMapper;
import br.com.liquentec.AgenteAchaPet.mapper.PetMapper;
import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.repository.PersonRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    private PersonRepository personRepository;
    private PersonMapper personMapper;
    private PetRepository petRepository;
    private PersonService personService;

   @BeforeEach
   void setup() {

    personRepository = mock(PersonRepository.class);
    personMapper = mock(PersonMapper.class);
    petRepository = mock(PetRepository.class);
    personService = new PersonService(personRepository, personMapper, petRepository);
    }

    @Test
    void personAdd_shouldSavePerson_whenEmailNotExists() {

        // Arrange
        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmail("test@email.com");

        Person personEntity = new Person();
        Person savedPerson = new Person();

        when(personRepository.existsByEmail("test@email.com")).thenReturn(false);
        when(personMapper.toEntity(personDTO)).thenReturn(personEntity);
        when(personRepository.save(personEntity)).thenReturn(savedPerson);
        when(personMapper.toDto(savedPerson)).thenReturn(personDTO);

        // Act
        PersonDTO result = personService.personAdd(personDTO);

        // Assert
        assertThat(result).isEqualTo(personDTO);
        verify(personRepository).save(personEntity);
    }

     @Test
    void personAdd_shouldThrow_whenEmailExists() {

        PersonDTO personDTO = new PersonDTO();

        personDTO.setEmail("exists@email.com");

        when(personRepository.existsByEmail("exists@email.com")).thenReturn(true);

        assertThatThrownBy(() -> personService.personAdd(personDTO))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Email já cadastrado!");

        verify(personRepository, never()).save(any());
    }

    @Test
    void addPersonWithPets_shouldSavePersonAndPets_whenEmailNotExists() {

        // Arrange
        PersonWithPetsDTO dto = new PersonWithPetsDTO();

        dto.setEmail("owner@email.com");

        PetDTO petDTO = new PetDTO();

        dto.setPets(Collections.singletonList(petDTO));

        Person personEntity = new Person();
        Person savedPerson = new Person();

        savedPerson.setId(1L);

        Pet petEntity = new Pet();

        when(personRepository.existsByEmail("owner@email.com")).thenReturn(false);
        when(personMapper.pwpToEntity(dto)).thenReturn(personEntity);
        when(personRepository.save(personEntity)).thenReturn(savedPerson);
        mockStatic(PetMapper.class).when(() -> PetMapper.toEntity(petDTO)).thenReturn(petEntity);

        // Act
        PersonDTO returnedDto = new PersonDTO();
        when(personMapper.toDto(savedPerson)).thenReturn(returnedDto);

        PersonDTO result = personService.addPersonWithPets(dto, mock(MultipartFile.class));

        // Assert
        assertThat(result).isEqualTo(returnedDto);
        verify(petRepository).save(petEntity);
        assertThat(petEntity.getPerson()).isEqualTo(savedPerson);
    }

    @Test
    void addPersonWithPets_shouldThrow_whenEmailExists() {

        PersonWithPetsDTO dto = new PersonWithPetsDTO();
        
        dto.setEmail("exists@email.com");

        when(personRepository.existsByEmail("exists@email.com")).thenReturn(true);

        assertThatThrownBy(() -> personService.addPersonWithPets(dto, mock(MultipartFile.class)))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Email já cadastrado!");

        verify(personRepository, never()).save(any());
        verify(petRepository, never()).save(any());
    }

}
