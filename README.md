# Desafio Backend - LEDS  
**Resolução do Desafio**


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

## Tecnologias Utilizadas

- **Java 17**  
- **Spring Boot 3.5.3**  
- **Spring Data JPA**  
- **PostgreSQL** 
- **Lombok** (para reduzir boilerplate)  
- **Springdoc OpenAPI** (Swagger para documentação da API)  
- **Mockito** (simulação de comportamentos em testes unitários)
- **Docker** (conteinização da Aplicação e do Banco de Dados)


---

## O que foi implementado

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
  - Código organizado e legível.  
- Padrão arquitetural **MVC** para organização e escalabilidade.
- **Testes unitários** e **Testes de Integração**
      comando para executar a pilha de testes via IDE ou comando:  
   ```bash
      mvn test
- implementacao com **SonarCloud** para análise automática da qualidade do código
      comando para executar a analise de forma manual via IDE ou comando:  
   ```bash
      mvn clean verify sonar:sonar -Dsonar.projectKey=pauulohfs_venhaparaoleds-backend -Dsonar.organization=pauulohfs -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=859752bd619861bc7eeff6c9535af99d18f4ca1b 

- Configuração com **GitHub Actions** para integração contínua (CI).

- Integração entre **SonarCloud** e **GitHub Actions**, de modo que toda vez que um push for realizado, a análise de qualidade de código do SonarCloud seja executada automaticamente, garantindo o monitoramento contínuo da saúde do código.


## Observações

- Ao rodar o Docker Compose, o banco de dados será automaticamente povoado com os dados contidos nos arquivos .txt presentes na pasta database-docker no momento da execução.
     'candidatos.txt' e 'concursos.txt' localizados em `desafio/database-docker` 
- Portanto, qualquer alteração nos arquivos candidatos.txt e concursos.txt antes de iniciar o compose será refletida no banco.
- Caso os arquivos não estejam presentes, a importação será ignorada, e o banco permanecerá vazio ou com os dados existentes.
- Se entrarmos na Pagina do Actions do Git em : https://github.com/pauulohfs/venhaparaoleds-backend/actions/runs/16032043562/job/45234768719 
  observaremos que na build antes de completar a tarefa  "Complete job", o projeto foi scaneado pelo SonarCloud na tarefa "SonarCloud Scan" 
- Para acessar a documentação da API acesse o link :  http://localhost:8080/swagger-ui/index.html com o projeto em execução


---

## Detalhes da implementação dos testes efetuados
- **Testes Unitarios:** 
  Nosso foco inicial foi desenvolver testes unitários para a camada de serviço, onde testamos a lógica de negócio isoladamente. Utilizamos o framework JUnit junto com Mockito para criar mocks das dependências, como os repositórios e outros serviços, permitindo testar os métodos do CandidateService sem precisar acessar o banco de dados real.
  Esses testes verificam se os métodos do serviço:
  Buscam candidatos por ID e CPF corretamente;
  Listam todos os candidatos;
  Salvam candidatos aplicando as regras de negócio esperadas;
  Tratam situações como candidatos inexistentes.

- **Teste de Integração:** 
  Após validar a camada de serviço, avançamos para testes de integração para garantir que a API REST está funcionando de ponta a ponta. Implementamos testes com o Spring Boot Test e  MockMvc para simular requisições HTTP aos endpoints do CandidateController.
  Nos testes de integração, cobrimos cenários importantes como:
  Listagem de todos os candidatos via endpoint GET /candidatos;
  Busca de candidato por ID via GET /candidatos/id/{id};
  Validação do retorno 404 quando o candidato não é encontrado;
  Criação de um novo candidato via POST /candidatos, verificando se o candidato é salvo corretamente e se a resposta HTTP é adequada.


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



### Pré-requisitos

- Java 17 instalado  
- Maven instalado  
- Docker instalado e rodando 

## Como executar
1. Com o Docker já rodando localmente, rode o **docker-composer.yaml** localizado em  `desafio -> database-docker -> docker-compose.yaml` 
  isso criará  2 conteiners, o do o banco de dados já configurado a também o serviço da API REST 
2. Rode o docker-compose via IDE ou comando:  
   ```bash
   cd desafio/database-docker docker-compose up --build
3. Alternativamente, você pode rodar o projeto diretamente pela sua IDE (como IntelliJ) sem usar Docker.
Basta dar Run no projeto Spring Boot (DesafioApplication.java) que o backend será iniciado normalmente, desde que o banco esteja disponível.
