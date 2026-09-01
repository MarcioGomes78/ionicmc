package com.marcio.ionicmc.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Pedido;
import com.marcio.ionicmc.repositories.PedidoRepository;
import com.marcio.ionicmc.services.exception.ObjectNotFoundException;

@Service // transforma a classe em um componente do Spring
public class PedidoService {

    private final PedidoRepository repo; // injeção de dependência

    // Construtor para injeção de dependência
    public PedidoService(PedidoRepository repo) {
        this.repo = repo;
    }

    public Pedido find(Integer id) {
        // Busca o id no repositório
        Optional<Pedido> obj = repo.findById(id);
        // Se não encontrar, lança uma exceção
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                    // id que não foi encontrado
                "Objeto não encontrado: " + id 
                // nome da classe que não foi encontrada
                + ", Tipo: " + Pedido.class.getName()
            ));
    }
}
