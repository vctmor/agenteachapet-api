package br.com.liquentec.AgenteAchaPet.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    
    PetSearchResponseDTO toResponseDto(PetSearch entity);

    List<PetSearchResponseDTO> toDoList(List<PetSearch> entities);
}
