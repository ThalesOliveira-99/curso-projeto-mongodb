package com.thalesoliveira.workshopmongo.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URL {

	// Método estático auxiliar para decodificar parâmetros que vêm na URL
	public static String decodeParam(String text) {
		try {
			// Tenta decodificar o texto usando o padrão UTF-8.
			// Exemplo: Se chegar "bom%20dia", ele transforma de volta para "bom dia".
			return URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// Se, por algum motivo muito raro, o padrão UTF-8 não for suportado pelo
			// sistema,
			// retorna uma string vazia para não quebrar a aplicação com erro.
			return "";
		}
	}
}
