package com.marcio.ionicmc.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Categoria;
import com.marcio.ionicmc.repositories.CategoriaRepository;
import com.marcio.ionicmc.services.exception.ObjectNotFoundException;

@Service // transforma a classe em um componente do Spring
public class CategoriaService {

    private final CategoriaRepository repo; // injeção de dependência

    // Construtor para injeção de dependência
    public CategoriaService(CategoriaRepository repo) {
        this.repo = repo;
    }

    public Categoria find(Integer id) {
        // Busca o id no repositório
        Optional<Categoria> obj = repo.findById(id);
        // Se não encontrar, lança uma exceção
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                    // id que não foi encontrado
                "Objeto não encontrado: " + id 
                // nome da classe que não foi encontrada
                + ", Tipo: " + Categoria.class.getName()
            ));
    }
}
