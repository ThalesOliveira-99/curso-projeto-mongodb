package com.thalesoliveira.workshopmongo.resources.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.thalesoliveira.workshopmongo.services.exception.ObjetoNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

//Trata possíveis erros das minhas requisições
@ControllerAdvice
public class ResourceExceptionHandler {

	// A anotação @ExceptionHandler funciona como um "interceptador": ela avisa ao
	// Spring que, se estourar uma exceção do tipo 'ObjetoNotFoundException' em
	// qualquer lugar, é este método que deve assumir o controle.
	@ExceptionHandler(ObjetoNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjetoNotFoundException e, HttpServletRequest request) {

		// Define qual será o código de resposta HTTP (neste caso, 404 Not Found),
		// indicando semanticamente que o recurso buscado não existe.
		HttpStatus status = HttpStatus.NOT_FOUND;

		// Cria o objeto StandardError (sua classe personalizada) preenchendo os dados:
		// 1. System.currentTimeMillis(): Pega o horário exato do erro.
		// 2. status.value(): Converte o ENUM para o número inteiro 404.
		// 3. e.getMessage(): Pega a mensagem de texto que foi definido lá no Service
		// ("Objeto não encontrado").
		// 4. request.getRequestURI(): Pega o caminho da URL que o usuário tentou
		// acessar (ex: "/users/10").
		StandardError err = new StandardError(System.currentTimeMillis(), status.value(), e.getMessage(),
				request.getRequestURI());

		// Monta a resposta final (ResponseEntity):
		// .status(status): Define o código 404 no cabeçalho da resposta.
		// .body(err): Coloca o seu objeto StandardError no corpo da resposta (que será
		// convertido para JSON).
		return ResponseEntity.status(status).body(err);
	}
}
