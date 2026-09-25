package com.marcio.ionicmc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marcio.ionicmc.domain.Cliente;
import com.marcio.ionicmc.domain.Pedido;

// aqui passamos o tipo da entidade e o tipo do id, Integer
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Transactional(readOnly = true)
    // garante que a transação seja somente de leitura
    Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
    
}