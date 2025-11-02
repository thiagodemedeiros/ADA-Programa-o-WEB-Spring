# API de Cache - Pokémon

Este é um microserviço Spring Boot que consome a PokeAPI v2, armazena (cacheia) dados de Pokémon em um banco H2 em memória e expõe endpoints REST para gerenciamento.

## Requisitos

* Java 17
* Maven 3.x

## Como Rodar

O projeto pode ser executado usando o Maven Wrapper:

```bash
# No Linux/macOS
./mvnw spring-boot:run

# No Windows
./mvnw.cmd spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

O console do H2 estará disponível em:
`http://localhost:8080/h2-console`
**JDBC URL:** `jdbc:h2:mem:pokemondb`
**User:** `sa`
**Password:** (deixar em branco)

## Exemplos de `curl`

### 1. Cachear um Pokémon (Pikachu)

```bash
curl -X POST http://localhost:8080/api/pokemon/cache/pikachu
```
*(Também funciona por ID: `.../cache/25`)*

### 2. Listar Pokémon cacheados (paginado)

```bash
curl -X GET "http://localhost:8080/api/pokemon?page=0&size=5"
```

### 3. Obter detalhes de um Pokémon (pelo ID local)

*Assumindo que o Pikachu foi cacheado e recebeu o idLocal=1*
```bash
curl -X GET http://localhost:8080/api/pokemon/1
```

### 4. Buscar por tipo

```bash
curl -X GET "http://localhost:8080/api/pokemon/search?type=electric"
```

### 5. Favoritar um Pokémon

```bash
curl -X PATCH http://localhost:8080/api/pokemon/1/favorite \
-H "Content-Type: application/json" \
-d '{
    "favorite": true,
    "note": "Meu favorito!"
}'
```

### 6. Verificar Saúde da Aplicação

```bash
curl -X GET http://localhost:8080/actuator/health
```