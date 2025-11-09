Mantendo a essência do código e respeitando a clareza semântica em inglês, aqui está a reescrita completa:

---

### 📌 Nomeações em inglês

| Português                       | Inglês sugerido       | Justificativa semântica                         |
| ------------------------------- | --------------------- | ----------------------------------------------- |
| `Desaparecimento`               | `PetSearch`           | “Busca por pet” é mais descritivo que “Missing” |
| `ParticipacaoNoDesaparecimento` | `SearchParticipation` | Participação no processo de busca               |
| `PapelNoEvento`                 | `SearchRole`          | Papel no contexto da busca                      |

---

## 🔁 Código completo com nomes em inglês

---

### 1. `SearchRole.java`

```java
public enum SearchRole {
    GUARDIAN,
    REPORTER,
    VOLUNTEER,
    CARETAKER
}
```

---

### 2. `SearchParticipation.java`

```java
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchParticipation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Person person;

    @ManyToOne(optional = false)
    private PetSearch petSearch;

    @Enumerated(EnumType.STRING)
    private SearchRole role;
}
```

---

### 3. DTO `SearchParticipationDTO.java`

```java
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParticipationDTO {
    private Long personId;
    private Long petSearchId;
    private SearchRole role;
}
```

---

### 4. Mapper `SearchParticipationMapper.java`

```java
import org.springframework.stereotype.Component;

@Component
public class SearchParticipationMapper {

    public SearchParticipation toEntity(SearchParticipationDTO dto, Person person, PetSearch petSearch) {
        return SearchParticipation.builder()
                .person(person)
                .petSearch(petSearch)
                .role(dto.getRole())
                .build();
    }
}
```

---

### 5. Repository `SearchParticipationRepository.java`

```java
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchParticipationRepository extends JpaRepository<SearchParticipation, Long> {
    List<SearchParticipation> findByPetSearchId(Long petSearchId);
}
```

---

### 6. Service `SearchParticipationService.java`

```java
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchParticipationService {

    private final SearchParticipationRepository repository;
    private final PersonRepository personRepository;
    private final PetSearchRepository petSearchRepository;
    private final SearchParticipationMapper mapper;

    public SearchParticipation createParticipation(SearchParticipationDTO dto) {
        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        PetSearch petSearch = petSearchRepository.findById(dto.getPetSearchId())
                .orElseThrow(() -> new EntityNotFoundException("PetSearch not found"));

        SearchParticipation participation = mapper.toEntity(dto, person, petSearch);
        return repository.save(participation);
    }

    public List<SearchParticipation> listByPetSearch(Long petSearchId) {
        return repository.findByPetSearchId(petSearchId);
    }
}
```

---

### 7. Controller `SearchParticipationController.java`

```java
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search-participations")
@RequiredArgsConstructor
public class SearchParticipationController {

    private final SearchParticipationService service;

    @PostMapping
    public ResponseEntity<SearchParticipation> create(@RequestBody SearchParticipationDTO dto) {
        return ResponseEntity.ok(service.createParticipation(dto));
    }

    @GetMapping("/pet-search/{id}")
    public ResponseEntity<List<SearchParticipation>> getByPetSearch(@PathVariable Long id) {
        return ResponseEntity.ok(service.listByPetSearch(id));
    }
}
```

---

## ✅ Notas finais

* `PetSearch` indica o processo de busca ativa do pet.
* `SearchParticipation` representa uma instância de envolvimento.
* `SearchRole` define o papel da pessoa **neste** evento (sem conflitar com roles sistêmicos globais como `USER`, `ADMIN`).
* A semântica e arquitetura continuam alinhadas ao DDD.

Se quiser, posso adaptar esse modelo para ser usado com eventos de domínio, ou adicionar campos como `timestamp`, observações, ou auditoria de mudanças. Deseja continuar nessa direção?
