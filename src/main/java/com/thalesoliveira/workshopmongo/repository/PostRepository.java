package com.thalesoliveira.workshopmongo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.thalesoliveira.workshopmongo.domain.Post;

//Indica que esta classe é responsável por acessar o banco de dados e gerenciar os dados (salvar, deletar, buscar)
@Repository
//Herda métodos prontos do Spring Data (como save, delete, findAll) para a entidade User, onde o ID é do tipo String
public interface PostRepository extends MongoRepository<Post, String> {

// QUERY METHOD (Método de Consulta Automático)

	// O Spring Data analisa o nome deste método para montar a consulta no banco:
	// 1. "findBy": Comando padrão para iniciar uma busca.
	// 2. "Title": O Spring procura um atributo chamado 'title' na sua classe Post.
	// 3. "Containing": Aplica um filtro de texto parcial (semelhante ao LIKE
	// '%texto%' do SQL).
	List<Post> findByTitleContainingIgnoreCase(String text);
}
