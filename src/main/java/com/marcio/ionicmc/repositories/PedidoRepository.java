package com.marcio.ionicmc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcio.ionicmc.domain.Pedido;

// aqui passamos o tipo da entidade e o tipo do id, Integer
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // A anotação @Repository já é inferida pelo Spring
}