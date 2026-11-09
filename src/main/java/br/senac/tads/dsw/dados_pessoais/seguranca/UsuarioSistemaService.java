package br.senac.tads.dsw.dados_pessoais.seguranca;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.senac.tads.dsw.dados_pessoais.entidade.UsuarioSistemaEntity;
import br.senac.tads.dsw.dados_pessoais.repositorio.UsuarioSistemaRepository;

@Service
public class UsuarioSistemaService implements UserDetailsService {

	private final UsuarioSistemaRepository usuarioSistemaRepository;

	public UsuarioSistemaService(UsuarioSistemaRepository usuarioSistemaRepository) {
		this.usuarioSistemaRepository = usuarioSistemaRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException {

		UsuarioSistemaEntity usuario = usuarioSistemaRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(
				"Usuário não encontrado: " + username));

		List<GrantedAuthority> authorities = usuario.getRoles()
			.stream()
			.map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getNome()))
			.toList();

		return new UsuarioSistema(
			usuario.getUsername(),
			usuario.getSenha(),
			authorities
		);
	}
}
