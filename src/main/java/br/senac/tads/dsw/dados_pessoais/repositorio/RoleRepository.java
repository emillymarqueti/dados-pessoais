package br.senac.tads.dsw.dados_pessoais.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.tads.dsw.dados_pessoais.entidade.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
