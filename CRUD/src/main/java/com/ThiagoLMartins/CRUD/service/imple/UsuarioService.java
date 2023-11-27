package com.ThiagoLMartins.CRUD.service.imple;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;
import com.ThiagoLMartins.CRUD.mapper.UsuarioMapper;
import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.repository.UsuarioRepository;
import com.ThiagoLMartins.CRUD.service.IUsuarioService;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private UsuarioRepository dao;

//	@Autowired
//	private UsuarioMapper usuarioMapper;

	public UsuarioService(UsuarioRepository dao) {
		this.dao = dao;
	}

	@Override
	public void validarEmail(String email) throws Exception {
		Optional<Usuario> aux = dao.findByEmail(email);
		if (aux != null) {
			throw new Exception("Email já utilizado.");
		}
	}

	@Override
	public Usuario cadastrar(UsuarioHelper usuario) {
		Usuario usu = null;
		if (usuario != null) {
			usu = new Usuario();
//			usuarioMapper.updateUsuarioFromHelper(usuario, usu);
			usu = dao.save(usu);
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
//			usuarioMapper.updateUsuarioFromHelper(usuario, usu);
			usu = this.dao.save(usu);
		}
		return usu;
	}

	@Override
	public void deletarUsuarioById(Long id) {
		this.dao.deleteById(id);
	}
}
