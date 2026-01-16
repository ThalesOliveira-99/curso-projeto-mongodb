package com.thalesoliveira.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thalesoliveira.workshopmongo.domain.Post;
import com.thalesoliveira.workshopmongo.repository.PostRepository;
import com.thalesoliveira.workshopmongo.services.exception.ObjetoNotFoundException;

// Indica que esta classe é um serviço do Spring, responsável por conter a lógica de negócio 
//(regras da aplicação) antes de salvar no banco
@Service
public class PostService {

	// Realiza a injeção de dependência automática: o Spring instancia e conecta o
	// objeto UserRepository aqui para você (sem precisar dar "new")
	@Autowired
	private PostRepository repo;

	public Post findById(String id) {
		// Chama o repositório para buscar pelo ID. O retorno é 'Optional' porque o
		// resultado é incerto:
		// funciona como uma "caixa" que pode conter o usuário ou estar vazia (null
		// safe).
		Optional<Post> obj = repo.findById(id);
		// Tenta abrir a "caixa":
		// 1. Se tiver um usuário dentro, retorna o objeto User.
		// 2. Se a caixa estiver vazia (orElseThrow), dispara a sua exceção
		// personalizada instantaneamente.
		return obj.orElseThrow(() -> new ObjetoNotFoundException("Objeto não encontrado"));
	}

	// Método responsável por buscar TODOS os posts existentes no banco de dados
	public List<Post> findAll() {
		// O repo.findAll() é um método padrão do MongoRepository que retorna uma lista
		// completa
		return repo.findAll();
	}

	// Método de serviço responsável por buscar uma lista de Posts baseada em um
	// trecho de texto
	public List<Post> findByTitle(String text) {

		// Chama o método do repositório.
		// O sufixo 'Containing' é uma palavra-chave mágica do Spring Data.
		// Ele diz ao banco: "Não procure o título exato, procure qualquer título que
		// CONTENHA esse texto no meio (como o LIKE %texto% do SQL)".
		return repo.findByTitleContainingIgnoreCase(text);
	}
}
