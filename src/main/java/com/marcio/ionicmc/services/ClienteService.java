package com.marcio.ionicmc.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Cliente;
import com.marcio.ionicmc.repositories.ClienteRepository;
import com.marcio.ionicmc.services.exception.ObjectNotFoundException;

@Service // transforma a classe em um componente do Spring
public class ClienteService {

    private final ClienteRepository repo; // injeção de dependência

    // Construtor para injeção de dependência
    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente find(Integer id) {
        // Busca o id no repositório
        Optional<Cliente> obj = repo.findById(id);
        // Se não encontrar, lança uma exceção
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                // id que não foi encontrado
                "Objeto não encontrado: " + id
                // nome da classe que não foi encontrada
                        + ", Tipo: " + Cliente.class.getName()));
    }
}
