package com.ThiagoLMartins.CRUD.service.imple;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ThiagoLMartins.CRUD.exception.UsuarioExisteException;
import com.ThiagoLMartins.CRUD.exception.UsuarioNotFoundException;
import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;
import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.repository.UsuarioRepository;
import com.ThiagoLMartins.CRUD.service.IUsuarioService;
import com.ThiagoLMartins.CRUD.util.Util;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private UsuarioRepository dao;


	public UsuarioService(UsuarioRepository dao) {
		this.dao = dao;
	}

	@Override
	public void validarEmail(String email) throws Exception {
		Optional<Usuario> aux = dao.findByEmail(email);
		if (aux != null && aux.get() != null) {
			throw new Exception("Email já utilizado.");
		}
	}

	@Override
	public Usuario cadastrar(UsuarioHelper usuario) throws UsuarioExisteException {
		Usuario usu = null;
		boolean verificacao = this.existsByEmail(usuario.getEmail());
		if (usuario != null && !verificacao) {
			usu = new Usuario();
			usu.setEmail(usuario.getEmail());
			usu.setNome(usuario.getNome());
			usu.setSenha(usuario.getSenha());
			usu = dao.save(usu);
		} else {
			throw new UsuarioExisteException("Usuario já cadastrado com esse email");
		}
		return usu;
	}

	@Override
	public boolean existsByEmail(String email) {
		return dao.existsByEmail(email);
	}

	@Override
	public List<Usuario> listAll() {
		return dao.findAll();
	}

	@Override
	public Usuario editarUsuarioById(UsuarioHelper usuario) throws Exception {
		Optional<Usuario> opt = dao.findById(usuario.getId());
		Usuario usu = null;
		if (opt.isPresent()) {
			this.validarEmail(usuario.getEmail());
			usu = opt.get();
			usu = Util.atualizarClasse(usuario, usu, Usuario.class);
			usu = this.dao.save(usu);
		} else {
			throw new UsuarioNotFoundException("Usuário não encontrado com esse ID.");
		}
		return usu;
	}

	@Override
	public void deletarUsuarioById(Long id) {
		this.dao.deleteById(id);
	}

	@Override
	public Usuario getById(Long id) {
		Optional<Usuario> usuario = dao.findById(id);
		return  usuario != null && usuario.isPresent()? usuario.get() : null;
	}
}
