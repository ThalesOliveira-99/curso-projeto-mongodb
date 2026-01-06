package com.thalesoliveira.workshopmongo.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thalesoliveira.workshopmongo.domain.User;
import com.thalesoliveira.workshopmongo.services.UserService;

//Indica que esta classe é um recurso web REST (vai responder com dados JSON, e não páginas HTML)
@RestController
//Define o caminho (endpoint) base da URL para acessar este recurso (ex: localhost:8080/users)
@RequestMapping(value = "/users")
public class UserResource {
	
	// Injeta a dependência do Serviço (UserService), permitindo que o Controlador delegue para ele a execução das regras de negócio
	@Autowired
	private UserService service;

	// Quando alguém acessar essa URL querendo buscar informações (GET), execute
	// este método aqui
	// @GetMapping
	@RequestMapping(method = RequestMethod.GET)
	// Encapsula toda a estrutura da resposta HTTP: permite definir o código de
	// status (ex: 200 OK, 404 Not Found), os cabeçalhos e o corpo (body) da
	// resposta
	public ResponseEntity<List<User>> findAll() {
		// Chama o serviço para buscar os usuários no banco e armazena o resultado na lista antes de retornar
		List<User> list = service.findAll();
		// Retorna a resposta com status HTTP 200 (Sucesso) e coloca a lista de usuários
		// no corpo da resposta
		return ResponseEntity.ok().body(list);
	}
}
