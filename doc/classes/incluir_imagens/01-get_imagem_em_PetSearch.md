Com base na sua estrutura e requisitos, a seguir está a **reescrita completa da feature `PetSearch`**, agora com:

* Persistência da imagem como `@Lob byte[] photo`;
* Upload via `multipart/form-data`;
* Conversão com `MultipartFile`;
* **Novo endpoint para recuperação da imagem:** `GET /api/pet-searches/{id}/image`.

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

    @Lob
    private byte[] photo;

    @Column(columnDefinition = "TEXT")
    private String additionalNotes;
}
```

---

## 🎭 2. Enum `SearchRole`

```java
public enum SearchRole {
    TUTOR,
    ANNOUNCER,
    VOLUNTEER,
    GUARDIAN
}
```

---

## 📤 3. DTOs

### PetSearchRequestForm

```java
@Data
public class PetSearchRequestForm {
    private Long petId;
    private Long personId;
    private SearchRole reporterRole;
    private LocalDateTime disappearanceDate;
    private String location;
    private String additionalNotes;
}
```

### PetSearchResponseDTO

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
    private byte[] photo;
    private String additionalNotes;
}
```

---

## 🔁 4. Mapper com MapStruct

```java
@Mapper(componentModel = "spring")
public interface PetSearchMapper {

    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "personId", source = "reportedBy.id")
    @Mapping(target = "petName", source = "pet.name")
    @Mapping(target = "personName", source = "reportedBy.name")
    PetSearchResponseDTO toDto(PetSearch entity);
}
```

---

## 🗃️ 5. Repository

```java
public interface PetSearchRepository extends JpaRepository<PetSearch, Long> {
    List<PetSearch> findByPetId(Long petId);
}
```

---

## 💼 6. Service

```java
@Service
@RequiredArgsConstructor
public class PetSearchService {

    private final PetSearchRepository repository;
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final PetSearchMapper mapper;

    public PetSearchResponseDTO register(PetSearchRequestForm form, MultipartFile photo) throws IOException {

        Pet pet = petRepository.findById(form.getPetId())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found"));

        Person person = personRepository.findById(form.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        PetSearch entity = PetSearch.builder()
                .pet(pet)
                .reportedBy(person)
                .reporterRole(form.getReporterRole())
                .disappearanceDate(form.getDisappearanceDate())
                .location(form.getLocation())
                .additionalNotes(form.getAdditionalNotes())
                .photo(photo != null ? photo.getBytes() : null)
                .build();

        return mapper.toDto(repository.save(entity));
    }

    public byte[] getPhotoById(Long id) {
        PetSearch search = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PetSearch not found"));
        if (search.getPhoto() == null) {
            throw new EntityNotFoundException("No photo available for this record");
        }
        return search.getPhoto();
    }
}
```

---

## 🌐 7. Controller

```java
@RestController
@RequestMapping("/api/pet-searches")
@RequiredArgsConstructor
public class PetSearchController {

    private final PetSearchService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetSearchResponseDTO> register(
            @RequestPart("data") @Valid PetSearchRequestForm form,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.register(form, photo));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] imageData = service.getPhotoById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // ou IMAGE_PNG, se for o caso
        headers.setContentLength(imageData.length);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
```

---

## ✅ Teste com Postman

### Upload:

* **POST** `/api/pet-searches`
* `multipart/form-data`

  * `data`: JSON da requisição
  * `photo`: arquivo de imagem

### Recuperação da imagem:

* **GET** `/api/pet-searches/{id}/image`
* Retorna: binário de imagem com header `Content-Type: image/jpeg`

---

## 📚 Fontes

* [Spring Multipart Upload](https://www.baeldung.com/spring-file-upload)
* [Hibernate LOB](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#basic-lob)
* [Spring ResponseEntity\<byte\[\]>](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
* Evans, Eric. *Domain-Driven Design*, Addison-Wesley, 2003.

---

Caso queira tratar a extensão e o tipo da imagem dinamicamente (por exemplo, detectar `png`, `jpeg`, etc.), posso expandir com esse tratamento. Deseja isso?
