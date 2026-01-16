package com.thalesoliveira.workshopmongo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thalesoliveira.workshopmongo.domain.Post;
import com.thalesoliveira.workshopmongo.resources.util.URL;
import com.thalesoliveira.workshopmongo.services.PostService;

//Indica que esta classe é um recurso web REST (vai responder com dados JSON, e não páginas HTML)
@RestController
//Define o caminho (endpoint) base da URL para acessar este recurso (ex: localhost:8080/posts)
@RequestMapping(value = "/posts")
public class PostResource {

	// Injeta a dependência do Serviço (PostService), permitindo que o Controlador
	// delegue para ele a execução das regras de negócio
	@Autowired
	private PostService service;

	// Mapeia requisições do tipo GET que trazem um id na URL (ex: /users/123). As
	// chaves { } indicam que essa parte do caminho é variável.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// O @PathVariable pega o valor do "{id}" que veio na URL e o atribui à variável
	// 'id' para ser usada na busca. O retorno será um UserDTO envelopado.
	public ResponseEntity<Post> findById(@PathVariable String id) {
		// Aciona a camada de serviço para buscar o usuário pelo ID e guarda o objeto
		// original (Entidade) retornado na variável 'obj'
		Post obj = service.findById(id);
		// Converte o objeto 'obj' (Entidade) para 'UserDTO' ali mesmo e o envia no
		// corpo da resposta com status 200 (OK), garantindo que apenas os dados
		// filtrados sejam expostos
		return ResponseEntity.ok().body(obj);
	}

	// Mapeia requisições GET para a raiz do endpoint (/posts).
	// Como não tem "/{id}", ele atende quando chamamos apenas localhost:8080/posts
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findAll() {

		// Chama o serviço para buscar a lista
		List<Post> list = service.findAll();

		// Retorna a lista no corpo da resposta com status 200 OK
		return ResponseEntity.ok().body(list);
	}

	// Mapeia uma nova rota GET.
	// Supondo que estamos no PostResource (/posts), a URL final será:
	// /posts/titlesearch
	@RequestMapping(value = "/titlesearch", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findByTitle(
			// @RequestParam: Indica que o valor não vem na barra (/), mas sim depois de uma
			// interrogação (?) na URL.
			// value="text": O nome do parâmetro na URL deve ser 'text'.
			// defaultValue="": Se o usuário não digitar nada, assume que é uma string vazia
			// (evita erro de nulo).
			@RequestParam(value = "text", defaultValue = "") String text) {

		// 1. Decodifica o texto.
		// Se o usuário digitou "bom%20dia" na URL, essa linha transforma volta para
		// "bom dia" usando sua classe utilitária URL.
		text = URL.decodeParam(text);

		// 2. Chama o serviço para buscar os posts que contenham essa palavra.
		List<Post> list = service.findByTitle(text);

		// 3. Retorna a lista encontrada com status 200 OK.
		return ResponseEntity.ok().body(list);
	}
}
