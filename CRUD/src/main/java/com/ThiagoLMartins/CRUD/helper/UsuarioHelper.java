package com.ThiagoLMartins.CRUD.helper;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioHelper {

	private Long id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String nome;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String senha;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String email;
}
