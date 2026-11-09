package br.senac.tads.dsw.dados_pessoais;

import br.senac.tads.dsw.dados_pessoais.seguranca.UsuarioSistema;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerJwt")
@RestController
public class ExemploSecurityController {

	private final UserDetailsService userDetailsService;

	// Injeta o UserDetailsService para carregar o UsuarioSistema
	// completo pelo username.
	public ExemploSecurityController(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	// Acessível apenas para usuários autenticados.
	@GetMapping("/me")
	public UsuarioSistema obterDadosUsuarioLogado(Authentication auth) {
		String username = (String) auth.getPrincipal();

		return (UsuarioSistema) userDetailsService.loadUserByUsername(username);
	}

	// Record que representa a resposta dos endpoints de acesso por role.
	public record MensagemRoleDto(String usuario, String mensagem) {}

	// Endpoint destinado a usuários com ROLE_ADMIN.
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/acesso/admin")
	public MensagemRoleDto mensagemAdmin(Authentication auth) {
		return new MensagemRoleDto(
			(String) auth.getPrincipal(),
			"Mensagem para usuário com Role ADMIN"
		);
	}

	// Endpoint destinado a usuários com ROLE_GERENTE.
	@PreAuthorize("hasRole('GERENTE')")
	@GetMapping("/acesso/gerente")
	public MensagemRoleDto mensagemGerente(Authentication auth) {
		return new MensagemRoleDto(
			(String) auth.getPrincipal(),
			"Mensagem para usuário com Role GERENTE"
		);
	}

	// Endpoint destinado a usuários com ROLE_USER.
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/acesso/user")
	public MensagemRoleDto mensagemUser(Authentication auth) {
		return new MensagemRoleDto(
			(String) auth.getPrincipal(),
			"Mensagem para usuário com Role USER"
		);
	}
}
