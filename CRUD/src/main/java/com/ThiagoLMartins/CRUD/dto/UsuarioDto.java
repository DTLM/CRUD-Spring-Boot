package com.ThiagoLMartins.CRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

	private Long id;
	private String nome;
	private String senha;
	private String email;
}
