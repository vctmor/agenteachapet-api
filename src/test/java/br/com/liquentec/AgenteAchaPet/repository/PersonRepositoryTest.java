package br.com.liquentec.AgenteAchaPet.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.liquentec.AgenteAchaPet.model.Person;
import br.com.liquentec.AgenteAchaPet.model.Role;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("Should return true if person exists by mail")
    void shouldReturnTrueWhenPersonExistsByEmail(){

        // Arrange
        Person person = new Person();

        person.setName("Alice");
        person.setEmail("alice@email.com");
        person.setPhone("11999999999");
        person.setRole(Role.TUTOR);

        personRepository.save(person);

        // act
        boolean exists = personRepository.existsByEmail("alice@email.com");

        // assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("should return false if person does not exist by email")
    void shouldReturnFalseWhenPersonDoesNotExistByEmail(){

        boolean exists = personRepository.existsByEmail("notfound@bol");

        assertThat(exists).isFalse();
    }

}
