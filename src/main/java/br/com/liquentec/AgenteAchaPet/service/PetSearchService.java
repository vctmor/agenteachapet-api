package br.com.liquentec.AgenteAchaPet.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.model.Role;
import br.com.liquentec.AgenteAchaPet.dto.PetSearchCreateRequest;
import br.com.liquentec.AgenteAchaPet.dto.request.SearchCreate;
import br.com.liquentec.AgenteAchaPet.dto.response.CartazDTO;
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
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public java.util.List<CartazDTO> listAll() {
        // use um findAllDetailed com fetch join; se ainda não tiver, usa findAll mesmo
        var entities = petSearchRepository.findAllDetailed();
        return petSearchMapper.toCartazDtoList(entities);
    }

    @Transactional
    public CartazDTO registerFullSearch(PetSearchCreateRequest request, MultipartFile photo) throws IOException {
 
        Person person = personMapper.toEntity(request.getPerson());

        if (person.getRole() == null) person.setRole(Role.REPORTER);

    if (personRepository.existsByEmail(request.getPerson().getEmail())){

        throw new BusinessException("Email já cadastrado");
    }
    // 1. Salvar a pessoa
    person = personRepository.save(personMapper.toEntity(request.getPerson()));
 
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
    search.setSpecialNeed(form.getSpecialNeed());

    System.out.println("slug service: " + search.getSlug());
 
    if (photo != null && !photo.isEmpty()) {

        pet.setPhoto(photo.getBytes());
        
    }   
    PetSearch saved = petSearchRepository.save(search);

    // Teste antes de mapear
    if (saved == null) {

        throw new EntityCreationException("Falha ao salvar busca");
    }

    CartazDTO dto = petSearchMapper.toCartazDto(saved);
   

    if (dto == null) {

        throw new MapperException("Falha ao mapear busca para DTO");
    }

    return dto;

}

    public CartazDTO register(SearchCreate form, MultipartFile photo) throws IOException {

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

        return petSearchMapper.toCartazDto(petSearchRepository.save(entity));
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

    @Transactional(readOnly = true)
    public CartazDTO getCartazBySlug(String slug){

        PetSearch entity = petSearchRepository.findDetailedBySlug(slug)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca via slug não encontrada"));

         CartazDTO dto = petSearchMapper.toCartazDto(entity);
        enrichPhotoUrl(dto, entity);

        return dto;

    }

    void enrichPhotoUrl(CartazDTO dto, PetSearch entity) {

        if (dto.getPet() == null) return;
        String url = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/api/v1/pet-searches/{id}/photo")
            .buildAndExpand(entity.getId()) // ou entity.getPet().getId(), se for assim
            .toUriString();
        dto.getPet().setPhotoUrl(url);
}

}

