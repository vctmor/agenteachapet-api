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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.liquentec.AgenteAchaPet.dto.PetSearchCreateRequest;
import br.com.liquentec.AgenteAchaPet.dto.response.CartazDTO;
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
    public ResponseEntity<CartazDTO> register(
            @Valid @RequestPart("data") PetSearchCreateRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {

        CartazDTO result = service.registerFullSearch(request, photo);

        

        // se quiser Location header: /cartaz/{slug}
        // URI location = URI.create("/cartaz/" + result.getSlug());
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequestUri() // /api/v1/pet-searches
            .path("/{slug}")
            .buildAndExpand(result.getSlug())
            .toUri();

        

        return ResponseEntity
                .created(location)
                .body(result);
    }
   
    @GetMapping(value = "/pet-searches")
    public ResponseEntity<List<CartazDTO>> listAll(){

        List<CartazDTO> result = service.listAll();

        return ResponseEntity.ok(result);
    }
            
    // substituir pelo de baixo
    
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
   
    // @GetMapping(path = "/{id}/photo", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    // public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
    //     byte[] imageData = service.getPhotoById(id);
    //     return ResponseEntity.ok().contentLength(imageData.length).body(imageData);
    // }

    // Recurso canônico por slug
    @GetMapping(value = "/{slug}")
    public ResponseEntity<CartazDTO> getBySlug(@Valid @PathVariable String slug) {

    // PetSearch search = petSearchRepository.findBySlug(slug)
    //         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jornada não encontrada"));
    
           
        return ResponseEntity.ok(service.getCartazBySlug(slug));

    }




}