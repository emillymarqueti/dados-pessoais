package br.senac.tads.dsw.dados_pessoais;

import java.util.List;
import java.util.Optional;

public class PessoaServiceMapImpl implements PessoaService {

    @Override
    public List<PessoaDto> obterPessoas() {
        // Implementação original com Map mantida para referência
        return List.of();
    }

    @Override
    public Optional<PessoaDto> obterPessoa(String username) {
        return Optional.empty();
    }

    @Override
    public PessoaDto incluirNovaPessoa(PessoaDto pessoa) {
        return pessoa;
    }

    @Override
    public PessoaDto alterarPessoa(String username, PessoaAlteracaoDto pessoaAlteracao) {
        return null;
    }

    @Override
    public void removerPessoa(String username) {
    }

    @Override
    public List<PessoaDto> buscarPessoas(String termo) {
        throw new UnsupportedOperationException("Busca não implementada na versão com Map");
    }
}