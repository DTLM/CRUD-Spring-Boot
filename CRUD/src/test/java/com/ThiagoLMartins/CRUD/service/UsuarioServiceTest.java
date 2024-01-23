package com.ThiagoLMartins.CRUD.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ThiagoLMartins.CRUD.exception.UsuarioExisteException;
import com.ThiagoLMartins.CRUD.exception.UsuarioNotFoundException;
import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;
import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.repository.UsuarioRepository;
import com.ThiagoLMartins.CRUD.service.imple.UsuarioService;

@ExtendWith(value = SpringExtension.class)
@ActiveProfiles("testes")
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UsuarioServiceTest {

	private Usuario usu;
	private UsuarioHelper helper;
	
	// Pode-se fazer dessa forma
	@Autowired
	private IUsuarioService usuarioService;
	
	/**
	 * Ou mokando os testes. Mokar é o ato de criar uma classe falsa, que nos dizemos o que deve retornar. Sem executar a query.
	 * Mocks são utilizados em testes unitarios, afim de evitar a ida do sistema ao banco de dados.
	 * 
	 * O {@link @MockBean} é uma anotação como autowired, onde o spring fica-ra a cargo de iniciar o mock.
	 * Mesma coisa com o {@link @SpyBean}
	 * 
	 * A diferença entr Spy e Mock é apenas que o Mock nunca entra na classe que está mokando, mas o spy entra e faz o fluxo, a não ser que
	 * o resultado da função seja informado seja informado
	 */
	private UsuarioService service; // esse é um mock
	@SpyBean
	private UsuarioService serviceSpy;
	@MockBean
	private UsuarioRepository dao;
	
	@BeforeAll
	public void setUp() {
		// forma manual de iniciar o mocks e spy
//		dao = Mockito.mock(UsuarioRepository.class);
		service = mock(UsuarioService.class);
//		service = Mockito.spy(UsuarioService.class);
	}
	
	@BeforeEach
	public void setUsu() {
		usu = Usuario.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		helper = UsuarioHelper.builder().nome("Thiago").email("teste@gmail.com").senha("123").id(1L).build();
	}
	
	@Test
	public void existsByEmailErrorWithMock() {
		UsuarioHelper usu = UsuarioHelper.builder().email("teste@gmail.com").nome("teste").id(1L).senha("123").build();
		
		Mockito.when(dao.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		assertThrows(Exception.class, () -> {
			serviceSpy.cadastrar(usu);
		});
	}
	
	@Test
	public void cadastroSuccessComMock() {
		try {
			Mockito.when(dao.save(Mockito.any(Usuario.class))).thenReturn(usu);
			when(service.existsByEmail(Mockito.anyString())).thenReturn(false);
			
			Usuario resultado = this.usuarioService.cadastrar(helper);
			assertThat(resultado).isNotNull();
		} catch (UsuarioExisteException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validarEmail() {
		Mockito.when(dao.findByEmail(Mockito.anyString())).thenReturn(null);
		try {
			service.validarEmail(usu.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void validarEmailErrorComMock() {
		Mockito.when(dao.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usu));
		assertThrows(Exception.class, ()->{
			serviceSpy.validarEmail(usu.getEmail());
		});
		
		Throwable exception = catchThrowable(() -> {
			serviceSpy.validarEmail(usu.getEmail());
		});
		
		assertThat(exception).isInstanceOf(Exception.class).hasMessage("Email já utilizado.");
	}
	
	@Test
	public void listAll() {
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usu);
		Mockito.when(dao.findAll()).thenReturn(usuarios);
		assertThat(serviceSpy.listAll()).isNotNull().isNotEmpty();
	}
	
	@Test
	public void editarUsuarioByIdSuccess() {
		Usuario esperado = Usuario.builder().nome("Thiago").email("teste@gmail.com").senha("123").id(1L).build();
		
		Mockito.when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(usu));
		Mockito.when(dao.save(Mockito.any())).thenReturn(esperado);
		
		assertDoesNotThrow(() ->{
			Mockito.doNothing().when(serviceSpy).validarEmail(Mockito.anyString());
			Usuario usuarioRetornado = this.serviceSpy.editarUsuarioById(helper);
			assertEquals(esperado.getNome(), usuarioRetornado.getNome());
			/* para testar se o validar email foi utilizado, mas não fez nada, é necessario mandar não fazer nada e verificar uma função
			dentro do metodo. Nesse caso a função que não pode ser chamada é o findByEmail do dao, que está dentro do metodo validarEmail.*/
			verify(dao, Mockito.never()).findByEmail(Mockito.anyString());
		});
	}
	
	@Test
	public void editarUsuarioByIdErrorFindByEmailExists() {
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(usu));
		
		assertThrows(Exception.class, ()->{
			doNothing().when(service).validarEmail(Mockito.anyString());
			Usuario usuarioRetornado = this.serviceSpy.editarUsuarioById(helper);
		});
	}
	
	@Test
	public void editarUsuarioByIdErrorFindById() {
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(UsuarioNotFoundException.class, ()->{
			Usuario usuarioRetornado = this.usuarioService.editarUsuarioById(helper);
		});
	}
	
	@Test
	public void deletarUsuarioById() {
		doNothing().when(serviceSpy).deletarUsuarioById(Mockito.anyLong());
		serviceSpy.deletarUsuarioById(1L);
		verify(serviceSpy, times(1)).deletarUsuarioById(1L);
	}

	@Test
	public void deletarUsuarioByIdWithService() {
		doNothing().when(dao).deleteById(Mockito.anyLong());;
		assertDoesNotThrow(() ->{
			usuarioService.deletarUsuarioById(1L);
		});
		verify(dao, times(1)).deleteById(1L);
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
	public void getById() {
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(usu));
		Usuario result = usuarioService.getById(1L);
		assertThat(result).isNotNull();
	}

	@Test
	public void getByIdNull() {
		when(dao.findById(Mockito.anyLong())).thenReturn(null);
		Usuario result = usuarioService.getById(1L);
		assertThat(result).isNull();
	}

	@Test
	public void getByIdOptionalNull() {
		when(dao.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Usuario result = usuarioService.getById(1L);
		assertThat(result).isNull();
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
