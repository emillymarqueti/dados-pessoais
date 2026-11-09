package br.senac.tads.dsw.dados_pessoais.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.dados_pessoais.entidade.UsuarioSistemaEntity;

public interface UsuarioSistemaRepository
	extends JpaRepository<UsuarioSistemaEntity, Integer> {

	Optional<UsuarioSistemaEntity> findByUsername(String username);
}
