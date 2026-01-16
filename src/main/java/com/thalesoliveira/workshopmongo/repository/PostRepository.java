package com.thalesoliveira.workshopmongo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.thalesoliveira.workshopmongo.domain.Post;

//Indica que esta classe é responsável por acessar o banco de dados e gerenciar os dados (salvar, deletar, buscar)
@Repository
//Herda métodos prontos do Spring Data (como save, delete, findAll) para a entidade User, onde o ID é do tipo String
public interface PostRepository extends MongoRepository<Post, String> {

	// @Query: Permite que você escreva uma consulta personalizada no formato JSON
	// do MongoDB.
	// Isso sobrescreve a criação automática de consulta do Spring (o nome do método
	// 'searchTitle' não importa mais).
	@Query("{ 'title': { $regex: ?0, $options: 'i' } }")
	List<Post> searchTitle(String text);

// QUERY METHOD (Método de Consulta Automático)

	// O Spring Data analisa o nome deste método para montar a consulta no banco:
	// 1. "findBy": Comando padrão para iniciar uma busca.
	// 2. "Title": O Spring procura um atributo chamado 'title' na sua classe Post.
	// 3. "Containing": Aplica um filtro de texto parcial (semelhante ao LIKE
	// '%texto%' do SQL).
	List<Post> findByTitleContainingIgnoreCase(String text);

	// @Query: Define uma consulta JSON personalizada completa.
	// A estrutura geral é um grande E ($and), que exige que três coisas aconteçam
	// ao mesmo tempo:
	// 1. A data deve ser maior ou igual a minDate.
	// 2. A data deve ser menor ou igual a maxDate.
	// 3. O texto deve ser encontrado em PELO MENOS UM ($or) destes lugares: Título,
	// Corpo ou Comentários.
	@Query("{ $and: [ {date: {$gte: ?1} }, {date: {$lte: ?2} } , { $or: [ { 'title': { $regex: ?0, $options: 'i' } }, { 'body': { $regex: ?0, $options: 'i' } }, { 'comments.text': { $regex: ?0, $options: 'i' } } ] } ] }")
	List<Post> fullSearch(String text, Date minDate, Date maxDate);
}
