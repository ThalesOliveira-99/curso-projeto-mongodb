package com.thalesoliveira.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thalesoliveira.workshopmongo.domain.User;
import com.thalesoliveira.workshopmongo.dto.UserDTO;
import com.thalesoliveira.workshopmongo.services.UserService;

//Indica que esta classe é um recurso web REST (vai responder com dados JSON, e não páginas HTML)
@RestController
//Define o caminho (endpoint) base da URL para acessar este recurso (ex: localhost:8080/users)
@RequestMapping(value = "/users")
public class UserResource {

	// Injeta a dependência do Serviço (UserService), permitindo que o Controlador
	// delegue para ele a execução das regras de negócio
	@Autowired
	private UserService service;

	// Quando alguém acessar essa URL querendo buscar informações (GET), execute
	// este método aqui
	@RequestMapping(method = RequestMethod.GET)
	// Encapsula toda a estrutura da resposta HTTP: permite definir o código de
	// status (ex: 200 OK, 404 Not Found), os cabeçalhos e o corpo (body) da
	// resposta
	public ResponseEntity<List<UserDTO>> findAll() {
		// Chama o serviço para buscar os usuários no banco e armazena o resultado na
		// lista antes de retornar
		List<User> list = service.findAll();
		// Converte a lista de User (Entidade) para uma lista de UserDTO: pega cada
		// objeto 'x', transforma em DTO e reagrupa em uma nova lista
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		// Retorna a resposta com status HTTP 200 (Sucesso) e coloca a lista de usuários
		// no corpo da resposta
		return ResponseEntity.ok().body(listDto);
	}

	// Mapeia requisições do tipo GET que trazem um id na URL (ex: /users/123). As
	// chaves { } indicam que essa parte do caminho é variável.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// O @PathVariable pega o valor do "{id}" que veio na URL e o atribui à variável
	// 'id' para ser usada na busca. O retorno será um UserDTO envelopado.
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		// Aciona a camada de serviço para buscar o usuário pelo ID e guarda o objeto
		// original (Entidade) retornado na variável 'obj'
		User obj = service.findById(id);
		// Converte o objeto 'obj' (Entidade) para 'UserDTO' ali mesmo e o envia no
		// corpo da resposta com status 200 (OK), garantindo que apenas os dados
		// filtrados sejam expostos
		return ResponseEntity.ok().body(new UserDTO(obj));
	}

	// Mapeia requisições do tipo POST (usado para inserir/criar novos recursos no
	// servidor)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {

		// Converte o objeto DTO que veio da requisição (JSON) para um objeto Entidade
		// (User) que o banco entende
		User obj = service.fromDTO(objDto);

		// Chama o serviço para salvar no banco. A variável 'obj' é atualizada com o
		// novo objeto salvo (agora contendo o ID gerado pelo Mongo)
		obj = service.insert(obj);

		// Cria o endereço (URI) do novo recurso criado. Pega a URL atual (ex: /users),
		// adiciona "/{id}" e substitui pelo ID real do novo usuário
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		// Retorna o código HTTP 201 (Created). Esse código espera um cabeçalho
		// 'Location' contendo a URI criada acima, mas não retorna corpo (body) na
		// resposta
		return ResponseEntity.created(uri).build();
	}

	// Mapeia requisições HTTP do tipo DELETE que tenham um ID na URL (ex: DELETE
	// /users/123)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {

		// Chama a camada de serviço para realizar a exclusão.
		// Lembre-se: o seu serviço já tem a lógica de verificar se o ID existe antes de
		// tentar apagar.
		service.delete(id);

		// Retorna o código HTTP 204 (No Content).
		// Esse é o padrão mundial para deletar: diz ao navegador "Operação realizada
		// com sucesso, mas não tenho nada para te mostrar de volta".
		return ResponseEntity.noContent().build();
	}

	// Mapeia requisições do tipo PUT (usado para atualizar dados) que tenham um ID
	// na URL.
	// Exemplo de chamada: PUT /users/123 (onde o corpo do JSON traz os novos dados)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody UserDTO objDto, @PathVariable String id) {

		// 1. Converte o DTO (JSON recebido) para um objeto Entidade (User).
		// Isso é necessário porque o Service trabalha com Entidades, não com DTOs.
		User obj = service.fromDTO(objDto);

		// 2. Garante a segurança e consistência: Pega o ID que veio na URL
		// (@PathVariable) e o coloca dentro do objeto.
		// Isso evita que o usuário mal intencionado envie um ID na URL (123) e outro
		// diferente no JSON (456). O da URL manda.
		obj.setId(id);

		// 3. Chama o serviço para efetivar a atualização no banco de dados.
		obj = service.update(obj);

		// 4. Retorna o código HTTP 204 (No Content).
		// Assim como no Delete, no Update geralmente não precisa retornar nada,
		// apenas avisar que a operação foi um sucesso.
		return ResponseEntity.noContent().build();
	}
}
