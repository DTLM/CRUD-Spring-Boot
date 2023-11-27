package com.ThiagoLMartins.CRUD.dto;

import lombok.Data;

@Data
public class UsuarioDto {

	private Long id;
	private String nome;
	private String senha;
	private String email;
}
