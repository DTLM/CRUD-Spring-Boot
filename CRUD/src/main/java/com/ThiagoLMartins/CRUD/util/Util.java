package com.ThiagoLMartins.CRUD.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	public static <T,U> U atualizarClasse(T fonte, U destino, Class<U> classeDestino) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String valoresAtualizados = mapper.writeValueAsString(fonte);
		destino = mapper.readValue(valoresAtualizados, classeDestino);
		return destino;
	}
	
	public static <T,U> List<U> atualizarListaClasse(List<T> fontes, List<U> destinos, Class<U> classeDestino) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		for(T fonte: fontes) {
			String valoresAtualizados = mapper.writeValueAsString(fonte);
			U destino = mapper.readValue(valoresAtualizados, classeDestino);
		}
		return destinos;
	}
}
