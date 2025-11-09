package br.com.liquentec.AgenteAchaPet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.dto.PersonCreate;
import br.com.liquentec.AgenteAchaPet.dto.PersonWithPetsDTO;

@Mapper(componentModel = "spring") // ou "default" se não usar Spring
public interface PersonMapper {

    // PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    // Mapeamento básico
    PersonCreate toDto(Person person);
    
    @Mapping(target = "role", defaultValue = "REPORTER")
    Person toEntity(PersonCreate dto);

    // Mapeamento de DTO com pets (não mapeando os pets ainda)
    Person pwpToEntity(PersonWithPetsDTO dto);
}
