# AGCS - Aplicação de Gerenciamento de Chamados de Serviço

O **AGCS** é uma aplicação backend desenvolvida em Java + Spring Boot para gerenciamento de chamados (tickets), desenvolvida como parte de um desafio técnico para o processo seletivo **Analista IV – Desenvolvedor(a) back-end** do **CNI - Confederação Nacional da Indústria**. 

A API permite criar, listar e atualizar tickets, com validações, tratamento de erros, e suporte a monitoramento de saúde da aplicação.

## Justificativa da Tecnologia Escolhida

- **Spring Boot**: Escolhido por ser um framework robusto para APIs REST, com suporte nativo a JPA, validação e Actuator, facilitando o desenvolvimento rápido.

- **H2 In-Memory**: Banco leve e embutido, ideal para protótipos e testes, eliminando a necessidade de configuração externa.

- **Actuator**: Utilizado para monitoramento de saúde (`/api/health`), fornecendo status da aplicação e do banco.

- **JUnit 5 e Mockito**: Ferramentas padrão para testes unitários e de integração, permitindo simulação de dependências e validação isolada.

- **SLF4J**: Biblioteca de logging eficiente, integrada ao Spring Boot, para rastreamento de operações.

- **Spring Tool Suite 4 (STS4)**: IDE personalizada do Eclipse, especificamente adaptada para desenvolvimento com o ecossistema Spring.

## Organização do Repositório e Decisões de Design

O código-fonte está disponível no GitHub: [https://github.com/dryengalvao/agcs-service](https://github.com/dryengalvao/agcs-service).

### Estrutura do Repositório

O repositório foi organizado em pacotes modulares para garantir clareza e escalabilidade:

- **br.com.agcs.controller**: Contém os controladores REST (`TicketController`, `HealthController`) que expõem os endpoints da API.

- **br.com.agcs.service**: Contém a lógica de negócios (`TicketService`), separando a camada de controle da persistência.

- **br.com.agcs.repository**: Inclui a interface de persistência (`TicketRepository`) que estende JpaRepository para operações CRUD automáticas utilizando os recursos do JPA.

- **br.com.agcs.dto**: Define os Data Transfer Objects (`TicketDTOCreate`, `TicketDTOUpdate`, `TicketDTOResponse`) para validação e mapeamento de dados.

- **br.com.agcs.entity**: Contém a entidade Ticket, mapeada para a tabela tickets no banco.

- **br.com.agcs.exception**: Gerencia exceções personalizadas (`ApplicationException`, `TicketNotFoundException`) e o manipulador global (`GlobalExceptionHandler`) para melhor tratativa dos erros da aplicação.

- **br.com.agcs.integration**: Contém os testes de integração (`TicketControllerIntegrationTest`).

- **br.com.agcs.unit**: Contém os testes unitários (`TicketServiceTest`).


### Decisão de Nomes, Mensagens de Logs e Comentários

- Os nomes de variáveis, classes, métodos e pacotes foram escritos em inglês (ex.: `TicketController`, `ticketDTOCreate`, `save`), seguindo o padrão na comunidade de desenvolvimento de software e as convenções definidas pelo Spring (ex.: `JpaRepository`, `RestController`).

- As mensagens de logs e comentários nas classes foram mantidas em português para melhor entendimento e facilitar a depuração do código implementado.

## Configuração e Execução

### Pré-requisitos
- Java 17 ou superior.
- Maven 3.8.x ou superior.
- Git (para clonar o repositório).

### Passos para Configurar e Executar

**1 . Clone o Repositório Git**:

```bash
git clone https://github.com/dryengalvao/agcs-service.git
cd agcs-service
```

**2. Compile e Instale as Dependências**:

```bash
mvn clean install
```
	
**3 . Execute a Aplicação**:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em *http://localhost:8086*.

### Teste Unitário e Integração


#### Foram implementados os seguintes teste unitários:

- **saveTicket_Success()** : Testa a persistência de um ticket com dados válidos diretamente pela camada de repositório ou serviço, garantindo que ele seja salvo corretamente.

- **updateTicket_Success()** : Verifica se a atualização de um ticket existente com dados válidos é realizada com sucesso e retorna os dados atualizados.

- **listAllTickets_MultipleTickets_Success()** : Garante que múltiplos tickets salvos possam ser listados corretamente, validando a resposta da listagem.

#### Foram implementados os seguintes teste de integração:
- **createTicket_ReturnsCreatedTicket()** : Verifica se a criação de um ticket via endpoint retorna status 201 (Created) e o corpo com os dados esperados.

- **createTicket_WithBlankFields_ReturnsValidationErrors()** : Testa se a criação de um ticket com campos obrigatórios em branco retorna status 400 (Bad Request) e mensagens de validação apropriadas.


#### Para a execução dos testes execute o comando:

```bash
mvn test
```

### Acesse os Recursos

Para acessar o banco de dados H2 pelo console: *http://localhost:8086/h2-console* e preencha os campos:

- **JDBC URL**: jdbc:h2:mem:agcs_db
- **usuário**: sa, 
- **senha**: vazia

## Exemplos de Chamadas aos Endpoints

### 1. Criar um Ticket (POST /api/tickets)

**Chamada**:
```bash
  curl -X POST http://localhost:8086/api/tickets \
    -H "Content-Type: application/json" \
    -d '{
      "title": "Problema com impressora",
      "description": "Impressora não está funcionando",
      "category": "Hardware",
      "sentiment": null
    }'
```

**Resposta Esperada (HTTP 201)**:

```json
{
  "id": 1,
  "title": "Problema com impressora",
  "description": "Impressora não está funcionando",
  "category": "Hardware",
  "sentiment": "Não Informado",
  "createdAt": "2025-05-31T23:55:00.123456",
  "updatedAt": "2025-05-31T23:55:00.123456"
}

```
---
### 2. Listar Todos os Tickets (GET /api/tickets)

**Chamada**:
```bash
  curl -X GET http://localhost:8086/api/tickets
```

**Resposta Esperada (HTTP 200)**:

```json
  [
    {
      "id": 1,
      "title": "Problema com impressora",
      "description": "Impressora não está funcionando",
      "category": "Hardware",
      "sentiment": "Não Informado",
      "createdAt": "2025-05-31T23:55:00.123456",
      "updatedAt": "2025-05-31T23:55:00.123456"
    }
  ]
```
---
### 3. Atualizar um Ticket (PUT /api/tickets/{id})

**Chamada**:
```bash
  curl -X PUT http://localhost:8086/api/tickets/1 \
    -H "Content-Type: application/json" \
    -d '{
      "title": "Problema com impressora - Atualizado",
      "description": "Impressora ainda não funciona",
      "category": "Hardware"
    }'
```

**Resposta Esperada (HTTP 200)**:

```json
 {
  "id": 1,
  "title": "Problema com impressora - Atualizado",
  "description": "Impressora ainda não funciona",
  "category": "Hardware",
  "sentiment": "Não Informado",
  "createdAt": "2025-05-31T23:55:00.123456",
  "updatedAt": "2025-05-31T23:56:00.654321"
} 
```
---
### 4. Verificar o Status da Aplicação (GET /api/health)

**Chamada**:
```bash
	curl -X GET http://localhost:8086/api/health
```

**Resposta Esperada (HTTP 200)**:

```json
	{
	  "applicationStatus": "UP",
	  "databaseStatus": "UP"
	}
```


# Recursos Extras

## Executando a Aplicação em um Container Docker

Como um recurso adicional, o repositório do projeto inclui a possibilidade de subir a aplicação em um container Docker.

### Requisitos

Para executar a aplicação em um container Docker, os seguintes requisitos devem ser atendidos:

- **Docker**: Versão mais atualizada (Docker 24.x ou superior). Você pode verificar sua versão com `docker --version` e atualizar, se necessário, seguindo as instruções em [docker.com](https://docs.docker.com/engine/install/).

- **Sistema Operacional**: Máquina Linux (recomendado Ubuntu 22.04 ou superior, ou outra distribuição compatível). O script foi testado em ambientes Linux, mas pode funcionar em outros sistemas (ex.: macOS, Windows com WSL2) com ajustes.

- **Maven**: Necessário para compilar a aplicação localmente (versão 3.8.x ou superior). Verifique com a versão com `mvn --version`.

- **Git**: Para clonar o repositório.

- **Acesso à Internet**: Para baixar a imagem base `eclipse-temurin:17-jre-alpine` do Docker Hub e as dependências do Maven.

### Passo a Passo para Executar o Script

1. Com o repositório já baixado acesse a pasta do projeto.

2. Certifique-se de que o script `build-and-run.sh` tem permissões de execução:
```bash
chmod +x build-and-run.sh
```
3. Execute o Script `build-and-run.sh`:
```bash
./build-and-run.sh
```
O script automatiza todo o processo: executa os testes, compila a aplicação, constrói a imagem Docker e inicia o container.

Após a execução do script, a aplicação estará disponível em `http://localhost:8086`


### Gerenciamento do Container

O script exibe instruções para gerenciar o container ao final da execução:

- Logs do Container: `docker logs agcs-service-container`
- Parar o container: `docker stop agcs-service-container`
- Remover o container: `docker rm agcs-service-container`
- Remover a imagem: `docker rmi agcs-service:latest`
