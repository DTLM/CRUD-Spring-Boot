package com.ThiagoLMartins.CRUD.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.service.IUsuarioService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Regras para testes unitarios e de integração
 * 1- cenario
 * 2- ação e execução
 * 3- validação
 */

@SpringBootTest
@ExtendWith(value = SpringExtension.class)
@ActiveProfiles("testes")
public class UsuarioRepositoryTest {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Test
	public void findByEmailTeste() {
		try {
			Usuario usu = new Usuario();
			usu.setNome("teste");
			usu.setEmail("teste@gmail.com");
			usu.setSenha("123");
			usuarioService.cadastrar(usu);
			Boolean result = usuarioService.existsByEmail(usu.getEmail());
			assertThat(result).isTrue();
			assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
