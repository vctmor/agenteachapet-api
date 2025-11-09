A seguir, implemento a **feature "Desaparecimento"** considerando sua stack: Spring Boot com Lombok, MySQL, DTOs e mapeamento com MapStruct (ou equivalente). A feature inclui:

* Referência ao `Pet`
* Referência explícita à `Person` (autora do desaparecimento)
* Campo `papelNoEvento` (como `TUTOR`, `ANUNCIANTE`, etc.)
* Data e local do desaparecimento
* Foto (como `String` de URL ou base64, conforme a estrutura de persistência)
* Campo de observações livres: `outrasConsideracoes`

---

## 🧱 1. Entidade `Desaparecimento`

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Desaparecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Pet pet;

    @ManyToOne(optional = false)
    private Person registradoPor;

    @Enumerated(EnumType.STRING)
    private PapelNoEvento papelDaPessoa;

    private LocalDateTime dataDesaparecimento;

    private String localizacao;

    private String fotoUrl; // Pode ser uma URL para o armazenamento externo (como S3)

    @Column(columnDefinition = "TEXT")
    private String outrasConsideracoes;
}
```

---

## 🎭 2. Enum `PapelNoEvento`

```java
public enum PapelNoEvento {
    TUTOR,
    ANUNCIANTE,
    VOLUNTARIO,
    ACOLHEDOR
}
```

---

## 📤 3. DTOs

### `DesaparecimentoRequestDTO` (entrada)

```java
@Data
public class DesaparecimentoRequestDTO {
    private Long petId;
    private Long personId;
    private PapelNoEvento papelDaPessoa;
    private LocalDateTime dataDesaparecimento;
    private String localizacao;
    private String fotoUrl;
    private String outrasConsideracoes;
}
```

### `DesaparecimentoResponseDTO` (saída)

```java
@Data
public class DesaparecimentoResponseDTO {
    private Long id;
    private Long petId;
    private Long personId;
    private String nomePet;
    private String nomePessoa;
    private PapelNoEvento papelDaPessoa;
    private LocalDateTime dataDesaparecimento;
    private String localizacao;
    private String fotoUrl;
    private String outrasConsideracoes;
}
```

---

## 🔁 4. Mapper com MapStruct

```java
@Mapper(componentModel = "spring")
public interface DesaparecimentoMapper {

    @Mapping(target = "pet.id", source = "petId")
    @Mapping(target = "registradoPor.id", source = "personId")
    Desaparecimento toEntity(DesaparecimentoRequestDTO dto);

    @Mapping(target = "petId", source = "pet.id")
    @Mapping(target = "personId", source = "registradoPor.id")
    @Mapping(target = "nomePet", source = "pet.nome")
    @Mapping(target = "nomePessoa", source = "registradoPor.nome")
    DesaparecimentoResponseDTO toDto(Desaparecimento entity);
}
```

---

## 🗃️ 5. Repositório

```java
public interface DesaparecimentoRepository extends JpaRepository<Desaparecimento, Long> {
    List<Desaparecimento> findByPetId(Long petId);
}
```

---

## 💼 6. Service

```java
@Service
@RequiredArgsConstructor
public class DesaparecimentoService {

    private final DesaparecimentoRepository repository;
    private final PetRepository petRepository;
    private final PersonRepository personRepository;
    private final DesaparecimentoMapper mapper;

    public DesaparecimentoResponseDTO registrar(DesaparecimentoRequestDTO dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado"));

        Person pessoa = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        Desaparecimento desaparecimento = mapper.toEntity(dto);
        desaparecimento.setPet(pet);
        desaparecimento.setRegistradoPor(pessoa);

        Desaparecimento salvo = repository.save(desaparecimento);
        return mapper.toDto(salvo);
    }
}
```

---

## 🌐 7. Controller

```java
@RestController
@RequestMapping("/api/desaparecimentos")
@RequiredArgsConstructor
public class DesaparecimentoController {

    private final DesaparecimentoService service;

    @PostMapping
    public ResponseEntity<DesaparecimentoResponseDTO> registrar(@RequestBody @Valid DesaparecimentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
    }
}
```

---

## 📝 Observações

* O campo `fotoUrl` presume que você salva imagens em um bucket (como AWS S3) ou local acessível.
* Se quiser usar o próprio banco para armazenar a imagem em base64 ou binário, o campo muda para `@Lob byte[] foto;`.
* A modelagem explicita a autoria e o papel da `Person`, conforme discutido.

---

## 📚 Fontes e referências

* Evans, Eric. *Domain-Driven Design: Tackling Complexity in the Heart of Software*. Addison-Wesley, 2003.
* Vernon, Vaughn. *Implementing Domain-Driven Design*. Addison-Wesley, 2013.
* Brandolini, Alberto. *Introducing Event Storming*. Leanpub, 2019.
* [MapStruct Documentation](https://mapstruct.org/)
* [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

---

Se quiser, posso expandir com:

* Validações de negócio
* Upload de imagem (com armazenamento em disco ou S3)
* Registro de eventos de domínio (`PetDesaparecidoEvent`)

Deseja seguir para algum desses pontos?
