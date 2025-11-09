Abaixo estão os testes unitários e de integração para a entidade `Person`, organizados conforme solicitado:

---

## 🧪 Testes para a entidade `Person`

> **Tecnologias utilizadas:**
>
> * JUnit 5
> * Spring Boot Test (para integração)
> * H2 Database (memória)
> * Lombok (já incluído)
> * `@DataJpaTest` para testes de repositório

---

### ✅ 1. Teste unitário: criação e leitura de atributos

```java
import net.amoreira.buscapet.model.Person;
import net.amoreira.buscapet.model.Role;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testCreateAndReadAttributes() {
        Person person = new Person();
        person.setId(1L);
        person.setName("Maria");
        person.setEmail("maria@email.com");
        person.setPhone("11999999999");
        person.setRole(Role.USER);

        assertEquals(1L, person.getId());
        assertEquals("Maria", person.getName());
        assertEquals("maria@email.com", person.getEmail());
        assertEquals("11999999999", person.getPhone());
        assertEquals(Role.USER, person.getRole());
    }

    @Test
    void testPetsRelationship() {
        Person person = new Person();
        assertNull(person.getPets());

        person.setPets(List.of());
        assertNotNull(person.getPets());
        assertTrue(person.getPets().isEmpty());
    }
}
```

---

### ✅ 2. Teste de integração com banco de dados

> Necessita: `PersonRepository`, `PetRepository`, e entidade `Pet`

```java
import net.amoreira.buscapet.model.Person;
import net.amoreira.buscapet.model.Pet;
import net.amoreira.buscapet.model.Role;
import net.amoreira.buscapet.repository.PersonRepository;
import net.amoreira.buscapet.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersonPersistenceTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PetRepository petRepository;

    @Test
    void testSaveAndLoadPerson() {
        Person person = new Person(null, "João", "joao@email.com", "11988887777", Role.ADMIN, null);
        Person saved = personRepository.save(person);

        assertThat(saved.getId()).isNotNull();

        Person loaded = personRepository.findById(saved.getId()).orElseThrow();
        assertThat(loaded.getName()).isEqualTo("João");
        assertThat(loaded.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    void testPersonPetRelationship() {
        Person person = new Person(null, "Carla", "carla@email.com", "11977776666", Role.USER, null);
        Person savedPerson = personRepository.save(person);

        Pet pet = new Pet();
        pet.setName("Totó");
        pet.setColor("Preto");
        pet.setBreed("Vira-lata");
        pet.setAge(2);
        pet.setPerson(savedPerson);

        petRepository.save(pet);

        Person fetched = personRepository.findById(savedPerson.getId()).orElseThrow();
        List<Pet> pets = fetched.getPets();

        assertThat(pets).isNotNull();
        assertThat(pets).hasSize(1);
        assertThat(pets.get(0).getName()).isEqualTo("Totó");
    }
}
```

---

### 🧩 Repositórios necessários

Crie os seguintes repositórios para os testes de integração funcionarem:

```java
package net.amoreira.buscapet.repository;

import net.amoreira.buscapet.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {}
```

```java
package net.amoreira.buscapet.repository;

import net.amoreira.buscapet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {}
```

---

### ✅ Conclusão

Esses testes garantem:

1. Que os **atributos e métodos básicos da classe** `Person` estão funcionando corretamente.
2. Que o **relacionamento com `Pet`** é persistido e carregado adequadamente.
3. Que é possível **salvar e recuperar dados do banco de dados (teste de integração com H2)**.

Se quiser, posso adaptar isso a um ambiente de teste mais completo com MockMvc, Testcontainers ou TestRestTemplate.

---

### 📚 Fontes complementares

* Spring Boot Docs: [https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
* Baeldung: ["Testing JPA Repositories with @DataJpaTest"](https://www.baeldung.com/spring-boot-testing)
* JUnit 5: [https://junit.org/junit5/](https://junit.org/junit5/)
* H2 Database: [https://www.h2database.com/html/main.html](https://www.h2database.com/html/main.html)
