package com.thalesoliveira.workshopmongo.services;

import java.util.Date;
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

	// Método de serviço responsável por buscar posts contendo um texto
	public List<Post> findByTitle(String text) {

		// Chama o nosso método personalizado 'searchTitle' lá do Repository.
		// Diferente do anterior, este aqui usa a anotação @Query com regex.
		// A grande vantagem agora é que ele ignora maiúsculas e minúsculas (Case
		// Insensitive) por causa da opção 'i' que configuramos no banco.
		return repo.searchTitle(text);
	}

	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {

		// AJUSTE DE DATA (O Pulo do Gato):
		// Por padrão, a data 'maxDate' vem setada como meia-noite (00:00:00).
		// Se o usuário pedir até o dia 10/01, e tiver um post feito dia 10/01 às 15h,
		// ele NÃO apareceria.
		// Esta linha adiciona 24 horas (em milissegundos) à data final.
		// Cálculo: 24 horas * 60 minutos * 60 segundos * 1000 milissegundos = 1 dia.
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);

		// Agora sim, chama o repositório passando a data final ajustada (que agora é
		// 00:00 do dia SEGUINTE),
		// garantindo que pegaremos todos os posts do último dia até o último segundo.
		return repo.fullSearch(text, minDate, maxDate);
	}
}
