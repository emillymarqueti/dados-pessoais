package br.senac.tads.dsw.dados_pessoais;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import br.senac.tads.dsw.dados_pessoais.entidade.PessoaEntity;
import br.senac.tads.dsw.dados_pessoais.entidade.ConhecimentoEntity;
import br.senac.tads.dsw.dados_pessoais.repositorio.PessoaRepository;
import br.senac.tads.dsw.dados_pessoais.repositorio.ConhecimentoRepository;

@Service
@Primary
public class PessoaServiceJpa implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ConhecimentoRepository conhecimentoRepository;

    public PessoaServiceJpa(PessoaRepository pessoaRepository, ConhecimentoRepository conhecimentoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.conhecimentoRepository = conhecimentoRepository;
    }

    @Override
    public List<PessoaDto> obterPessoas() {
        return pessoaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PessoaDto> obterPessoa(String username) {
        return pessoaRepository.findByUsername(username)
                .map(this::toDto);
    }

    @Override
    public PessoaDto incluirNovaPessoa(PessoaDto pessoaDto) {
        PessoaEntity entity = toEntity(pessoaDto);
        PessoaEntity salva = pessoaRepository.save(entity);
        return toDto(salva);
    }

    @Override
    public PessoaDto alterarPessoa(String username, PessoaAlteracaoDto dto) {
        PessoaEntity entity = pessoaRepository.findByUsername(username)
                .orElseThrow(() -> new NaoEncontradoException("Pessoa " + username + " não encontrada"));

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setDataNascimento(dto.getDataNascimento());

        // Atualizar conhecimentos se necessário
        if (dto.getConhecimentos() != null) {
            entity.getConhecimentos().clear();
            for (String nomeConhecimento : dto.getConhecimentos()) {
                ConhecimentoEntity conhecimento = conhecimentoRepository.findByNome(nomeConhecimento)
                        .orElseGet(() -> conhecimentoRepository.save(new ConhecimentoEntity(nomeConhecimento)));
                entity.getConhecimentos().add(conhecimento);
            }
        }

        PessoaEntity atualizada = pessoaRepository.save(entity);
        return toDto(atualizada);
    }

    @Override
    public void removerPessoa(String username) {
        PessoaEntity entity = pessoaRepository.findByUsername(username)
                .orElseThrow(() -> new NaoEncontradoException("Pessoa " + username + " não encontrada"));
        pessoaRepository.delete(entity);
    }

    @Override
    public List<PessoaDto> buscarPorTermo(String termo) {
        return pessoaRepository.buscarPorTermo(termo).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares de conversão (Entity <-> DTO)
    private PessoaDto toDto(PessoaEntity entity) {
        List<String> conhecimentos = entity.getConhecimentos() != null 
            ? entity.getConhecimentos().stream().map(ConhecimentoEntity::getNome).collect(Collectors.toList())
            : List.of();

        PessoaDto dto = new PessoaDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setTelefone(entity.getTelefone());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setSenha(entity.getSenha());
        dto.setConhecimentos(conhecimentos);
        return dto;
    }

    private PessoaEntity toEntity(PessoaDto dto) {
        PessoaEntity entity = new PessoaEntity();
        entity.setUsername(dto.getUsername());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setSenha(dto.getSenha());

        if (dto.getConhecimentos() != null) {
            for (String nomeConhecimento : dto.getConhecimentos()) {
                ConhecimentoEntity conhecimento = conhecimentoRepository.findByNome(nomeConhecimento)
                        .orElseGet(() -> conhecimentoRepository.save(new ConhecimentoEntity(nomeConhecimento)));
                entity.getConhecimentos().add(conhecimento);
            }
        }

        return entity;
    }
}