package com.ThiagoLMartins.CRUD.service;

import java.util.List;

import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;
import com.ThiagoLMartins.CRUD.modal.Usuario;

public interface IUsuarioService{

	void validarEmail(String email) throws Exception;
	
	Usuario cadastrar(UsuarioHelper usuario);
	
	boolean existsByEmail(String email);
	
	List<Usuario> listAll();
	
	Usuario editarUsuarioById(UsuarioHelper usuario) throws Exception;
	
	void deletarUsuarioById(Long id);
}
