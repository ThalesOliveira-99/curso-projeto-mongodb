package com.thalesoliveira.workshopmongo.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thalesoliveira.workshopmongo.domain.User;

//Indica que esta classe é um recurso web REST (vai responder com dados JSON, e não páginas HTML)
@RestController
//Define o caminho (endpoint) base da URL para acessar este recurso (ex: localhost:8080/users)
@RequestMapping(value = "/users")
public class UserResource {

	// Quando alguém acessar essa URL querendo buscar informações (GET), execute
	// este método aqui
	// @GetMapping
	@RequestMapping(method = RequestMethod.GET)
	// Encapsula toda a estrutura da resposta HTTP: permite definir o código de
	// status (ex: 200 OK, 404 Not Found), os cabeçalhos e o corpo (body) da
	// resposta
	public ResponseEntity<List<User>> findAll() {
		User maria = new User("1", "Maria", "Maria@email.com");
		User alex = new User("2", "Alex Green", "Alex@email.com");
		List<User> list = new ArrayList<>();
		list.addAll(Arrays.asList(maria, alex));
		// Retorna a resposta com status HTTP 200 (Sucesso) e coloca a lista de usuários
		// no corpo da resposta
		return ResponseEntity.ok().body(list);
	}
}
