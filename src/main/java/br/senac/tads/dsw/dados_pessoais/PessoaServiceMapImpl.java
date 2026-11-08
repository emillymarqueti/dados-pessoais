
package br.senac.tads.dsw.dados_pessoais;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PessoaServiceMapImpl implements PessoaService {

	// Armazena as pessoas em memória, utilizando username como chave
	private final Map<String, PessoaDto> pessoas = new LinkedHashMap<>();

	@Override
	public List<PessoaDto> obterPessoas() {
		return new ArrayList<>(pessoas.values());
	}

	@Override
	public Optional<PessoaDto> obterPessoa(String username) {
		return Optional.ofNullable(pessoas.get(username));
	}

	@Override
	public PessoaDto incluirNovaPessoa(PessoaDto pessoa) {
		pessoas.put(pessoa.getUsername(), pessoa);
		return pessoa;
	}

	@Override
	public PessoaDto alterarPessoa(
		String username,
		PessoaAlteracaoDto pessoaAlteracao) {

		PessoaDto pessoa = pessoas.get(username);

		if (pessoa == null) {
			throw new NaoEncontradoException(
				"Pessoa " + username + " não encontrada");
		}

		pessoa.setNome(pessoaAlteracao.getNome());
		pessoa.setEmail(pessoaAlteracao.getEmail());
		pessoa.setTelefone(pessoaAlteracao.getTelefone());
		pessoa.setDataNascimento(pessoaAlteracao.getDataNascimento());
		pessoa.setConhecimentos(
			pessoaAlteracao.getConhecimentos());

		return pessoa;
	}

	@Override
	public void removerPessoa(String username) {

		if (!pessoas.containsKey(username)) {
			throw new NaoEncontradoException(
				"Pessoa " + username + " não encontrada");
		}

		pessoas.remove(username);
	}

	@Override
	public List<PessoaDto> buscarPessoas(String termo) {
		throw new UnsupportedOperationException(
			"Busca não implementada na versão com Map");
	}
}
