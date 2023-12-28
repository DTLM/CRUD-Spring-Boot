package com.ThiagoLMartins.CRUD.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;
import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.service.IUsuarioService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/user")
public class UsuarioControler {

	@Autowired
	private IUsuarioService usuarioService;
	
	@PostMapping(path = "/salvar")
	public ResponseEntity salvarUsuario(@RequestBody UsuarioHelper usuario ) {
		try {
			Usuario usu = usuarioService.cadastrar(usuario);
			return new ResponseEntity(usu, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping(path = "/editar")
	public ResponseEntity editarUsuario(@RequestBody UsuarioHelper usuario ) {
		try {
			Usuario usu = usuarioService.editarUsuarioById(usuario);
			return new ResponseEntity(usu, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "/getById/{id}")
	public ResponseEntity getById(@PathParam("id") String id ) {
		try {
			Usuario usu = usuarioService.getById(Long.valueOf(id));
			return new ResponseEntity(usu, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping(path = "/listAll")
	public ResponseEntity findAll() {
		try {
			List<Usuario> usu = usuarioService.listAll();
			return new ResponseEntity(usu, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/deleteById/{id}")
	public ResponseEntity deleteById(@PathParam("id") String id ) {
		try {
			usuarioService.deletarUsuarioById(Long.valueOf(id));
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}
