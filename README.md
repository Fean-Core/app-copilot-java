# Personal Data API

API Spring Boot para controle de dados pessoais fake com 4 endpoints principais.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.1
- Maven
- Jackson (para processamento JSON)

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── PersonalDataApiApplication.java
│   │   ├── controller/
│   │   │   └── PessoaController.java
│   │   ├── model/
│   │   │   ├── Pessoa.java
│   │   │   └── Endereco.java
│   │   └── service/
│   │       └── PessoaService.java
│   └── resources/
│       ├── application.yml
│       └── data/
│           └── pessoas.json
```

## Endpoints da API

### 1. Listar todas as pessoas
- **URL**: `GET /api/pessoas`
- **Descrição**: Retorna todas as pessoas cadastradas
- **Resposta**: Lista de objetos Pessoa

### 2. Buscar pessoa por ID
- **URL**: `GET /api/pessoas/{id}`
- **Descrição**: Busca uma pessoa específica pelo ID
- **Parâmetros**: 
  - `id` (path) - ID da pessoa
- **Resposta**: Objeto Pessoa ou 404 se não encontrado

### 3. Buscar por termo
- **URL**: `GET /api/pessoas/buscar?termo={valor}`
- **Descrição**: Busca pessoas por qualquer termo (nome, email, telefone, cidade, profissão, CPF, endereço)
- **Parâmetros**: 
  - `termo` (query, opcional) - Termo de busca
- **Resposta**: Lista de objetos Pessoa que correspondem ao termo

### 4. Health Check
- **URL**: `GET /api/health`
- **Descrição**: Verifica o status da aplicação
- **Resposta**: Objeto com informações de saúde da aplicação

## Modelo de Dados

### Pessoa
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "telefone": "(11) 99999-1234",
  "idade": 35,
  "cidade": "São Paulo",
  "profissao": "Desenvolvedor",
  "cpf": "123.456.789-01",
  "endereco": {
    "rua": "Rua das Flores, 123",
    "bairro": "Centro",
    "cep": "01234-567",
    "cidade": "São Paulo",
    "estado": "SP"
  }
}
```

## Como Executar

1. **Pré-requisitos**:
   - Java 17 ou superior
   - Maven 3.6 ou superior

2. **Executar a aplicação**:
   ```bash
   mvn spring-boot:run
   ```

3. **Verificar se está funcionando**:
   ```bash
   curl http://localhost:8080/api/health
   ```

## Exemplos de Uso

### Listar todas as pessoas
```bash
curl http://localhost:8080/api/pessoas
```

### Buscar pessoa por ID
```bash
curl http://localhost:8080/api/pessoas/1
```

### Buscar por termo
```bash
# Buscar por nome
curl "http://localhost:8080/api/pessoas/buscar?termo=João"

# Buscar por cidade
curl "http://localhost:8080/api/pessoas/buscar?termo=São Paulo"

# Buscar por profissão
curl "http://localhost:8080/api/pessoas/buscar?termo=Desenvolvedor"
```

### Health Check
```bash
curl http://localhost:8080/api/health
```

## Dados Mock

A aplicação utiliza dados fictícios armazenados no arquivo `src/main/resources/data/pessoas.json` com 8 pessoas cadastradas para demonstração.

## Configuração

A aplicação utiliza arquivo YAML (`application.yml`) para configuração, rodando por padrão na porta 8080.
