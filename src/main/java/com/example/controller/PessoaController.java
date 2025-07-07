package com.example.controller;

import com.example.model.Pessoa;
import com.example.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    /**
     * Endpoint para listar todas as pessoas
     * GET /api/pessoas
     */
    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> listarTodas() {
        List<Pessoa> pessoas = pessoaService.listarTodas();
        return ResponseEntity.ok(pessoas);
    }

    /**
     * Endpoint para buscar pessoa por ID
     * GET /api/pessoas/{id}
     */
    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaService.buscarPorId(id);
        
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para buscar pessoas por qualquer termo
     * GET /api/pessoas/buscar?termo=valor
     */
    @GetMapping("/pessoas/buscar")
    public ResponseEntity<List<Pessoa>> buscarPorTermo(@RequestParam(required = false) String termo) {
        List<Pessoa> pessoas = pessoaService.buscarPorTermo(termo);
        return ResponseEntity.ok(pessoas);
    }

    /**
     * Endpoint de healthcheck
     * GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", System.currentTimeMillis());
        health.put("service", "Personal Data API");
        health.put("version", "1.0.0");
        
        // Verifica se os dados estão carregados
        try {
            List<Pessoa> pessoas = pessoaService.listarTodas();
            health.put("totalPessoas", pessoas.size());
            health.put("dataLoaded", true);
        } catch (Exception e) {
            health.put("dataLoaded", false);
            health.put("error", e.getMessage());
            return ResponseEntity.status(503).body(health);
        }
        
        return ResponseEntity.ok(health);
    }
}
