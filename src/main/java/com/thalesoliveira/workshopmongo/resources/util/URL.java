package com.thalesoliveira.workshopmongo.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

	// Método utilitário estático para converter uma String (ex: "2026-01-15") em um
	// objeto Date do Java
	public static Date convertDate(String textDate, Date defaultValue) {

		// Define o formato esperado da data.
		// "yyyy-MM-dd" significa que esperamos o ano com 4 dígitos, hífen, mês com 2
		// dígitos, hífen, dia com 2 dígitos.
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// Define o fuso horário como GMT (Greenwich Mean Time).
		// Isso é crucial para evitar problemas de "D-1" (quando você salva dia 15 e o
		// banco grava dia 14 às 21h por causa do fuso do Brasil).
		// O MongoDB armazena datas em UTC/GMT por padrão.
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		try {
			// Tenta converter o texto recebido para uma data válida.
			return sdf.parse(textDate);
		} catch (ParseException e) {
			// A REDE DE SEGURANÇA:
			// Se a conversão falhar (ex: usuário enviou "banana" ou "15/01/2026" com
			// barras),
			// em vez de quebrar o programa com erro, retornamos um valor padrão
			// (defaultValue) passado por quem chamou o método.
			return defaultValue;
		}
	}

}
