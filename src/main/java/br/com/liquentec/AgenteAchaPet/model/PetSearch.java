package br.com.liquentec.AgenteAchaPet.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetSearch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Pet pet;

    @ManyToOne(optional = false)
    private Person registeredBy;

    @Enumerated(EnumType.STRING)
    private Role reporterRole;

    
    private LocalDateTime disappearanceDate;
 
    private String location;

    @Column(unique = true)
    private String slug;

    // @Embedded
    // @AttributeOverrides({
    //     @AttributeOverride(name = "description", column = @Column(name = "special_need_description", length = 700))
    // })
    
    @Column(columnDefinition = "TEXT")
    private String specialNeed;
 
    // @Lob
    // private byte[] photo; // Pode ser uma URL para o armazenamento externo (como S3)
 
    @Column(columnDefinition = "TEXT")
    private String additionalNotes;
    
}
