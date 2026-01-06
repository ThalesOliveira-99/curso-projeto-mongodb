package com.thalesoliveira.workshopmongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thalesoliveira.workshopmongo.domain.User;
import com.thalesoliveira.workshopmongo.repository.UserRepository;

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
}
