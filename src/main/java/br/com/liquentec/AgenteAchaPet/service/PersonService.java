package br.com.liquentec.AgenteAchaPet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import br.com.liquentec.AgenteAchaPet.dto.PersonWithPetsDTO;
import br.com.liquentec.AgenteAchaPet.dto.PetDTO;
import br.com.liquentec.AgenteAchaPet.exception.BusinessException;
import br.com.liquentec.AgenteAchaPet.mapper.PersonMapper;
import br.com.liquentec.AgenteAchaPet.mapper.PetMapper;
import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.dto.PersonDTO;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.repository.PersonRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetRepository;

@Service
@RequiredArgsConstructor
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;
    @Autowired
    private final PersonMapper personMapper;
    @Autowired
    private final PetRepository petRepository;

    // public List<PersonDTO> listAll(){

    // return personRepository.findAll().stream()
    // .mzap(PersonMapper::toDTO).collect(Collectors.toList());
    // }
    
    public PersonDTO personAdd(PersonDTO personDTO) {

         if (personRepository.existsByEmail(personDTO.getEmail())) {

            throw new BusinessException("Email já cadastrado!");
    }

        Person person = personMapper.toEntity(personDTO);

        // (Opcional) verificação de dados, validação de role, email, etc.

        Person saved = personRepository.save(person);
        return personMapper.toDto(saved);
    }

    @Transactional
    public PersonDTO addPersonWithPets(PersonWithPetsDTO dto, MultipartFile image) {
        // Converte o DTO para a entidade usando o PersonMapper
        Person person = personMapper.pwpToEntity(dto);

        // Salva a pessoa primeiro
        Person savedPerson = personRepository.save(person);

        // Mapeia e salva os pets associados
        if (dto.getPets() != null && !dto.getPets().isEmpty()) {
            for (PetDTO petDTO : dto.getPets()) {
                Pet pet = PetMapper.toEntity(petDTO);
                pet.setPerson(savedPerson); // associa o pet à pessoa recém salva
                petRepository.save(pet);
            }
        }
        return personMapper.toDto(savedPerson);
    }

}
