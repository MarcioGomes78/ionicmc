package com.marcio.ionicmc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marcio.ionicmc.domain.ItemPedido;
import com.marcio.ionicmc.domain.ItemPedidoPK;

// aqui passamos o tipo da entidade e o tipo do id
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {

    // A anotação @Repository já é inferida pelo Spring
}
