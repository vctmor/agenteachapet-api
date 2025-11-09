Com base no seu código original e nas boas práticas em aplicações Spring Boot com persistência de imagens como `@Lob`, **vou incluir duas das três sugestões anteriores**, pois fazem sentido neste contexto:

1. ✅ **Upload via `multipart/form-data` no controller**
2. ✅ **Conversão entre `MultipartFile` e `byte[]` com utilitários do Spring**
3. ❌ **Endpoint específico para recuperação da imagem** – esse ponto *não será incluído agora*, pois seu DTO já traz a imagem no corpo da resposta (como `byte[]`), o que pode ser suficiente neste estágio. Podemos incluir depois, caso queira tratá-la separadamente (como stream de imagem ou link público).

---

## 🔄 Atualizações no Código

### 1. Atualização do `Controller` para receber `MultipartFile`

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
}
```

---

### 2. Criar `PetSearchRequestForm` para receber `data` do JSON + imagem

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

---

### 3. Atualizar o `Service` para processar `MultipartFile`

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

        // Monta entidade com builder
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
}
```

---

### 4. Atualização do `DTO` de resposta (mantido)

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

### 🧪 Exemplo de requisição no Postman

**POST** `/api/pet-searches`
**Content-Type:** `multipart/form-data`
**Part 1 (JSON):**

* Key: `data`
* Value (como JSON):

```json
{
  "petId": 1,
  "personId": 2,
  "reporterRole": "TUTOR",
  "disappearanceDate": "2025-05-27T14:00:00",
  "location": "Rua das Flores, 123",
  "additionalNotes": "Pulou o muro e saiu correndo."
}
```

**Part 2 (File):**

* Key: `photo`
* Value: *selecionar arquivo de imagem*

---

## 📚 Fontes e referências

* [Spring MultipartFile docs](https://docs.spring.io/spring-framework/reference/web/webmvc/multipart.html)
* [Spring Boot file upload tutorial](https://www.baeldung.com/spring-file-upload)
* Evans, Eric. *Domain-Driven Design*, Addison-Wesley, 2003.
* Vernon, Vaughn. *Implementing DDD*, Addison-Wesley, 2013.
* [Hibernate LOB Handling](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#basic-lob)

---

Se quiser incluir a **recuperação da imagem como endpoint específico (`GET /{id}/image`)**, posso gerar isso também. Deseja isso agora?
