package br.com.liquentec.AgenteAchaPet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.com.liquentec.AgenteAchaPet.dto.PetSearchCompositeForm;
import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.mapper.PetSearchMapper;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository;
import br.com.liquentec.AgenteAchaPet.service.PetSearchService;
import lombok.RequiredArgsConstructor;

@RestController
// @RequestMapping("/pet-searches")

@RequiredArgsConstructor
public class PetSearchController {

    private final PetSearchService service;
    private final PetSearchRepository petSearchRepository;
    
    @PostMapping(value = "/pet-searches", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetSearchResponseDTO> register(
            @RequestPart("data") @Validated PetSearchCompositeForm compositeform,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {

                System.out.println("Recebido: " + compositeform);
                if (photo != null) System.out.println("Foto: " + photo.getOriginalFilename());
            

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(service.registerFullSearch(compositeform, photo));
    }
   
    @GetMapping("/pet-searches")
    public ResponseEntity<List<PetSearchResponseDTO>> listAll(){

        List<PetSearchResponseDTO> result = service.listAll();

        return ResponseEntity.ok(result);
    }

    // @GetMapping("/pet-s")
    // public List<PetSearchResponseDTO> list() {

    //     // List<PetSearchResponseDTO> result = service.listAll();

    //     return service.listAll();
    // }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {

        byte[] imageData = (byte[]) service.getPhotoById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // ou IMAGE_PNG, se for o caso
        headers.setContentLength(imageData.length);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @GetMapping("/pet-searches/slug/{slug}")
    public ResponseEntity<PetSearchResponseDTO> getBySlug(@PathVariable String slug) {

    PetSearch search = petSearchRepository.findBySlug(slug)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jornada não encontrada"));
    
            
        return ResponseEntity.ok(PetSearchMapper.toDto(search));

    }




}