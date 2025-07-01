# Desafio Backend - LEDS  
*Resolução do Desafio * 


## Endpoints Requisitados:

Desenvolvemos uma aplicação backend que realiza buscas relacionadas a concursos públicos e candidatos, conforme os requisitos:

1. Listar os **órgãos, códigos e editais dos concursos públicos** compatíveis com o perfil do candidato, tomando como base o **CPF** informado.
 Endpoint:  
   ```bash
   GET /candidados/{cpf}

2. Listar os **nomes, datas de nascimento e CPF** dos candidatos compatíveis com um concurso, tomando como base o **Código do Concurso**.
 Endpoint:  
   ```bash
   GET /concursos/candidados/{codigoConcurso}

---

## O que foi implementado
- **Ao iniciar o programa, ele pega os arquivos povoados de 'candidatos.txt' e 'concursos.txt' localizados em `desafio/database-docker`  e grava tudo no banco de dados
- **Modelagem das entidades** `Candidate` e `Contest` com persistência em banco via JPA.  
- Criação das **camadas Controller, Service, Repository, DTO, Mapper** seguindo o padrão MVC.  
- **Endpoints REST** para CRUD completo de candidatos e concursos.  
- **Busca específica** para listar concursos compatíveis a partir do CPF do candidato.  
- **Busca inversa** para listar candidatos que se encaixam em determinado concurso via código.  
- Integração com banco PostgreSQL   
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
- Projeto configurado para usar **Java 17** e Spring Boot 3.x. 
- impkementacao com **SonarCloud** para análise automática da qualidade do código
      comando para executar a analise de forma manual
   ```bash
      mvn clean verify sonar:sonar -Dsonar.projectKey=pauulohfs_venhaparaoleds-backend -Dsonar.organization=pauulohfs -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=859752bd619861bc7eeff6c9535af99d18f4ca1b 

- Configuração com GitHub Actions para integração contínua (CI).

-Integração entre SonarCloud e GitHub Actions, de modo que toda vez que um push for realizado, a análise de qualidade de código do SonarCloud seja executada automaticamente, garantindo o monitoramento contínuo da saúde do código.

Observação :  o "login=859752bd619861bc7eeff6c9535af99d18f4ca1b" é um token temporário para testes

## Observações

- Ao rodar o Docker Compose, o banco de dados será automaticamente povoado com os dados contidos nos arquivos .txt presentes na pasta database-docker no momento da execução.
- Portanto, qualquer alteração nos arquivos candidatos.txt e concursos.txt antes de iniciar o compose será refletida no banco.
- Caso os arquivos não estejam presentes, a importação será ignorada, e o banco permanecerá vazio ou com os dados existentes.






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

## Tecnologias Utilizadas

- **Java 17**  
- **Spring Boot 3.5.3**  
- **Spring Data JPA**  
- **PostgreSQL** (Banco principal)  
- **Lombok** (para reduzir boilerplate)  
- **Springdoc OpenAPI** (Swagger para documentação da API)  
- **Maven** (Gerenciador de dependências e build)  
- **Docker** (para containerização do banco de dados)  

---

## Como executar
### Pré-requisitos

- Java 17 instalado  
- Maven instalado  
- Docker instalado e rodando 

1. Com o Docker já rodando localmente, rode o docker-composer.yaml localizado em  `desafio -> database-docker -> docker-composer.yaml` 
  isso criará  2 conteiners, o do o banco de dados já configurado a também o serviço da API REST 
2. Rode o docker-compose via IDE ou comando:  
   ```bash
   cd desafio/database-docker
   docker-compose up --build
