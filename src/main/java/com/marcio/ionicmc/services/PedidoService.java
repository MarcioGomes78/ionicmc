package com.marcio.ionicmc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcio.ionicmc.domain.ItemPedido;
import com.marcio.ionicmc.domain.PagamentoComBoleto;
import com.marcio.ionicmc.domain.Pedido;
import com.marcio.ionicmc.domain.enums.EstadoPagamento;
import com.marcio.ionicmc.repositories.ItemPedidoRepository;
import com.marcio.ionicmc.repositories.PagamentoRepository;
import com.marcio.ionicmc.repositories.PedidoRepository;
import com.marcio.ionicmc.services.exception.ObjectNotFoundException;

@Service // transforma a classe em um componente do Spring
public class PedidoService<itemPedidoRepository> {

    @Autowired
    private PedidoRepository repo; // injeção de dependência

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

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

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstant(new Date());
        //configurando o cliente
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        //configurando o endereço do pedido como endereço de entrega
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            //usar o mock service de boleto
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstant());
        }
        repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            //configurando o desconto
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            //configurando o preço do item
            ip.setPreco(produtoService.find(ip.getProduto().getId()).getPrice());
            //configurando o pedido
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        //enviando email de confirmação de pedido
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }
}
