package com.example.service;

import com.example.model.Pessoa;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private List<Pessoa> pessoas;
    private final ObjectMapper objectMapper;

    public PessoaService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        carregarDados();
    }

    private void carregarDados() {
        try {
            ClassPathResource resource = new ClassPathResource("data/pessoas.json");
            InputStream inputStream = resource.getInputStream();
            
            TypeReference<List<Pessoa>> typeReference = new TypeReference<List<Pessoa>>() {};
            pessoas = objectMapper.readValue(inputStream, typeReference);
            
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar dados do arquivo JSON", e);
        }
    }

    public List<Pessoa> listarTodas() {
        return pessoas;
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.getId().equals(id))
                .findFirst();
    }

    public List<Pessoa> buscarPorTermo(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return pessoas;
        }

        String termoLowerCase = termo.toLowerCase();
        
        return pessoas.stream()
                .filter(pessoa -> 
                    pessoa.getNome().toLowerCase().contains(termoLowerCase) ||
                    pessoa.getEmail().toLowerCase().contains(termoLowerCase) ||
                    pessoa.getTelefone().toLowerCase().contains(termoLowerCase) ||
                    pessoa.getCidade().toLowerCase().contains(termoLowerCase) ||
                    pessoa.getProfissao().toLowerCase().contains(termoLowerCase) ||
                    pessoa.getCpf().toLowerCase().contains(termoLowerCase) ||
                    (pessoa.getEndereco() != null && (
                        pessoa.getEndereco().getRua().toLowerCase().contains(termoLowerCase) ||
                        pessoa.getEndereco().getBairro().toLowerCase().contains(termoLowerCase) ||
                        pessoa.getEndereco().getCidade().toLowerCase().contains(termoLowerCase) ||
                        pessoa.getEndereco().getEstado().toLowerCase().contains(termoLowerCase)
                    ))
                )
                .collect(Collectors.toList());
    }
}
