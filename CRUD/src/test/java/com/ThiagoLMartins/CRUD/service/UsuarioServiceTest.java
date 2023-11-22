package com.ThiagoLMartins.CRUD.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.service.imple.UsuarioService;

@ExtendWith(value = SpringExtension.class)
@ActiveProfiles("testes")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioServiceTest {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private TestEntityManager maneger;
	
	@Test
	public void existsByEmailError() {
		Usuario usu = Usuario.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		assertThrows(Exception.class, () -> {
			maneger.persist(usu);
			usuarioService.validarEmail(usu.getEmail());
			maneger.persist(usu);
		});
	}
	
	@Test
	public void existsByEmail() {
		Usuario usu = Usuario.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		assertThrows(Exception.class, () -> {
			usuarioService.validarEmail(usu.getEmail());
			maneger.persist(usu);
		});
	}
}
