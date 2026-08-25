package com.marcio.ionicmc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcio.ionicmc.domain.Categoria;
import com.marcio.ionicmc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		// Injeta o serviço e chama o método find
		Categoria obj = service.find(id);
		// Retorna o objeto encontrado com o código HTTP 200
		return ResponseEntity.ok(obj);
	}
}
