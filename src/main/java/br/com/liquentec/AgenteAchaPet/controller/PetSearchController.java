package br.com.liquentec.AgenteAchaPet.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import br.com.liquentec.AgenteAchaPet.dto.PetSearchCreateRequest;
import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.mapper.PetSearchMapper;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.repository.PetSearchRepository;
import br.com.liquentec.AgenteAchaPet.service.PetSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class PetSearchController {

    private final PetSearchService service;
    private final PetSearchRepository petSearchRepository;
    
    // @PostMapping(
    //     value = "/pet-searches",
    //     consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    // )
    @PostMapping(value = "/pet-searches", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetSearchResponseDTO> register(
            @Valid @RequestPart("data") PetSearchCreateRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        PetSearchResponseDTO result = service.registerFullSearch(request, photo);

        

        // se quiser Location header: /cartaz/{slug}
        URI location = URI.create("/cartaz/" + result.getSlug());

        

        return ResponseEntity
                .created(location)
                .body(result);
    }
   
    @GetMapping(value = "/pet-searches")
    public ResponseEntity<List<PetSearchResponseDTO>> listAll(){

        List<PetSearchResponseDTO> result = service.listAll();

        return ResponseEntity.ok(result);
    }

    // @GetMapping("/pet-searches/{id}/photo")
    // Foto servida em endpoint estável (cacheável se quiser)
    @GetMapping(value = "/{id}/photo", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> getPhoto(@Valid @PathVariable Long id) {

        byte[] imageData = (byte[]) service.getPhotoById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // ou IMAGE_PNG, se for o caso
        headers.setContentLength(imageData.length);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    // Recurso canônico por slug
    @GetMapping(value = "/{slug}")
    public ResponseEntity<PetSearchResponseDTO> getBySlug(@Valid @PathVariable String slug) {

    PetSearch search = petSearchRepository.findBySlug(slug)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jornada não encontrada"));
    
            System.out.println("slug getBySlug: " + search.getSlug());
        return ResponseEntity.ok(PetSearchMapper.toDto(search));

    }




}