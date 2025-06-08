package br.com.liquentec.AgenteAchaPet.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.dto.PetSearchCompositeForm;
import br.com.liquentec.AgenteAchaPet.dto.request.PetSearchRequestForm;
import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.mapper.PersonMapper;
import br.com.liquentec.AgenteAchaPet.mapper.PetMapper;
// import br.com.liquentec.AgenteAchaPet.dto.PetSearchRequestForm;
// import br.com.liquentec.AgenteAchaPet.dto.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.mapper.PetSearchMapper;
import br.com.liquentec.AgenteAchaPet.repository.PersonRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetSearchService {

    private final PetSearchRepository repository;
    private final PetRepository petRepository;
    private final PetSearchRepository petSearchRepository;
    private final PersonRepository personRepository;
    private final PetSearchMapper petSearchMapper;
    private final PetMapper petMapper;
    private final PersonMapper personMapper;

    @Transactional
public PetSearchResponseDTO registerFullSearch(PetSearchCompositeForm compositeForm, MultipartFile photo) throws IOException {
 
    // 1. Salvar a pessoa
    Person person = personRepository.save(personMapper.toEntity(compositeForm.getPerson()));
 
    // 2. Salvar o pet
    Pet pet = petMapper.toEntity(compositeForm.getPet());
    pet.setPerson(person);
    pet = petRepository.save(pet);
 
    // 3. Criar a busca
    PetSearchRequestForm form = compositeForm.getSearch();
    PetSearch search = new PetSearch();
    search.setPet(pet);
    search.setRegisteredBy(person);
    search.setLocation(form.getLocation());
    search.setDisappearanceDate(form.getDisappearanceDate());
    search.setAdditionalNotes(form.getAdditionalNotes());
    search.setReporterRole(form.getReporterRole());
 
    if (photo != null && !photo.isEmpty()) {
        search.setPhoto(photo.getBytes());
    }
 
    petSearchRepository.save(search);
    return petSearchMapper.toResponseDto(search);
}

    public PetSearchResponseDTO register(PetSearchRequestForm form, MultipartFile photo) throws IOException {

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
                .photo(photo != null ? photo.getBytes() : null)
                .build();

        return petSearchMapper.toResponseDto(repository.save(entity));
    }

    public byte[] getPhotoById(Long id) {
        PetSearch search = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PetSearch not found"));
        if (search.getPhoto() == null) {
            throw new EntityNotFoundException("No photo available for this record");
        }
        return search.getPhoto();
    }

    public List<PetSearchResponseDTO> listAll(){

        List<PetSearch> entities = repository.findAll();

        return petSearchMapper.toDoList(entities);
       
    }



}

