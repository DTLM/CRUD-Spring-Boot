package com.ThiagoLMartins.CRUD.helper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioHelper {

	private Long id;
	private String nome;
	private String senha;
	private String email;
}
