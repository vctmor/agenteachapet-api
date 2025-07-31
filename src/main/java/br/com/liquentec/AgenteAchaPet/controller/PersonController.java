package br.com.liquentec.AgenteAchaPet.controller;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import br.com.liquentec.AgenteAchaPet.dto.PersonDTO;
import br.com.liquentec.AgenteAchaPet.dto.PersonWithPetsDTO;
import br.com.liquentec.AgenteAchaPet.service.PersonService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping(value="/persons")
    public ResponseEntity<PersonDTO> addPersonOnly(@RequestBody @Valid PersonDTO personDTO) {

        PersonDTO saved = personService.personAdd(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping(value = "/withPets", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PersonDTO> addPersonAndPets(
            @RequestPart("personWithPetsDTO") PersonWithPetsDTO dto,
            @RequestPart("image") @Valid MultipartFile image) {

        PersonDTO saved = personService.addPersonWithPets(dto, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}
