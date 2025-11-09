package br.com.liquentec.AgenteAchaPet.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

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
import br.com.liquentec.AgenteAchaPet.service.PetSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/pet-searches", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class PetSearchController {

    private final PetSearchService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CartazDTO> register(
            @Valid @RequestPart("data") PetSearchCreateRequest request,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {

        CartazDTO result = service.registerFullSearch(request, photo);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri() // /api/v1/pet-searches
                .path("/{slug}")
                .buildAndExpand(result.getSlug())
                .toUri();

           ResponseEntity<CartazDTO>     resp = ResponseEntity.created(location).body(result);
           System.out.println(">>>>>>>>>>>Retorno register: " + resp);
        return  resp; //ResponseEntity.created(location).body(result);
    }

    @GetMapping
    public ResponseEntity<List<CartazDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    // Foto: caminho certo, igual ao que seu front e mapper usam
    @GetMapping(value = "/{id}/photo", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        byte[] imageData = service.getPhotoById(id);
        return ResponseEntity.ok()
                .contentLength(imageData.length)
                .body(imageData);
    }

    // Recurso canônico por slug
    @GetMapping("/{slug}")
    public ResponseEntity<CartazDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(service.getCartazBySlug(slug));
    }
    
}
