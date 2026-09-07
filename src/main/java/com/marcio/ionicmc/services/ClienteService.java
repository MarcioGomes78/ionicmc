package com.marcio.ionicmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Cliente;
import com.marcio.ionicmc.dto.ClienteDTO;
import com.marcio.ionicmc.repositories.ClienteRepository;
import com.marcio.ionicmc.services.exception.DataIntegrityException;
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

    public Cliente update(Cliente obj) {
        // Busca o id no repositório
        Cliente newObj = find(obj.getId());
        // Atualiza os dados do objeto
        updateData(newObj, obj);
        // Salva o objeto
        return repo.save(newObj);
    }

    //método auxiliar para atualizar os dados do objeto
    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
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
                "Não é possível excluir este cliente pois ele possui pedidos(entidades relacionadas)"
            );
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    //método para buscar categorias por páginação e ordenação
    public Page<Cliente> findPage(Integer page, Integer linePerPage, String orderBy, String direction) {
        //Criação do PageRequest com os parâmetros fornecidos
        PageRequest pageRequest = PageRequest.of(page, linePerPage, Direction.valueOf(direction), orderBy);
        //Retorna a página de categorias
        return repo.findAll(pageRequest);
    }

    //método para converter ClienteDTO para Cliente
    public Cliente fromDTO(ClienteDTO objDto) {
        //Criação do objeto Categoria com os dados do DTO
        return new Cliente(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
    }
}
