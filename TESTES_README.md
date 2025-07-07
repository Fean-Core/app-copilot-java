# Testes Unitários - PessoaService

## Visão Geral
Este projeto contém testes unitários e de integração para a classe `PessoaService`, que é responsável por gerenciar operações relacionadas a pessoas no sistema.

## Estrutura de Testes

### 1. PessoaServiceUnitTest
Testes unitários que testam a lógica de negócio da classe `PessoaService` de forma isolada.

**Funcionalidades testadas:**
- `listarTodas()` - Lista todas as pessoas
- `buscarPorId()` - Busca pessoa por ID
- `buscarPorTermo()` - Busca pessoas por termo em diversos campos

**Casos de teste do método `buscarPorTermo()`:**
- Termo nulo
- Termo vazio
- Termo com apenas espaços
- Busca por nome (case-sensitive e case-insensitive)
- Busca por email
- Busca por telefone
- Busca por cidade
- Busca por profissão
- Busca por CPF
- Busca por campos do endereço (rua, bairro, cidade, estado)
- Busca por termo inexistente
- Busca por termo parcial
- Tratamento de pessoa sem endereço (null safety)
- Busca em múltiplos campos

### 2. PessoaServiceIntegrationTest
Testes de integração que testam a classe `PessoaService` em um contexto Spring Boot completo.

**Funcionalidades testadas:**
- Carregamento de dados do arquivo JSON
- Integração com o container Spring
- Comportamento end-to-end dos métodos principais

## Dados de Teste

Os testes utilizam dados fictícios que incluem:
- João Silva (São Paulo, SP) - Desenvolvedor
- Maria Santos (Rio de Janeiro, RJ) - Analista  
- Pedro Oliveira (Belo Horizonte, MG) - Gerente

Cada pessoa possui dados completos incluindo nome, email, telefone, idade, cidade, profissão, CPF e endereço.

## Executando os Testes

### Todos os testes:
```bash
mvn test
```

### Apenas testes unitários:
```bash
mvn test -Dtest=PessoaServiceUnitTest
```

### Apenas testes de integração:
```bash
mvn test -Dtest=PessoaServiceIntegrationTest
```

## Cobertura de Testes

Os testes cobrem:
- ✅ Todos os métodos públicos da classe `PessoaService`
- ✅ Cenários de sucesso e falha
- ✅ Casos extremos (null, vazio, inexistente)
- ✅ Tratamento de exceções
- ✅ Validação de dados
- ✅ Busca case-insensitive
- ✅ Busca em múltiplos campos
- ✅ Null safety (endereço nulo)

## Tecnologias Utilizadas

- **JUnit 5** - Framework de testes
- **Mockito** - Framework de mocks (para testes unitários)
- **Spring Boot Test** - Testes de integração
- **Reflection API** - Para injeção de dados de teste nos testes unitários

## Arquivos de Teste

- `src/test/java/com/example/service/PessoaServiceUnitTest.java` - Testes unitários
- `src/test/java/com/example/service/PessoaServiceIntegrationTest.java` - Testes de integração
- `src/test/resources/data/pessoas.json` - Dados de teste em JSON
- `src/test/resources/application-test.properties` - Configurações de teste

## Resultados dos Testes

Todos os testes estão passando com sucesso:
- **26 testes executados**
- **0 falhas**
- **0 erros**
- **0 ignorados**
