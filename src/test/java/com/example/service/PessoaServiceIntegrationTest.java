package com.example.service;

import com.example.model.Pessoa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PessoaServiceIntegrationTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    void testListarTodas_Integration() {
        // Act
        List<Pessoa> pessoas = pessoaService.listarTodas();

        // Assert
        assertNotNull(pessoas);
        assertTrue(pessoas.size() > 0);
    }

    @Test
    void testBuscarPorId_Integration() {
        // Arrange
        List<Pessoa> pessoas = pessoaService.listarTodas();
        Long primeiroId = pessoas.get(0).getId();

        // Act
        Optional<Pessoa> pessoa = pessoaService.buscarPorId(primeiroId);

        // Assert
        assertTrue(pessoa.isPresent());
        assertEquals(primeiroId, pessoa.get().getId());
    }

    @Test
    void testBuscarPorTermo_Integration() {
        // Arrange
        List<Pessoa> todasPessoas = pessoaService.listarTodas();
        String nomeParaBuscar = todasPessoas.get(0).getNome().split(" ")[0]; // Primeiro nome

        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo(nomeParaBuscar);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.size() > 0);
        assertTrue(resultado.get(0).getNome().toLowerCase().contains(nomeParaBuscar.toLowerCase()));
    }

    @Test
    void testBuscarPorTermo_TermoInexistente_Integration() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("XYZ_INEXISTENTE");

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void testBuscarPorTermo_TermoVazio_Integration() {
        // Act
        List<Pessoa> resultado = pessoaService.buscarPorTermo("");

        // Assert
        assertNotNull(resultado);
        assertEquals(pessoaService.listarTodas().size(), resultado.size());
    }
}
