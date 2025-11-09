// src/main/java/.../dto/response/CartazDTO.java
package br.com.liquentec.AgenteAchaPet.dto.response;

import lombok.*;
import java.time.LocalDateTime;

import br.com.liquentec.AgenteAchaPet.model.Role;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CartazDTO {
    private String slug;
    private PetInfo pet;
    private ReporterInfo reporter;
    private SightingInfo sighting;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
  
    public static class PetInfo {
        private Long id;
        private String petName;
        private String color;
        private String breed;
        private Integer age;
        private String photoUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor

    public static class ReporterInfo {
        private String name;
        private String phone;
        private String email;
        private Role role;
        
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    
    public static class SightingInfo {
        private String lastSeenPlace;
        private LocalDateTime lastSeenAt; // mapeado de disappearanceDate
        private String notes;
        private String specialNeed;
        
    }
}
