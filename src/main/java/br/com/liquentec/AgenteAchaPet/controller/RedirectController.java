package br.com.liquentec.AgenteAchaPet.controller;

import java.net.URI;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {

    @GetMapping("/")
    public ResponseEntity<Void> redirectToFrontend(){

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        
        headers.setLocation(URI.create("http://localhost:5173/"));
        // https://gold-eagle-793639.hostingersite.com/ 
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    
}
