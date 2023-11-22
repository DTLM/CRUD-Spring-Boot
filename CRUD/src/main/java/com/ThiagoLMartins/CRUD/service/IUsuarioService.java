package com.ThiagoLMartins.CRUD.service;

import org.springframework.stereotype.Component;

import com.ThiagoLMartins.CRUD.modal.Usuario;

@Component
public interface IUsuarioService{

	void validarEmail(String email) throws Exception;
	
	Usuario cadastrar(Usuario usuario);
	
	boolean existsByEmail(String email);
}
