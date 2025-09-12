package br.com.liquentec.AgenteAchaPet.service;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.dto.PetSearchCreateRequest;
import br.com.liquentec.AgenteAchaPet.dto.request.SearchCreate;
import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.exception.BusinessException;
import br.com.liquentec.AgenteAchaPet.exception.EntityCreationException;
import br.com.liquentec.AgenteAchaPet.exception.MapperException;
import br.com.liquentec.AgenteAchaPet.mapper.PersonMapper;
import br.com.liquentec.AgenteAchaPet.mapper.PetMapper;
import br.com.liquentec.AgenteAchaPet.mapper.PetSearchMapper;
import br.com.liquentec.AgenteAchaPet.repository.PersonRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository;
import br.com.liquentec.AgenteAchaPet.util.SlugUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor

public class PetSearchService {

    // private final PetSearch responseDTO;
    private final PetSearchRepository petSearchRepository;
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final PetSearchMapper petSearchMapper;
    private final PersonMapper personMapper;


    public List<PetSearchResponseDTO> listAll() {

        List<PetSearch> entities = petSearchRepository.findAll();

        return petSearchMapper.toDoList(entities);

    }

    @Transactional
    public PetSearchResponseDTO registerFullSearch(PetSearchCreateRequest request, MultipartFile photo) throws IOException {
 
    if (personRepository.existsByEmail(request.getPerson().getEmail())){

        throw new BusinessException("Email já cadastrado");
    }
    // 1. Salvar a pessoa
    Person person = personRepository.save(personMapper.toEntity(request.getPerson()));
 
    // 2. Salvar o pet
    Pet pet = PetMapper.toEntity(request.getPet());

    pet.setPerson(person);
    pet = petRepository.save(pet);
 
    // 3. Criar a busca
    SearchCreate form = request.getSearch();
    PetSearch search = new PetSearch();

    search.setPet(pet);
    search.setRegisteredBy(person);
    
    search.setLocation(form.getLocation());
    search.setDisappearanceDate(form.getDisappearanceDate());
    search.setAdditionalNotes(form.getAdditionalNotes());
    
    search.setReporterRole(form.getReporterRole());
    search.setSlug(SlugUtil.toSlug(search.getPet().getName()));
    // search.setSpecialNeed(form.getSpecialNeed());
 
    if (photo != null && !photo.isEmpty()) {

        pet.setPhoto(photo.getBytes());
        
    }   
    PetSearch saved = petSearchRepository.save(search);

    // Teste antes de mapear
    if (saved == null) {

        throw new EntityCreationException("Falha ao salvar busca");
    }

    PetSearchResponseDTO dto = petSearchMapper.toResponseDto(saved);

    if (dto == null) {

        throw new MapperException("Falha ao mapear busca para DTO");
    }

    return dto;

}

    public PetSearchResponseDTO register(SearchCreate form, MultipartFile photo) throws IOException {

        Pet pet = petRepository.findById(form.getPetId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        Person person = personRepository.findById(form.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        PetSearch entity = PetSearch.builder()
                .pet(pet)
                .registeredBy(person)
    
                .reporterRole(form.getReporterRole())
                .disappearanceDate(form.getDisappearanceDate())
                .location(form.getLocation())
                .additionalNotes(form.getAdditionalNotes())
                .specialNeed(form.getSpecialNeed())
                .build();

        return petSearchMapper.toResponseDto(petSearchRepository.save(entity));
    }

    public byte[] getPhotoById(Long id) {

       PetSearch compositeForm = petSearchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PetSearch not found"));
       
        byte[] petPhoto = compositeForm.getPet().getPhoto();
        if (petPhoto == null) {
            throw new EntityNotFoundException("No photo available for this record");
        }
        return petPhoto;
    }



}

