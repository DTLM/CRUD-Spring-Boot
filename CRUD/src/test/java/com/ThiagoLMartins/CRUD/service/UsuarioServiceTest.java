package com.ThiagoLMartins.CRUD.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ThiagoLMartins.CRUD.exception.UsuarioExisteException;
import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;
import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.repository.UsuarioRepository;
import com.ThiagoLMartins.CRUD.service.imple.UsuarioService;

@ExtendWith(value = SpringExtension.class)
@ExtendWith(value = MockitoExtension.class)
@ActiveProfiles("testes")
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioServiceTest {

	// Pode-se fazer dessa forma
	@Autowired
	private IUsuarioService usuarioService;
	
	/**
	 * Ou mokando os testes. Mokar é o ato de criar uma classe falsa, que nos dizemos o que deve retornar. Sem executar a query.
	 * Mocks são utilizados em testes de integração.
	 * 
	 * O {@MockBean} é uma anotação como autowired, onde o spring fica-ra a cargo de iniciar o mock.
	 */
	@MockBean
	private UsuarioRepository dao;
	private IUsuarioService service;
	
	@BeforeAll
	public void setUp() {
		// forma manual de iniciar o mock
		// dao = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioService(dao);
	}
	
	@Test
	public void existsByEmailErrorWithMock() {
		UsuarioHelper usu = UsuarioHelper.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		
		Mockito.when(dao.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		assertThrows(Exception.class, () -> {
			service.cadastrar(usu);
		});
	}
	
	@Test
	public void cadastroSuccessComMock() {
		try {
			UsuarioHelper usuHelper = UsuarioHelper.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
			Usuario usu = Usuario.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
			
			Mockito.when(service.cadastrar(usuHelper)).thenReturn(usu);
			
			Usuario resultado = this.service.cadastrar(usuHelper);
			assertThat(resultado).isNotNull();
		} catch (UsuarioExisteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void existsByEmailErrorSemMock() {
		UsuarioHelper usu = UsuarioHelper.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		assertThrows(Exception.class, () -> {
			usuarioService.cadastrar(usu);
			usuarioService.validarEmail(usu.getEmail());
			usuarioService.cadastrar(usu);
		});
	}
	
	@Test
	public void existsByEmailSemMock() {
		UsuarioHelper usu = UsuarioHelper.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		assertThrows(Exception.class, () -> {
			usuarioService.validarEmail(usu.getEmail());
			usuarioService.cadastrar(usu);
		});
	}
}
