# Desafio Backend - LEDS  
*Resolução do Desafio Backend para o time LEDS*  

---

## Descrição do Projeto

Este projeto é uma aplicação backend desenvolvida para gerenciar e realizar buscas relacionadas a concursos públicos e candidatos. A solução permite:

- Consultar concursos compatíveis com o perfil de um candidato pelo CPF.  
- Consultar candidatos compatíveis com um concurso pelo código do concurso.

O backend é estruturado utilizando o padrão arquitetural **MVC** (Model-View-Controller), com uma organização clara das camadas para facilitar manutenção e escalabilidade.

---

## Endpoints Requisitados

1. **Buscar concursos por CPF do candidato**  
   Retorna os órgãos, códigos e editais dos concursos que possuem vagas compatíveis com as profissões do candidato.

GET /candidatos/{cpf}


2. **Buscar candidatos por código do concurso**  
Retorna o nome, data de nascimento e CPF dos candidatos cujas profissões se encaixam nas vagas do concurso.

GET /concursos/candidatos/{codigoConcurso}


Além desses, o sistema oferece endpoints RESTful completos para gerenciamento de candidatos e concursos (CRUD).

---

## Funcionalidades Implementadas

- **Modelagem do domínio** com entidades `CandidateEntity` e `ContestEntity` usando JPA/Hibernate.  
- **DTOs e Mappers** para separar as entidades do modelo da representação externa na API.  
- **Camadas separadas** de Controller, Service e Repository para melhor organização e responsabilidade.  
- **Validações básicas** de dados e tratamento consistente de erros, incluindo:  
- Retorno HTTP 404 quando o recurso não é encontrado (ex: candidato ou concurso inexistente).  
- Retorno HTTP 409 para conflitos, como CPF duplicado ao cadastrar candidato.  
- **Documentação automática da API** com Swagger (via Springdoc OpenAPI), acessível em `/swagger-ui.html`.  
- **Banco de dados configurado** para PostgreSQL em produção e H2 para testes locais.  
- **Uso do Lombok** para reduzir código repetitivo (getters, setters, construtores, etc).  
- **Clean Code aplicado**:  
- Código claro e legível.  
- Métodos e classes com responsabilidades únicas.  
- Nomes autoexplicativos.  
- Estrutura modular.  
- **Configuração moderna** com Java 17 e Spring Boot 3.5.3.  

---

## Estrutura do Projeto

| Pacote         | Descrição                                    |
|----------------|----------------------------------------------|
| `entity`       | Modelos JPA que representam as tabelas no banco. |
| `dto`          | Objetos de transferência para entrada e saída da API. |
| `mapper`       | Classes responsáveis pela conversão entre entidades e DTOs. |
| `repository`   | Interfaces para acesso a dados, estendendo JPARepository. |
| `service`      | Camada de negócios, contendo regras e lógica. |
| `controller`   | Endpoints REST que expõem a API.              |
| `config`       | Configurações da aplicação e integrações.    |

---

## Tecnologias Utilizadas

- **Java 17**  
- **Spring Boot 3.5.3**  
- **Spring Data JPA**  
- **PostgreSQL** (Banco principal)  
- **H2 Database** (Banco em memória para testes locais)  
- **Lombok** (para reduzir boilerplate)  
- **Springdoc OpenAPI** (Swagger para documentação da API)  
- **Maven** (Gerenciador de dependências e build)  
- **Docker** (para containerização do banco de dados)  

---

## Como Executar o Projeto

### Pré-requisitos

- Java 17 instalado  
- Maven instalado  
- Docker instalado e rodando (para banco PostgreSQL)  

### Passo a passo

1. **Rodar banco PostgreSQL via Docker**  

Na pasta `database-docker` (ou pasta onde está o `docker-compose.yaml`), execute:

```bash
docker-compose up -d