package br.com.liquentec.AgenteAchaPet.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.liquentec.AgenteAchaPet.dto.response.CartazDTO;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;

@Mapper(componentModel = "spring")
public interface PetSearchMapper {

    // item único
    @Mapping(target = "slug", source = "slug")

    // Pet
    @Mapping(target = "pet.id", source = "pet.id")
    @Mapping(target = "pet.petName", source = "pet.name")
    @Mapping(target = "pet.color", source = "pet.color")
    @Mapping(target = "pet.breed", source = "pet.breed")
    @Mapping(target = "pet.age", source = "pet.age")

    // Reporter
    @Mapping(target = "reporter.name", source = "registeredBy.name")
    @Mapping(target = "reporter.phone", source = "registeredBy.phone")
    @Mapping(target = "reporter.email", source = "registeredBy.email")

    // Sighting
    @Mapping(target = "sighting.lastSeenPlace", source = "location")
    @Mapping(target = "sighting.lastSeenAt", source = "disappearanceDate")
    @Mapping(target = "sighting.notes", source = "additionalNotes")
    @Mapping(target = "sighting.specialNeed", source = "specialNeed")
    CartazDTO toCartazDto(PetSearch entity);

    java.util.List<CartazDTO> toCartazDtoList(java.util.List<PetSearch> entities);

    @AfterMapping
    default void fillPhotoUrl(@MappingTarget CartazDTO dto, PetSearch entity) {
        if (dto == null || dto.getPet() == null)
            return;
        String url = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/pet-searches/{id}/photo")
                .buildAndExpand(entity.getId()) // id do PetSearch
                .toUriString();
        dto.getPet().setPhotoUrl(url);
    }
}
