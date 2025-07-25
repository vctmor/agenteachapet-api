package br.com.liquentec.AgenteAchaPet.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;

@Mapper(componentModel = "spring")
public interface PetSearchMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "personId", source = "registeredBy.id") 
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "personName", source = "registeredBy.name")
    @Mapping(target = "email", source = "registeredBy.email")
    @Mapping(target = "phone", source = "registeredBy.phone")
    @Mapping(target = "name", source = "pet.name")
    @Mapping(target = "color", source = "pet.color")
    @Mapping(target = "breed", source = "pet.breed")
    @Mapping(target = "age", source = "pet.age")
    @Mapping(target = "reporterRole", source = "reporterRole")
    @Mapping(target = "specialNeed", source = "specialNeed")
    @Mapping(target = "disappearanceDate", source = "disappearanceDate")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "photo", source = "pet.photo")
    @Mapping(target = "additionalNotes", source = "additionalNotes")
    
    @Named("toResponseDto")
    PetSearchResponseDTO toResponseDto(PetSearch entity);

    @IterableMapping(qualifiedByName = "toDto")
    List<PetSearchResponseDTO> toDoList(List<PetSearch> entities);

    @Named("toDto")
    public static PetSearchResponseDTO toDto(PetSearch search) {

        PetSearchResponseDTO dto = new PetSearchResponseDTO();

        dto.setId(search.getId());
        dto.setPetId(search.getPet().getId());
        dto.setPersonId(search.getRegisteredBy().getId());
        dto.setPetName(search.getPet().getName());
        dto.setBreed(search.getPet().getBreed());
        dto.setColor(search.getPet().getColor());
        dto.setPersonName(search.getRegisteredBy().getName());
        dto.setReporterRole(search.getReporterRole());
        dto.setDisappearanceDate(search.getDisappearanceDate());
        dto.setLocation(search.getLocation());
        dto.setAdditionalNotes(search.getAdditionalNotes());
        dto.setSpecialNeed(search.getSpecialNeed());
        dto.setSlug(search.getSlug());

        // Se você quiser incluir a imagem (caso exista):
        dto.setPhoto(search.getPet().getPhoto()); // ajuste conforme o seu model

        return dto;
    }

}
