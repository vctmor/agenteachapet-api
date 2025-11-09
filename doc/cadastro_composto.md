Para permitir o cadastro de uma nova busca de pet desaparecido **sem que `Pet` e `Person` existam previamente no banco**, você precisa alterar a lógica do endpoint e do service para que:

1. **Pet e Person sejam criados dinamicamente** com base nos dados do formulário (`PetSearchRequestForm`);
2. E **depois disso**, a entidade `PetSearch` (ou equivalente) seja criada com os dados corretos.

---

## ✅ Caminho mais limpo: usar um DTO que contenha os dados do Pet, da Pessoa e da Busca

Crie um novo DTO composto, por exemplo:

### `PetSearchCompositeForm.java`

```java
@Data
public class PetSearchCompositeForm {

    @Valid
    private PersonDTO person;

    @Valid
    private PetDTO pet;

    @Valid
    private PetSearchRequestForm search;

}
```

Esse DTO pode ser enviado via `@RequestPart("data")` e conter os dados completos.

---

## ✅ Atualize o controller

```java
@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<PetSearchResponseDTO> registerNewSearch(
        @RequestPart("data") @Validated PetSearchCompositeForm compositeForm,
        @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.registerFullSearch(compositeForm, photo));
}
```

---

## ✅ Atualize o service

No seu `PetSearchService`, você precisa:

1. Salvar `Person` com os dados do DTO;
2. Salvar o `Pet`, associando ao `Person` salvo;
3. Salvar o `PetSearch`, com referência ao `Pet` e ao `Person`.

Exemplo:

```java
@Transactional
public PetSearchResponseDTO registerFullSearch(PetSearchCompositeForm compositeForm, MultipartFile photo) throws IOException {
    
    // 1. Salvar a pessoa
    Person person = personRepository.save(personMapper.toEntity(compositeForm.getPerson()));

    // 2. Salvar o pet
    Pet pet = petMapper.toEntity(compositeForm.getPet());
    pet.setPerson(person);
    pet = petRepository.save(pet);

    // 3. Criar a busca
    PetSearchRequestForm form = compositeForm.getSearch();
    PetSearch search = new PetSearch();
    search.setPet(pet);
    search.setPerson(person);
    search.setLocation(form.getLocation());
    search.setDisappearanceDate(form.getDisappearanceDate());
    search.setAdditionalNotes(form.getAdditionalNotes());
    search.setReporterRole(form.getReporterRole());
    
    if (photo != null && !photo.isEmpty()) {
        search.setPhoto(photo.getBytes());
    }

    petSearchRepository.save(search);
    return petSearchMapper.toResponseDto(search);
}
```

---

## ✅ Exemplo de `curl`

```bash
curl -X POST http://localhost:8080/api/pet-searches \
  -H "Content-Type: multipart/form-data" \
  -F 'data={
      "person": {
          "name": "João da Silva",
          "email": "joao@email.com"
      },
      "pet": {
          "name": "Rex",
          "type": "Cachorro",
          "breed": "SRD",
          "age": 4
      },
      "search": {
          "reporterRole": "TUTOR",
          "disappearanceDate": "2024-05-27T14:30:00",
          "location": "Rua X, 123",
          "additionalNotes": "Sumiu perto da praça."
      }
  };type=application/json' \
  -F "photo=@/home/faunus/Downloads/deu.jpg"
```

---

## Alternativa (menos elegante)

Você poderia estender o próprio `PetSearchRequestForm` com campos de `PetDTO` e `PersonDTO`, mas isso viola o princípio da **responsabilidade única** do DTO. Melhor manter o DTO composto (`PetSearchCompositeForm`), para deixar clara a composição.

---

## ✅ Conclusão

* O problema ocorre porque a lógica atual exige entidades `Pet` e `Person` **já existentes**.
* A solução é permitir o envio de dados aninhados via um DTO composto (`PetSearchCompositeForm`), e no backend, salvar primeiro `Person`, depois `Pet`, e por fim a `PetSearch`.
* O `curl` precisa seguir o formato `multipart/form-data` com os dados JSON aninhados e a foto.

---

## 📚 Fontes

* [Baeldung – Map JSON to Java Object via Multipart](https://www.baeldung.com/spring-multipart-post-request-json)
* [Spring Documentation – @RequestPart](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestPart.html)
* [Effective DTO design – Microsoft Architecture Guide](https://learn.microsoft.com/en-us/azure/architecture/patterns/data-transfer-object)
