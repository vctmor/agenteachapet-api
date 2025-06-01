package br.com.liquentec.AgenteAchaPet.mapper;

import java.util.Base64;

import org.mapstruct.Mapper;

import br.com.liquentec.AgenteAchaPet.dto.PetDTO;
import br.com.liquentec.AgenteAchaPet.model.*;

@Mapper(componentModel = "spring")
public class PetMapper {

    public static Pet toEntity(PetDTO dto) {
        Pet pet = new Pet();
        pet.setId(dto.getId());
        pet.setName(dto.getName());
        pet.setColor(dto.getColor());
        pet.setBreed(dto.getBreed());
        pet.setAge(dto.getAge());

        if (dto.getPersonId() != null) {
            Person person = new Person();
            person.setId(dto.getPersonId());
            pet.setPerson(person);
        }

        if (dto.getImageBase64() != null) {
            pet.setImage(Base64.getDecoder().decode(dto.getImageBase64()));
        }

        return pet;
    }

    public static PetDTO toDTO(Pet pet) {
        String imageBase64 = null;
        if (pet.getImage() != null) {
            imageBase64 = Base64.getEncoder().encodeToString(pet.getImage());
        }

        return new PetDTO(
                pet.getId(),
                pet.getName(),
                pet.getColor(),
                pet.getBreed(),
                pet.getAge(),
                imageBase64,
                pet.getPerson() != null ? pet.getPerson().getId() : null);
    }

}
