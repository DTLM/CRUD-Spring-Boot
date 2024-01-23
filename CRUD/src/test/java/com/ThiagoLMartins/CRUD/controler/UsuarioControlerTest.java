package com.ThiagoLMartins.CRUD.controler;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ThiagoLMartins.CRUD.dto.UsuarioDto;
import com.ThiagoLMartins.CRUD.exception.UsuarioExisteException;
import com.ThiagoLMartins.CRUD.helper.UsuarioHelper;
import com.ThiagoLMartins.CRUD.modal.Usuario;
import com.ThiagoLMartins.CRUD.repository.UsuarioRepository;
import com.ThiagoLMartins.CRUD.service.imple.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(value =  {SpringExtension.class, MockitoExtension.class})
@ActiveProfiles("testes")
@WebMvcTest(controllers = UsuarioControler.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class UsuarioControlerTest {
	
	private static final String PATH = "/user";
	private static final MediaType JSON = MediaType.APPLICATION_JSON;
	private Usuario usu;
	private UsuarioHelper helper;
	private UsuarioDto dto;
	String json;
	
	@Autowired
	private MockMvc mvc;
	@MockBean
	private UsuarioRepository dao;
	@MockBean
	private UsuarioService service;
	
	@BeforeEach
	public void setUp() throws JsonProcessingException {
		Long id = 1L;
		String nome = "teste";
		String email = "teste@gmail.com";
		String senha = "123";
		helper = UsuarioHelper.builder().nome(nome).email(email).senha(senha).build();
		usu = Usuario.builder().email(email).nome(nome).id(id).senha(senha).build();
		dto = UsuarioDto.builder().email(email).nome(nome).id(id).senha(senha).build();
		json = new ObjectMapper().writeValueAsString(helper);
	}
	
	@Test
	@Order(1)
	public void salvarUsuario() throws Exception{
		when(service.cadastrar(Mockito.any())).thenReturn(usu);
		
		/*
		 * accept = o tipo que aceita como retorno da API
		 * contentType = tipo que está enviando para a api
		 * content = o corpo da requisição (body)
		 */
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(PATH.concat("/salvar"))
		.accept(JSON)
		.contentType(JSON)
		.content(json);
		
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("id").value(usu.getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usu.getNome()))
		.andExpect(MockMvcResultMatchers.jsonPath("email").value(usu.getEmail()))
		.andReturn().getResponse().getContentAsString();
		
//		assertThat(usu).isNotNull();
//		assertThat(usu.getEmail()).isNotBlank().isEqualToIgnoringCase("teste@gmail.com");
	}
	
	@Test
	@Order(2)
	public void salvarUsuarioErroBadRequest() throws Exception{
		Mockito.when(service.cadastrar(Mockito.any(UsuarioHelper.class))).thenThrow(UsuarioExisteException.class);
		
		/*
		 * accept = o tipo que aceita como retorno da API
		 * contentType = tipo que está enviando para a api
		 * content = o corpo da requisição (body)
		 */
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(PATH.concat("/salvar"))
				.accept(JSON)
				.contentType(JSON)
				.content(json);
		
		mvc.perform(request)
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
}
