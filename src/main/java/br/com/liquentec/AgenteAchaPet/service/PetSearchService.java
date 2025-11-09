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

    private final PetSearchRepository petSearchRepository;
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final PetSearchMapper petSearchMapper;
    private final PersonMapper personMapper;

    @Transactional(readOnly = true)
    public List<CartazDTO> listAll() {

        var entities = petSearchRepository.findAllDetailed(); // precisa existir no repo
        
        return petSearchMapper.toCartazDtoList(entities);
    }

    @Transactional
    public CartazDTO registerFullSearch(PetSearchCreateRequest request, MultipartFile photo) throws IOException {
        Person person = personMapper.toEntity(request.getPerson());
        if (person.getRole() == null)
            person.setRole(Role.REPORTER);

        if (personRepository.existsByEmail(request.getPerson().getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }
        person = personRepository.save(person); // não precisa remapear

        // Pet: defina a foto ANTES de salvar
        Pet pet = PetMapper.toEntity(request.getPet());
        if (photo != null && !photo.isEmpty()) {
            pet.setPhoto(photo.getBytes());
        }
        pet.setPerson(person);
        pet = petRepository.save(pet);

        // Busca
        SearchCreate form = request.getSearch();
        PetSearch search = new PetSearch();
        search.setPet(pet);
        search.setRegisteredBy(person);
        search.setLocation(form.getLocation());
        search.setDisappearanceDate(form.getDisappearanceDate());
        search.setAdditionalNotes(form.getAdditionalNotes());
        search.setReporterRole(form.getReporterRole());
        search.setSpecialNeed(form.getSpecialNeed());
        search.setSlug(SlugUtil.toSlug(search.getPet().getName())); // considere garantir unicidade

        PetSearch saved = petSearchRepository.save(search);
        if (saved == null)
            throw new EntityCreationException("Falha ao salvar busca");

        // MapStruct + @AfterMapping já preenche photoUrl
        CartazDTO dto = petSearchMapper.toCartazDto(saved);
        if (dto == null)
            throw new MapperException("Falha ao mapear busca para DTO");
        return dto;
    }

    public byte[] getPhotoById(Long id) {
        PetSearch ps = petSearchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PetSearch not found"));
        byte[] petPhoto = ps.getPet().getPhoto();
        if (petPhoto == null)
            throw new EntityNotFoundException("No photo available for this record");
        return petPhoto;
    }

    @Transactional(readOnly = true)
    public CartazDTO getCartazBySlug(String slug) {
        PetSearch entity = petSearchRepository.findDetailedBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Busca via slug não encontrada"));
        return petSearchMapper.toCartazDto(entity); // @AfterMapping já cuida do photoUrl
    }
}

