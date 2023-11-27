package com.ThiagoLMartins.CRUD.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ThiagoLMartins.CRUD.modal.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, CrudRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	@Query("select u from Usuario u where u.id = :id")
	Usuario getById(@Param(value = "id") Long id);
}
