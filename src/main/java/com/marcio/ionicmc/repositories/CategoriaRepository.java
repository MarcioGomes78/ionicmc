package com.marcio.ionicmc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcio.ionicmc.domain.Categoria;

// aqui passamos o tipo da entidade e o tipo do id, Integer
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // A anotação @Repository já é inferida pelo Spring
}
