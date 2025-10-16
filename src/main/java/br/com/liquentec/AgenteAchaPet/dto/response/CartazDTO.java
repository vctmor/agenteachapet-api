// src/main/java/.../dto/response/CartazDTO.java
package br.com.liquentec.AgenteAchaPet.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartazDTO {
    private String slug;
    private PetInfo pet;
    private ReporterInfo reporter;
    private SightingInfo sighting;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
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
    @Builder
    public static class ReporterInfo {
        private String name;
        private String phone;
        private String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SightingInfo {
        private String lastSeenPlace;
        private LocalDateTime lastSeenAt; // mapeado de disappearanceDate
        private String notes;
        private String specialNeed;
    }
}
