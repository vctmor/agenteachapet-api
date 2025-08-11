package br.com.liquentec.AgenteAchaPet.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Pet;
import br.com.liquentec.AgenteAchaPet.model.PetSearch;
import br.com.liquentec.AgenteAchaPet.model.Role;
import br.com.liquentec.AgenteAchaPet.model.SearchRole;

@DataJpaTest
public class PetSearchRepositoryTest {

        // List<PetSearch> findByPetId(Long petId);
        // Optional<PetSearch> findBySlug(String slug);

        @Autowired
        private PetSearchRepository petSearchRepository;

        @Autowired PetRepository petRepository;

        @Autowired
        private PersonRepository personRepository;

        @Test
        @DisplayName("should find PetSearch by petId")
        void shouldFindByPetId() {
           // Arrange: cria um tutor

           Person person = new Person();

           person.setName("Alice");
           person.setEmail("alice@email.com");
           person.setPhone("11999999999");
           person.setRole(Role.TUTOR);

           personRepository.save(person);


           // Arrange: cria um Pet
        Pet pet = new Pet();

        pet.setName("Rex");
        pet.setBreed("SRD");
        pet.setColor("Preto");
        pet.setAge(4);
        pet.setPerson(person);

        petRepository.save(pet);

        // Arrange: cria um PetSearch
        PetSearch search = new PetSearch();

        search.setPet(pet);
        search.setRegisteredBy(person);
        search.setReporterRole(SearchRole.BASTIAN);
        search.setDisappearanceDate(LocalDateTime.of(2024, 1, 1, 10, 0));
        search.setLocation("São Paulo");
        search.setSlug("rex-123");
        search.setSpecialNeed("Precisa de remédio");
        search.setAdditionalNotes("Muito assustado");

        petSearchRepository.save(search);

        // Act
        List<PetSearch> results = petSearchRepository.findByPetId(pet.getId());

       
        assertThat(results.get(0).getPet()).isNotNull();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getSlug()).isEqualTo("rex-123");



       }



}
