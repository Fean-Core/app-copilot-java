# Testes da API Personal Data

## Comandos de teste usando curl

### 1. Health Check
```bash
curl http://localhost:8080/api/health
```

### 2. Listar todas as pessoas
```bash
curl http://localhost:8080/api/pessoas
```

### 3. Buscar pessoa por ID
```bash
# Pessoa existente
curl http://localhost:8080/api/pessoas/1

# Pessoa inexistente (retorna 404)
curl http://localhost:8080/api/pessoas/999
```

### 4. Buscar por termo
```bash
# Buscar por nome
curl "http://localhost:8080/api/pessoas/buscar?termo=Joao"
curl "http://localhost:8080/api/pessoas/buscar?termo=Maria"

# Buscar por profissão
curl "http://localhost:8080/api/pessoas/buscar?termo=Desenvolvedor"
curl "http://localhost:8080/api/pessoas/buscar?termo=Designer"
curl "http://localhost:8080/api/pessoas/buscar?termo=Medica"

# Buscar por cidade
curl "http://localhost:8080/api/pessoas/buscar?termo=Paulo"
curl "http://localhost:8080/api/pessoas/buscar?termo=Rio"

# Buscar por email
curl "http://localhost:8080/api/pessoas/buscar?termo=gmail"

# Buscar por telefone
curl "http://localhost:8080/api/pessoas/buscar?termo=11"

# Buscar sem termo (retorna todos)
curl "http://localhost:8080/api/pessoas/buscar"
```

## Respostas esperadas

### Health Check
```json
{
  "dataLoaded": true,
  "service": "Personal Data API",
  "totalPessoas": 8,
  "version": "1.0.0",
  "status": "UP",
  "timestamp": 1751903964577
}
```

### Buscar pessoa por ID (exemplo ID=1)
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

### Buscar pessoa inexistente
- Status HTTP: 404 Not Found
- Corpo vazio

### Buscar por termo
- Retorna array de pessoas que correspondem ao termo
- Busca é case-insensitive
- Busca em todos os campos: nome, email, telefone, cidade, profissão, CPF e endereço
