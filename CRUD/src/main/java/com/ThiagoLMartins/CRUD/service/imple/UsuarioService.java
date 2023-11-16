package com.ThiagoLMartins.CRUD.service.imple;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.repository.UsuarioRepository;
import com.ThiagoLMartins.CRUD.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService{

	@Autowired
	private UsuarioRepository dao;
	
	public UsuarioService(UsuarioRepository dao) {
		super();
		this.dao = dao;
	}

	@Override
	public void validarEmail(String email) throws Exception {
		Optional<Usuario> aux = dao.findByEmail(email);
		if(aux != null) {
			throw new Exception("Email já utilizado.");
		}
	}

	@Override
	public Usuario cadastrar(Usuario usuario) {
		return dao.save(usuario);
	}

	@Override
	public boolean existsByEmail(String email) {
		return dao.existsByEmail(email);
	}
}
