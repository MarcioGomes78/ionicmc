package com.marcio.ionicmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcio.ionicmc.domain.Cidade;
import com.marcio.ionicmc.domain.Cliente;
import com.marcio.ionicmc.domain.Endereco;
import com.marcio.ionicmc.domain.enums.TipoCliente;
import com.marcio.ionicmc.dto.ClienteDTO;
import com.marcio.ionicmc.dto.ClienteNewDTO;
import com.marcio.ionicmc.repositories.CidadeRepository;
import com.marcio.ionicmc.repositories.ClienteRepository;
import com.marcio.ionicmc.repositories.EnderecoRepository;
import com.marcio.ionicmc.services.exception.DataIntegrityException;
import com.marcio.ionicmc.services.exception.ObjectNotFoundException;

@Service // transforma a classe em um componente do Spring
public class ClienteService {
    // Injeção de dependência via construtor (recomendado)
    private final ClienteRepository repo;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;

    public ClienteService(ClienteRepository repo, CidadeRepository cidadeRepository, EnderecoRepository enderecoRepository) {
        this.repo = repo;
        this.cidadeRepository = cidadeRepository;
        this.enderecoRepository = enderecoRepository;
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

    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        // Busca o id no repositório
        Cliente newObj = find(obj.getId());
        // Atualiza os dados do objeto
        updateData(newObj, obj);
        // Salva o objeto
        return repo.save(newObj);
    }

    // método para converter ClienteDTO para Cliente
    public Cliente fromDTO(ClienteDTO objDto) {
        // Criação do objeto Categoria com os dados do DTO
        return new Cliente(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
    }

    // método para converter ClienteNewDTO para Cliente
    public Cliente fromDTO(ClienteNewDTO objDto) {
        // Criação do objeto Cliente com os dados do DTO
        Cliente cli = new Cliente(null, objDto.getName(), objDto.getEmail(), objDto.getCpfOuCnpj(),TipoCliente.toEnum(objDto.getTipo()));
        // verifica se a cidade foi informada
        if (objDto.getCidadeId() == null) {
            throw new IllegalArgumentException("O ID da cidade não pode ser nulo. Verifique os dados da requisição.");
        }
        // busca a cidade no banco de dados
        Optional<Cidade> cid = cidadeRepository.findById(objDto.getCidadeId());
        // criação do objeto Endereço com os dados do DTO
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid.orElseThrow(() -> new ObjectNotFoundException(
                // id que não foi encontrado
                "Objeto não encontrado: " + objDto.getCidadeId()
                // nome da classe que não foi encontrada
                        + ", Tipo: " + Cidade.class.getName())));
        // associação entre cliente e endereço
        cli.getEnderecos().add(end);
        // associação entre cliente e telefones
        cli.getTelefones().add(objDto.getTelefone1());
        // se o telefone 2 não for nulo, adiciona
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        // se o telefone 3 não for nulo, adiciona
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    // método auxiliar para atualizar os dados do objeto
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
                    "Não é possível excluir este cliente pois ele possui pedidos(entidades relacionadas)");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    // método para buscar categorias por páginação e ordenação
    public Page<Cliente> findPage(Integer page, Integer linePerPage, String orderBy, String direction) {
        // Criação do PageRequest com os parâmetros fornecidos
        PageRequest pageRequest = PageRequest.of(page, linePerPage, Direction.valueOf(direction), orderBy);
        // Retorna a página de categorias
        return repo.findAll(pageRequest);
    }
}
