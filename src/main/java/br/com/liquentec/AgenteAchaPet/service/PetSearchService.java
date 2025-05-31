package br.com.liquentec.AgenteAchaPet.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.dto.PetSearchRequestForm;
import br.com.liquentec.AgenteAchaPet.dto.PetSearchResponseDTO;
// import br.com.liquentec.AgenteAchaPet.dto.PetSearchRequestForm;
// import br.com.liquentec.AgenteAchaPet.dto.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.mapper.PetSearchMapper;
import br.com.liquentec.AgenteAchaPet.repository.PersonRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetRepository;
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PetSearchService {

    private final PetSearchRepository repository;
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final PetSearchMapper mapper;

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

        return mapper.toDto(repository.save(entity));
    }

    public byte[] getPhotoById(Long id) {
        PetSearch search = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PetSearch not found"));
        if (search.getPhoto() == null) {
            throw new EntityNotFoundException("No photo available for this record");
        }
        return search.getPhoto();
    }
}
