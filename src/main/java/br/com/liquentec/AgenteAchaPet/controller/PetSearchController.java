package br.com.liquentec.AgenteAchaPet.controller;

import java.io.IOException;

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

import br.com.liquentec.AgenteAchaPet.dto.PetSearchCompositeForm;
import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.service.PetSearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pet-searches")
@RequiredArgsConstructor
public class PetSearchController {

    private final PetSearchService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetSearchResponseDTO> register(
            @RequestPart("data") @Validated PetSearchCompositeForm compositeform,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerFullSearch(compositeform, photo));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] imageData = service.getPhotoById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // ou IMAGE_PNG, se for o caso
        headers.setContentLength(imageData.length);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}