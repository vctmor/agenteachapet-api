package br.com.liquentec.AgenteAchaPet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SpecialNeed {

    @Column(length = 700) 
    private String description;
    
}
