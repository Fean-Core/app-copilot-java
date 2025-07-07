package com.example.service;

import com.example.model.Endereco;
import com.example.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceUnitTest {

    private PessoaService pessoaService;
    private List<Pessoa> pessoasMock;

    @BeforeEach
    void setUp() throws Exception {
        pessoaService = new PessoaService(null);
        
        // Criando dados de teste
        Endereco endereco1 = new Endereco("Rua das Flores, 123", "Centro", "01234-567", "São Paulo", "SP");
        Endereco endereco2 = new Endereco("Avenida Copacabana, 456", "Copacabana", "22070-000", "Rio de Janeiro", "RJ");
        Endereco endereco3 = new Endereco("Rua da Liberdade, 789", "Savassi", "30112-000", "Belo Horizonte", "MG");

        Pessoa pessoa1 = new Pessoa(1L, "João Silva", "joao@email.com", "(11) 99999-9999", 30, "São Paulo", "Desenvolvedor", "123.456.789-00", endereco1);
        Pessoa pessoa2 = new Pessoa(2L, "Maria Santos", "maria@email.com", "(21) 88888-8888", 25, "Rio de Janeiro", "Analista", "987.654.321-00", endereco2);
        Pessoa pessoa3 = new Pessoa(3L, "Pedro Oliveira", "pedro@email.com", "(31) 77777-7777", 35, "Belo Horizonte", "Gerente", "456.789.123-00", endereco3);

        pessoasMock = List.of(pessoa1, pessoa2, pessoa3);
        
        // Injetando dados de teste diretamente no campo usando reflection
        Field pessoasField = PessoaService.class.getDeclaredField("pessoas");
        pessoasField.setAccessible(true);
        pessoasField.set(pessoaService, pessoasMock);
    }

    @Test
    void testListarTodas() {
        // Act
        List<Pessoa> resultado = pessoaService.listarTodas();

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
        assertEquals("Maria Santos", resultado.get(1).getNome());
        assertEquals("Pedro Oliveira", resultado.get(2).getNome());
    }

    @Test
    void testBuscarPorId_PessoaExistente() {
        // Act
        Optional<Pessoa> resultado = pessoaService.buscarPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("João Silva", resultado.get().getNome());
        assertEquals("joao@email.com", resultado.get().getEmail());
    }

    @Test
    void testBuscarPorId_PessoaInexistente() {
        // Act
        Optional<Pessoa> resultado = pessoaService.buscarPorId(99L);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    void testBuscarPorTermo_TermoNull() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo(null);

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
    }

    @Test
    void testBuscarPorTermo_TermoVazio() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("");

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
    }

    @Test
    void testBuscarPorTermo_TermoComEspacos() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("   ");

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
    }

    @Test
    void testBuscarPorTermo_PorNome() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("João");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorNome_CaseInsensitive() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("joão");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorEmail() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("maria@email.com");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Maria Santos", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorTelefone() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("(31) 77777-7777");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pedro Oliveira", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorCidade() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("São Paulo");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorProfissao() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("Desenvolvedor");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorCPF() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("123.456.789-00");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorEnderecoRua() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("Rua das Flores");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorEnderecoBairro() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("Copacabana");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Maria Santos", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorEnderecoCidade() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("Belo Horizonte");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pedro Oliveira", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PorEnderecoEstado() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("SP");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_TermoNaoEncontrado() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("Termo Inexistente");

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void testBuscarPorTermo_TermoParcial() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("Silva");

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
    }

    @Test
    void testBuscarPorTermo_PessoaSemEndereco() throws Exception {
        // Arrange - Adicionar uma pessoa sem endereço
        Pessoa pessoaSemEndereco = new Pessoa(4L, "Ana Costa", "ana@email.com", "(41) 66666-6666", 28, "Curitiba", "Designer", "111.222.333-44", null);
        List<Pessoa> pessoasComSemEndereco = List.of(pessoasMock.get(0), pessoasMock.get(1), pessoasMock.get(2), pessoaSemEndereco);
        
        // Injetar a nova lista
        Field pessoasField = PessoaService.class.getDeclaredField("pessoas");
        pessoasField.setAccessible(true);
        pessoasField.set(pessoaService, pessoasComSemEndereco);

        // Act & Assert - Verificar se não há NullPointerException
        assertDoesNotThrow(() -> {
            List<Pessoa> resultado = pessoaService.buscarPorTermo("XYZ_TERMO_INEXISTENTE");
            // Como "XYZ_TERMO_INEXISTENTE" não existe nos dados de teste, o resultado deve ser vazio
            assertEquals(0, resultado.size());
        });
    }

    @Test
    void testBuscarPorTermo_MultiplosCampos() {
        // Act - Buscar por termo que pode estar em múltiplos campos
        List<Pessoa> resultado = pessoaService.buscarPorTermo("email");

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.size()); // Todos têm "email" no campo email
    }
}
