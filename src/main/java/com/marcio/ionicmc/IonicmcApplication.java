package com.marcio.ionicmc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marcio.ionicmc.domain.Categoria;
import com.marcio.ionicmc.domain.Cidade;
import com.marcio.ionicmc.domain.Cliente;
import com.marcio.ionicmc.domain.Endereco;
import com.marcio.ionicmc.domain.Estado;
import com.marcio.ionicmc.domain.ItemPedido;
import com.marcio.ionicmc.domain.Pagamento;
import com.marcio.ionicmc.domain.PagamentoComBoleto;
import com.marcio.ionicmc.domain.PagamentoComCartao;
import com.marcio.ionicmc.domain.Pedido;
import com.marcio.ionicmc.domain.Produto;
import com.marcio.ionicmc.domain.enums.EstadoPagamento;
import com.marcio.ionicmc.domain.enums.TipoCliente;
import com.marcio.ionicmc.repositories.CategoriaRepository;
import com.marcio.ionicmc.repositories.CidadeRepository;
import com.marcio.ionicmc.repositories.ClienteRepository;
import com.marcio.ionicmc.repositories.EnderecoRepository;
import com.marcio.ionicmc.repositories.EstadoRepository;
import com.marcio.ionicmc.repositories.ItemPedidoRepository;
import com.marcio.ionicmc.repositories.PagamentoRepository;
import com.marcio.ionicmc.repositories.PedidoRepository;
import com.marcio.ionicmc.repositories.ProdutoRepository;


@SpringBootApplication
public class IonicmcApplication implements CommandLineRunner {

    //importar o repositório de categoria
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(IonicmcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfumaria");

        Produto p1 = new Produto(null, "Computador", 2000.00);
        Produto p2 = new Produto(null, "Mouse", 80.00);
        Produto p3 = new Produto(null, "Teclado", 100.00);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "Belo Horizonte", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Campinas", est2);

        est1.getCidades().addAll(Arrays.asList(c1));
        est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Marcio Gomes", "mjgomes1978@gmail.com", "3631772000149", TipoCliente.PESSOA_FISICA);
        cli1.getTelefones().addAll(Arrays.asList("27363323", "938383434"));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Centro", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "100", "Casa", "Centro", "38220834", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));    

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2016 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2016 11:32"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, null, sdf.parse("20/10/2016 00:00"));
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00); 
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
