package com.thalesoliveira.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thalesoliveira.workshopmongo.domain.User;
import com.thalesoliveira.workshopmongo.dto.UserDTO;
import com.thalesoliveira.workshopmongo.repository.UserRepository;
import com.thalesoliveira.workshopmongo.services.exception.ObjetoNotFoundException;

// Indica que esta classe é um serviço do Spring, responsável por conter a lógica de negócio 
//(regras da aplicação) antes de salvar no banco
@Service
public class UserService {

	// Realiza a injeção de dependência automática: o Spring instancia e conecta o
	// objeto UserRepository aqui para você (sem precisar dar "new")
	@Autowired
	private UserRepository repo;

	// Repassa a chamada para a camada de repositório (repo) buscar todos os objetos
	// User no banco de dados e retorna essa lista
	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		// Chama o repositório para buscar pelo ID. O retorno é 'Optional' porque o
		// resultado é incerto:
		// funciona como uma "caixa" que pode conter o usuário ou estar vazia (null
		// safe).
		Optional<User> obj = repo.findById(id);
		// Tenta abrir a "caixa":
		// 1. Se tiver um usuário dentro, retorna o objeto User.
		// 2. Se a caixa estiver vazia (orElseThrow), dispara a sua exceção
		// personalizada instantaneamente.
		return obj.orElseThrow(() -> new ObjetoNotFoundException("Objeto não encontrado"));
	}

	// Método responsável por receber um objeto User e solicitar sua gravação no
	// banco de dados
	public User insert(User obj) {
		// Chama a função 'insert' do repositório, que cria um novo documento no MongoDB
		// e retorna o objeto já salvo (incluindo o ID gerado automaticamente)
		return repo.insert(obj);
	}

	// Método responsável por excluir um usuário do banco de dados
	public void delete(String id) {
		// Passo 1: Busca o usuário pelo ID para garantir que ele existe.
		// Se o ID não for encontrado, este método lança a exceção
		// "ObjectNotFoundException" e o código para aqui (não tenta deletar).
		findById(id);

		// Passo 2: Se passou pela linha de cima, significa que o usuário existe.
		// Então, chama o repositório para efetuar a exclusão física no MongoDB.
		repo.deleteById(id);
	}

	// Método auxiliar responsável por converter um objeto UserDTO (focado na
	// comunicação externa) de volta para um objeto User (Entidade do banco de
	// dados)
	public User fromDTO(UserDTO objDTO) {
		// Instancia uma nova Entidade User, extraindo os dados do DTO e passando para o
		// construtor da Entidade
		return new User(objDTO.getId(), objDTO.getName(), objDTO.getEmail());
	}
}
