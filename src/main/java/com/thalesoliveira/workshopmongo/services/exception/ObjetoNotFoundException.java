package com.thalesoliveira.workshopmongo.services.exception;

public class ObjetoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// Construtor que recebe a mensagem de erro personalizada e a repassa para a
	// classe pai (RuntimeException) para que ela seja armazenada e exibida no log
	public ObjetoNotFoundException(String msg) {
		super(msg);
	}
}
