package com.ThiagoLMartins.CRUD.exception;

public class UsuarioNotFoundException extends RuntimeException{

	public UsuarioNotFoundException(String mensagem) {
		super(mensagem);
		
	}
}
