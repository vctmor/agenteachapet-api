# 🧭 AgenteAchaPet — API

## Visão Geral

O **AgenteAchaPet** é uma API backend desenvolvida em Java com Spring Boot, criada a partir de uma filosofia colaborativa. Mais do que uma solução técnica para encontrar pets desaparecidos, o projeto propõe uma rede baseada em **mutualismo, cooperação e empatia** — inspirada em exemplos da natureza, como os líquens e a relação do lobo-guará com a lobeira. Seu objetivo é fomentar conexões solidárias, promovendo o protagonismo da comunidade utilizadora e construindo pontes de apoio mútuo entre pessoas.

Desenvolvida com foco em **arquitetura limpa**, **Domain-Driven Design (DDD)** e **boas práticas de engenharia de software**, esta API prioriza a clareza do domínio e a separação de responsabilidades. A estrutura do projeto valoriza **programação não hierárquica** e colaboração genuína, inspirada em princípios de mutualismo e cooperação, refletidos tanto no código quanto na proposta social da plataforma.

Com **testes automatizados (TDD)** e **documentação aberta via Swagger**, a API assegura manutenibilidade, segurança e rápida integração com outros sistemas. Além de ser o núcleo para a busca de pets desaparecidos, a solução foi pensada para evoluir como uma rede social de apoio e solidariedade, promovendo impacto positivo e protagonismo da comunidade usuária.

---

## 📌 Principais Funcionalidades

* **Cadastro de Buscas Ativas**
* **Listagem pública de Buscas Ativas**
* **Cartaz digital compartilhável**


## Funcionalidades a serem desenvolvidas ou em desenvolvimento
* **Lista de pets encontrados**
* **Feed de buscas ativas (linha do tempo)**
* **Notificações para a comunidade**

---

## 🛠️ Funcionalidades do Sistema

* **TDD (unitários e de integração)**
* **API RESTful seguindo boas práticas**
* **Validações padronizadas com Bean Validation**
* **Documentação interativa via Swagger/OpenAPI**

### Em desenvolvimento...
* **Tratamento de erros padronizados e retornos amigáveis**

### Futuro não muito distante... 
* **Estrutura pronta para autenticação JWT**
* **Pronta para deploy em nuvem**

---

## ⚙️ Tecnologias & Boas Práticas

* **Java 21, Spring Boot 3+**
* **Banco de Dados: PostgreSQL**
* **Arquitetura: Camadas (Controller, Service, Repository, DTO, Mapper)**
* **Padrões RESTful**
* **TDD: Testes unitários e de integração com JUnit e Mockito**
* **DTOs e Mapeamento para clean code e segurança**
* **Validações automáticas e mensagens customizadas**
* **Documentação automática com Swagger**
* **CORS configurado para integração frontend**
* **Preparada para deploy em nuvem (Render, Railway, Vercel)**
* **Foco em princípios SOLID e SRP**

---

## 🚀 Como rodar o projeto localmente

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seuusuario/agenteachapet-api.git
   cd agenteachapet-api
   ```

2. **Configure o banco de dados:**

   * PostgreSQL 15+
   * Defina as variáveis no `application.properties` ou use Docker (veja [docker-compose.yml](./docker-compose.yml) se disponível)

3. **Instale dependências e rode a aplicação:**

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acesse a documentação Swagger:**

   * [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 📑 Documentação dos Endpoints

A documentação completa, exemplos de requisição/resposta e detalhes dos modelos estão disponíveis em [Swagger UI](https://agenteachapet.onrender.com/swagger-ui/index.html).Por estar na Render, a api acaba ficando em standby... Tenha paciência (: 

Principais recursos:

* `/api/pets` — CRUD de pets
* `/api/persons` — CRUD de tutores
* `/api/searches` — Registro de buscas

---

## 🧪 Testes

O projeto segue a filosofia **TDD**:

* Testes unitários (JUnit)
* Testes de integração (MockMvc)
* Cobertura dos fluxos principais e casos de erro

---

## 📦 Deploy & Sustentação

* API desenhada para deploy em serviços como **Render, Railway, Vercel**, com variáveis de ambiente e scripts para setup rápido.
* Pronta para uso em MVPs, provas de conceito ou produção controlada.

---

## 🤝 Filosofia & Impacto Social

Inspirado nas relações de **mutualismo** (líquen, lobo-guará/lobeira), o projeto propõe uma plataforma que estimula cooperação genuína e respeito à privacidade, com transparência sobre o uso de dados e foco em empatia.

---

## 👨‍💻 Sobre o Autor

Desenvolvedor em transição para tecnologia, por ora focado em backend (Java/Spring Boot), mas aberto a outras linguagens, arquitetura de APIs, testes automatizados e soluções sociais inovadoras.
Buscando oportunidades em **estágio backend ou fullstack** para atuar com propósito e crescer profissionalmente.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Perfil-blue)](https://www.linkedin.com/in/seu-perfil/)

---

## ✨ Contribua

Sugestões, issues e PRs são bem-vindos!
Vamos juntos tornar a busca por pets mais acolhedora e eficiente.

---

> "Cada busca é um esperançar. Ninguém solta a mão de ninguém."

