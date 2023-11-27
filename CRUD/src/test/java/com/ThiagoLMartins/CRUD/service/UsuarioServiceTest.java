package com.ThiagoLMartins.CRUD.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;

@ExtendWith(value = SpringExtension.class)
@ActiveProfiles("testes")
@SpringBootTest
public class UsuarioServiceTest {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Test
	public void existsByEmailError() {
		UsuarioHelper usu = UsuarioHelper.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		assertThrows(Exception.class, () -> {
			usuarioService.cadastrar(usu);
			usuarioService.validarEmail(usu.getEmail());
			usuarioService.cadastrar(usu);
		});
	}
	
	@Test
	public void existsByEmail() {
		UsuarioHelper usu = UsuarioHelper.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		assertThrows(Exception.class, () -> {
			usuarioService.validarEmail(usu.getEmail());
			usuarioService.cadastrar(usu);
		});
	}
}
