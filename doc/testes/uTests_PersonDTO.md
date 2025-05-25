Abaixo segue o **teste unitário** para a classe `PersonDTO`, com foco em:

1. Criação do objeto via construtor.
2. Verificação dos *getters* e *setters*.
3. Teste de integridade com o `Role` (enum).

---

### ✅ **Teste Unitário: `PersonDTOTest.java`**

```java
import net.amoreira.buscapet.dto.PersonDTO;
import net.amoreira.buscapet.model.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonDTOTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        PersonDTO dto = new PersonDTO(1L, "Maria", "maria@email.com", "11999999999", Role.USER);

        assertEquals(1L, dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals("maria@email.com", dto.getEmail());
        assertEquals("11999999999", dto.getPhone());
        assertEquals(Role.USER, dto.getRole());
    }

    @Test
    void testSetters() {
        PersonDTO dto = new PersonDTO();

        dto.setId(2L);
        dto.setName("João");
        dto.setEmail("joao@email.com");
        dto.setPhone("11888888888");
        dto.setRole(Role.ADMIN);

        assertEquals(2L, dto.getId());
        assertEquals("João", dto.getName());
        assertEquals("joao@email.com", dto.getEmail());
        assertEquals("11888888888", dto.getPhone());
        assertEquals(Role.ADMIN, dto.getRole());
    }

    @Test
    void testNoArgsConstructor() {
        PersonDTO dto = new PersonDTO();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getEmail());
        assertNull(dto.getPhone());
        assertNull(dto.getRole());
    }
}
```

---

### ✅ Observações

* Esse teste cobre completamente uma classe `DTO` com Lombok, verificando o funcionamento de seus *getters*, *setters* e construtores.
* Como `DTO` não possui lógica de negócio, **testes mais complexos não são necessários**.

---

### 📚 Fontes complementares

* [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
* [Lombok Docs](https://projectlombok.org/features/Data)
* [Baeldung – Testing DTOs](https://www.baeldung.com/java-record-vs-dto#testing-java-dtos)

Se quiser estender isso para testes de mapeamento (por exemplo, DTO ↔ entidade), posso gerar os testes do mapper também.
