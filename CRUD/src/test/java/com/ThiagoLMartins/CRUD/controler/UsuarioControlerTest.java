package com.ThiagoLMartins.CRUD.controler;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;
import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.service.IUsuarioService;
import com.ThiagoLMartins.CRUD.service.imple.UsuarioService;

@AutoConfigureMockMvc
@WebMvcTest(controllers = UsuarioControler.class)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(value = SpringExtension.class)
@SpringBootTest
@ActiveProfiles("testes")
public class UsuarioControlerTest {

	// Constantes
	private final String ROUTE = "/user/";
	private final MediaType JSON = MediaType.APPLICATION_JSON;
	
	private Usuario usu;
	private UsuarioHelper helper;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IUsuarioService usuarioService;
	
	@MockBean
	private UsuarioService service;
	
	@BeforeEach
	void setUp() {
		String email = "teste@gmail.com";
		String nome = "teste";
		String senha = "123";
		usu = Usuario.builder().email(email).nome(nome).id(1L).senha(senha).build();
		helper = UsuarioHelper.builder().email(email).nome(nome).id(1L).senha(senha).build();
	}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
