package com.marcio.ionicmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Categoria;
import com.marcio.ionicmc.domain.Produto;
import com.marcio.ionicmc.repositories.CategoriaRepository;
import com.marcio.ionicmc.repositories.ProdutoRepository;
import com.marcio.ionicmc.services.exception.ObjectNotFoundException;

@Service // transforma a classe em um componente do Spring
public class ProdutoService {

    private final ProdutoRepository repo; // injeção de dependência
    private CategoriaRepository categoriaRepository;

    // Construtor para injeção de dependência
    public ProdutoService(ProdutoRepository repo, CategoriaRepository categoriaRepository) {
        this.repo = repo;
        this.categoriaRepository = categoriaRepository;
    }

    public Produto find(Integer id) {
        // Busca o id no repositório
        Optional<Produto> obj = repo.findById(id);
        // Se não encontrar, lança uma exceção
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                    // id que não foi encontrado
                "Objeto não encontrado: " + id 
                // nome da classe que não foi encontrada
                + ", Tipo: " + Produto.class.getName()
            ));
    }

    public Page<Produto> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        // Cria o objeto de paginação
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        // Busca o id no repositório
        return repo.search(name, categorias, pageRequest);
    }
}
