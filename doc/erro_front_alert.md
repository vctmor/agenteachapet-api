Obrigado por compartilhar o trecho completo. Como os dados são **inseridos com sucesso no banco**, mas o front ainda exibe a mensagem de erro `"Erro ao cadastrar anúncio."`, isso indica que:

---

### ✅ O *fetch* funcionou parcialmente, mas o `response.ok` retornou `false`.

Ou seja, **o backend retornou status HTTP diferente de 2xx (200, 201 etc.)**, apesar de processar os dados corretamente.

---

## 🔍 Provável causa

O backend pode estar retornando:

* **HTTP 400 (Bad Request)** por erro de parsing parcial (como no caso do `LocalDateTime`);
* **HTTP 500 (Internal Server Error)** após o `commit`;
* **HTTP 201 (Created)** sem `body`, mas com `Content-Type` malformado;
* **Outros status** (ex: 204 No Content), que não são considerados `ok` pelo `fetch`.

---

## ✅ Como diagnosticar com precisão

Altere seu `try/catch` para imprimir o `status` e o `text` da resposta:

```js
try {
  const response = await fetch("http://localhost:8080/api/pet-searches", {
    method: "POST",
    body: formData,
    headers: {
      Accept: "application/json"
    }
  });

  const responseText = await response.text(); // <-- lê o corpo como texto

  if (!response.ok) {
    console.error("Status:", response.status);
    console.error("Body:", responseText);
    throw new Error("Erro ao cadastrar: " + responseText);
  }

  alert("Anúncio cadastrado com sucesso!");
} catch (error) {
  console.error(error);
  alert("Erro ao cadastrar anúncio.");
}
```

Isso vai mostrar no console:

* `Status: 400` (por exemplo)
* `Body: Cannot deserialize value...` (ou qualquer outro erro)

---

## ✅ Soluções possíveis (dependendo do resultado do log)

### 🔧 1. O status retornado não é 2xx

Se for `201`, `204` ou até `200` com algum `Content-Type` inesperado, você pode ajustar sua verificação:

```js
if (response.status >= 200 && response.status < 300) {
  alert("Anúncio cadastrado com sucesso!");
} else {
  console.error("Status:", response.status);
  throw new Error("Erro ao cadastrar");
}
```

---

### 🔧 2. O erro vem de `LocalDateTime` no backend

Mesmo que a inserção ocorra, o erro de *deserialização parcial* pode deixar o Jackson em um estado inconsistente e forçar a resposta com erro (HTTP 400), mesmo depois do commit no banco.

Neste caso, **corrigir o envio do `datetime` como `yyyy-MM-ddTHH:mm:ss` resolve isso** (como falamos antes).

---

### 🧪 3. Simular retorno correto no backend

Verifique o que sua API retorna. O ideal seria retornar:

```http
HTTP/1.1 201 Created
Content-Type: application/json
{
  "id": 123,
  "message": "Cadastro realizado com sucesso."
}
```

Caso o `@PostMapping` não esteja retornando um `ResponseEntity`, crie isso manualmente:

```java
@PostMapping
public ResponseEntity<?> create(@RequestPart("data") PetSearchDTO dto,
                                @RequestPart("photo") MultipartFile photo) {
    petSearchService.create(dto, photo);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Cadastro realizado com sucesso"));
}
```

---

## ✅ Conclusão

Seu frontend está correto, mas:

* Está tratando qualquer resposta não `ok` como erro, mesmo que o backend tenha inserido os dados;
* O backend provavelmente está retornando um status inadequado (`400`, `500`, `204`) ou está com parsing incompleto;
* A correção definitiva exige:

  * Corrigir o envio de `disappearanceDate` com hora;
  * Ajustar o tratamento da resposta no backend para garantir status `201` com `Content-Type: application/json`.

---

Se quiser, posso revisar seu `@PostMapping` no backend para garantir que a API esteja respondendo corretamente ao frontend. Deseja colar aqui esse trecho do controller?
