# Movie API

Este é um projeto de API REST para gerenciamento de filmes, utilizando Java, Spring, Swagger, Gradle e Docker para subir o container do MySQL.

## Como rodar o projeto

Siga as instruções abaixo para rodar o projeto localmente em sua máquina:

### Pré-requisitos

Certifique-se de ter os seguintes softwares instalados em seu ambiente de desenvolvimento:

- Docker: https://docs.docker.com/get-docker/
- Docker Compose: https://docs.docker.com/compose/install/
Navegue até o diretório do projeto clonado:

```sh
cd movie-api
```
Inicie o banco de dados MySQL utilizando o Docker Compose:

```sh
docker-compose up -d
```
Aguarde até que o banco de dados MySQL esteja em execução.

Compile e rode o projeto utilizando o Gradle:
```sh
./gradlew bootRun
```
A API estará disponível em http://localhost:8080/movie e a documentação do Swagger estará disponível em http://localhost:8080/swagger-ui.html.

Como usar a API
A API possui os seguintes endpoints:

- GET /movie/{id}: Retorna os detalhes de um filme pelo ID.
- GET /movie: Retorna uma lista de filmes com base nos parâmetros de busca.
- POST /movie: Registra um novo filme.
- PUT /movie/{id}: Atualiza os detalhes de um filme existente pelo ID.
- PATCH /movie/{id}/rating: Avalia um filme existente pelo ID.
- GET /movie/non-rated: Retorna uma lista de filmes não avaliados com base em uma lista de IDs de filmes.

Você pode utilizar ferramentas como o Postman ou o curl para testar os endpoints da API.
