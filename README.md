# Desafio Backend - LEDS  
*Bem-vindo!* 👋

Neste desafio, você terá a oportunidade de demonstrar suas habilidades no desenvolvimento backend para o laboratório LEDS.

---

## Contextualização

Desenvolvemos uma aplicação backend que realiza buscas relacionadas a concursos públicos e candidatos, conforme os requisitos:

1. Listar os **órgãos, códigos e editais dos concursos públicos** compatíveis com o perfil do candidato, tomando como base o **CPF** informado.  
2. Listar os **nomes, datas de nascimento e CPF** dos candidatos compatíveis com um concurso, tomando como base o **Código do Concurso**.

---

## O que foi implementado

- **Modelagem das entidades** `Candidate` e `Contest` com persistência em banco via JPA.  
- Criação das **camadas Controller, Service, Repository, DTO, Mapper** seguindo o padrão MVC.  
- **Endpoints REST** para CRUD completo de candidatos e concursos.  
- **Busca específica** para listar concursos compatíveis a partir do CPF do candidato.  
- **Busca inversa** para listar candidatos que se encaixam em determinado concurso via código.  
- Integração com banco PostgreSQL (com opção H2 para testes).  
- **Documentação API via Swagger/OpenAPI** com o springdoc-openapi.  
- Uso do **Lombok** para reduzir código boilerplate (getters, setters, construtores).  
- **Tratamento de erros** consistente nos endpoints:  
  - Retorno 404 quando `id` ou `CPF` não encontrados.  
  - Retorno 409 para CPF duplicado ao criar candidato.  
  - Mensagens claras para o cliente da API.  
- Aplicação de conceitos de **Clean Code**:  
  - Nomes claros e consistentes.  
  - Responsabilidades bem definidas (separação Controller, Service, Repository).  
  - DTOs e mappers para isolar entidades da camada API.  
  - Validação mínima nas camadas de serviço.  
  - Código organizado e legível.  
- Projeto configurado para usar **Java 17** e Spring Boot 3.x.  
- Padrão arquitetural **MVC** para organização e escalabilidade.

---

## Estrutura do projeto

- `entity`: Modelos JPA do domínio.  
- `dto`: Objetos de transferência usados na API.  
- `mapper`: Conversores entre DTOs e entidades.  
- `repository`: Interfaces JPA para acesso ao banco.  
- `service`: Lógica de negócio e regras.  
- `controller`: Endpoints REST.  
- `config` (opcional): Configurações da aplicação.

---

## Tecnologias

- Java 17  
- Spring Boot 3.5.3  
- Spring Data JPA  
- PostgreSQL / H2  
- Lombok  
- Springdoc OpenAPI (Swagger UI)  
- Maven  

---

## Como executar

1. Configure o banco PostgreSQL com as credenciais em `application.properties`.  
2. Rode o projeto via IDE ou comando:  
   ```bash
   mvn spring-boot:run
