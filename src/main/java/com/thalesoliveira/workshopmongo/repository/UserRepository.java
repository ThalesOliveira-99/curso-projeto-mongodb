package com.thalesoliveira.workshopmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.thalesoliveira.workshopmongo.domain.User;

//Indica que esta classe é responsável por acessar o banco de dados e gerenciar os dados (salvar, deletar, buscar)
@Repository
//Herda métodos prontos do Spring Data (como save, delete, findAll) para a entidade User, onde o ID é do tipo String
public interface UserRepository extends MongoRepository<User, String> {

}
