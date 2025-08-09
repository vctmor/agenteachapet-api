package br.com.liquentec.AgenteAchaPet.repository;

import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;



@DataJpaTest
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    @Autowired 
    private PersonRepository personRepository;

   @Test
   @DisplayName("should add petl no repository")
   void shouldAddPetInRepository(){

    // Arrange: cria um tutor
        Person owner = new Person();
        owner.setName("Alice");
        owner.setEmail("alice@email.com");
        owner.setPhone("11999999999");
        owner.setRole(Role.TUTOR);
        personRepository.save(owner);

       Pet pet = new Pet();

       pet.setName("Rex");
       pet.setAge(5);
       pet.setBreed("boxer");
       pet.setColor("azul");
       pet.setPerson(owner);

       Pet savedPet = petRepository.save(pet);

       // Act: busca no banco
       Optional<Pet> foundPet = petRepository.findById(savedPet.getId());

       Pet petFromDb = foundPet.get();
       // Assert
       org.assertj.core.api.Assertions.assertThat(foundPet).isPresent();
     
       org.assertj.core.api.Assertions.assertThat(petFromDb.getName()).isEqualTo("Rex");
       org.assertj.core.api.Assertions.assertThat(petFromDb.getPerson().getName()).isEqualTo("Alice");


    }


}
