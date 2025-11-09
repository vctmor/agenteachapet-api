A seguir, reescrevo integralmente a feature **Desaparecimento** com os nomes das classes e variáveis em inglês, mantendo coerência com a tradução já aplicada ao modelo `PetSearch` e às participações. A nomenclatura é adaptada para seguir boas práticas de clareza e domínio do inglês no contexto de modelagem orientada a domínio (DDD).

---

## ✅ **Resumo das mudanças de nomes**

| Nome original em português | Nome em inglês      | Justificativa semântica                        |
| -------------------------- | ------------------- | ---------------------------------------------- |
| `Desaparecimento`          | `PetSearch`         | Refere-se à busca ativa de um pet desaparecido |
| `PapelNoEvento`            | `SearchRole`        | Papel da pessoa na busca                       |
| `registradoPor`            | `reportedBy`        | Pessoa que registrou o desaparecimento         |
| `dataDesaparecimento`      | `disappearanceDate` | Data da ocorrência                             |
| `localizacao`              | `location`          | Onde o pet desapareceu                         |
| `fotoUrl`                  | `photoUrl`          | Link ou referência à imagem                    |
| `outrasConsideracoes`      | `additionalNotes`   | Texto livre                                    |

---

## 🧱 1. Entidade `PetSearch`

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Pet pet;

    @ManyToOne(optional = false)
    private Person reportedBy;

    @Enumerated(EnumType.STRING)
    private SearchRole reporterRole;

    private LocalDateTime disappearanceDate;

    private String location;

    private String photoUrl; // Can be a URL (e.g., to S3)

    @Column(columnDefinition = "TEXT")
    private String additionalNotes;
}
```

---

## 🎭 2. Enum `SearchRole`

```java
public enum SearchRole {
    GUARDIAN,
    REPORTER,
    VOLUNTEER,
    CARETAKER
}
```

---

## 📤 3. DTOs

### `PetSearchRequestDTO` (input)

```java
@Data
public class PetSearchRequestDTO {
    private Long petId;
    private Long personId;
    private SearchRole reporterRole;
    private LocalDateTime disappearanceDate;
    private String location;
    private String photoUrl;
    private String additionalNotes;
}
```

### `PetSearchResponseDTO` (output)

```java
@Data
public class PetSearchResponseDTO {
    private Long id;
    private Long petId;
    private Long personId;
    private String petName;
    private String personName;
    private SearchRole reporterRole;
    private LocalDateTime disappearanceDate;
    private String location;
    private String photoUrl;
    private String additionalNotes;
}
```

---

## 🔁 4. Mapper `PetSearchMapper` (com MapStruct)

```java
@Mapper(componentModel = "spring")
public interface PetSearchMapper {

    @Mapping(target = "pet.id", source = "petId")
    @Mapping(target = "reportedBy.id", source = "personId")
    PetSearch toEntity(PetSearchRequestDTO dto);

    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "personId", source = "reportedBy.id")
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "personName", source = "reportedBy.name")
    PetSearchResponseDTO toDto(PetSearch entity);
}
```

---

## 🗃️ 5. Repositório

```java
public interface PetSearchRepository extends JpaRepository<PetSearch, Long> {
    List<PetSearch> findByPetId(Long petId);
}
```

---

## 💼 6. Serviço `PetSearchService`

```java
@Service
@RequiredArgsConstructor
public class PetSearchService {

    private final PetSearchRepository repository;
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final PetSearchMapper mapper;

    public PetSearchResponseDTO register(PetSearchRequestDTO dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        Person person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        PetSearch search = mapper.toEntity(dto);
        search.setPet(pet);
        search.setReportedBy(person);

        PetSearch saved = repository.save(search);
        return mapper.toDto(saved);
    }
}
```

---

## 🌐 7. Controller `PetSearchController`

```java
@RestController
@RequestMapping("/api/pet-searches")
@RequiredArgsConstructor
public class PetSearchController {

    private final PetSearchService service;

    @PostMapping
    public ResponseEntity<PetSearchResponseDTO> register(@RequestBody @Valid PetSearchRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(dto));
    }
}
```

---

## 📌 Observações adicionais

* O campo `photoUrl` pode representar um link para imagem em S3, Firebase ou outro armazenamento. Para persistência binária, substitua por:

  ```java
  @Lob
  private byte[] photo;
  ```

* A distinção entre os papéis (`SearchRole`) e a role global de sistema (`Role`) da `Person` continua preservada.

* A abordagem está adequada à prática do DDD tático, encapsulando valor semântico no evento de busca.

---

## 📚 Referências

* Evans, Eric. *Domain-Driven Design: Tackling Complexity in the Heart of Software*. Addison-Wesley, 2003.
* Vernon, Vaughn. *Implementing Domain-Driven Design*. Addison-Wesley, 2013.
* Brandolini, Alberto. *Introducing Event Storming*. Leanpub, 2019.
* [MapStruct Documentation](https://mapstruct.org/)
* [Spring Boot Docs](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

---

Se desejar, posso:

* incluir persistência de imagens como `@Lob`,
* adicionar eventos de domínio (como `PetMissingEvent`),
* gerar testes automatizados ou contrato REST.

Gostaria de seguir para algum desses desdobramentos?
