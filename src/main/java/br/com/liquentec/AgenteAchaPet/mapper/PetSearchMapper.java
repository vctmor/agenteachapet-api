package br.com.liquentec.AgenteAchaPet.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.liquentec.AgenteAchaPet.dto.response.PetSearchResponseDTO;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;

@Mapper(componentModel = "spring")
public interface PetSearchMapper {

    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "personId", source = "registeredBy.id")
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "personName", source = "registeredBy.name")
    
    PetSearchResponseDTO toResponseDto(PetSearch entity);

    List<PetSearchResponseDTO> toDoList(List<PetSearch> entities);
}
