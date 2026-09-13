package com.marcio.ionicmc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marcio.ionicmc.domain.Pedido;
import com.marcio.ionicmc.services.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		// Injeta o serviço e chama o método find
		Pedido obj = service.find(id);
		// Retorna o objeto encontrado com o código HTTP 200
		return ResponseEntity.ok(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		// O corpo da requisição é o objeto Categoria
		obj = service.insert(obj);
		// Adiciona a URI do novo recurso criado ao header da resposta
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		// Retorna o objeto criado com o código HTTP 201
		return ResponseEntity.created(uri).build();
	}
}
