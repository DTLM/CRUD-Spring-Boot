package com.ThiagoLMartins.CRUD.service;

import com.ThiagoLMartins.CRUD.modal.Usuario;

public interface IUsuarioService {

	void validarEmail(String email) throws Exception;
	
	Usuario cadastrar(Usuario usuario);
	
	boolean existsByEmail(String email);
}
