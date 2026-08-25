package com.marcio.ionicmc.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Categoria;
import com.marcio.ionicmc.repositories.CategoriaRepository;

@Service // transforma a classe em um componente do Spring
public class CategoriaService {

    private final CategoriaRepository repo; // injeção de dependência

    // Construtor para injeção de dependência
    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElse(null);
    }
}
