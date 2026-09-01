package com.marcio.ionicmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Categoria;
import com.marcio.ionicmc.repositories.CategoriaRepository;
import com.marcio.ionicmc.services.exception.DataIntegrityException;
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

    public Categoria insert(Categoria obj) {
        obj.setId(null); //garante que o objeto é novo
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        // Busca o id no repositório
        find(obj.getId());
        // Salva o objeto
        return repo.save(obj);
    }

    public void delete(Integer id) {
        // Busca o id no repositório
        find(id);
        try {
            // Deleta o objeto
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Lança uma exceção
            throw new DataIntegrityException(
                "Não é possível excluir uma categoria que possui produtos"
            );
        }
    }
}
