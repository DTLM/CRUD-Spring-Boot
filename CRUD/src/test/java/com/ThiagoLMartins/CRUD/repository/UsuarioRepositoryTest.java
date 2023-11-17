package com.ThiagoLMartins.CRUD.repository;

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

import com.ThiagoLMartins.CRUD.exception.UsuarioNotFoundException;
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

/**
 * O @DataJpaTest serve para fazer testes em memoria, no caso inicia a bateria de testes e depois apaga tudo como no banco H2.
 * O @AutoConfigureTestDatabase(replace = Replace.NONE) é instanciado dentro do DataJpaTest para criar um banco em memoria, mas já temos nossas configurações
 * para o H2, então usa-se essa anotação para dizer ao DataJpaTest que não altere nada da configuração que fizemos para o banco e que use a que já existe.
 * 
 * @author Thiago Leal Martins
 *
 */

@ExtendWith(value = SpringExtension.class)
@ActiveProfiles("testes")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	private TestEntityManager maneger;
	
	@Test
	public void findByEmailTeste() {
		try {
			Usuario usu = new Usuario();
			usu.setNome("teste");
			usu.setEmail("teste@gmail.com");
			usu.setSenha("123");
			repository.save(usu);
			Boolean result = repository.existsByEmail(usu.getEmail());
			assertThat(result).isTrue();
			assertTrue(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
