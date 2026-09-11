package com.marcio.ionicmc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marcio.ionicmc.domain.Categoria;
import com.marcio.ionicmc.domain.Produto;

// aqui passamos o tipo da entidade e o tipo do id, Integer
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    // A anotação @Repository já é inferida pelo Spring

    @Transactional(readOnly=true)
    @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.name LIKE %:name% AND cat IN :categorias")
    Page<Produto> search(@Param("name") String name, @Param("categorias") List<Categoria> categorias, Pageable pageable);
}
