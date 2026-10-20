package br.senac.tads.dsw.dados_pessoais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class PessoaServiceMapImpl implements PessoaService {
    private AtomicInteger contador = new AtomicInteger(0);
    private Map<String, PessoaDto> mapPessoas = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // Adiciona algumas pessoas de exemplo
        PessoaDto p1 = new PessoaDto();
        p1.setId(contador.incrementAndGet());
        p1.setUsername("Isabele");
        p1.setNome("Isabele Laranjo Souto");
        p1.setEmail("isabelelaranjo@gmail.com");
        p1.setTelefone("11 99999-9999");
        p1.setDataNascimento(LocalDate.parse("2005-01-14"));
        mapPessoas.put(p1.getUsername(), p1);

        PessoaDto p2 = new PessoaDto();
        p2.setId(contador.incrementAndGet());
        p2.setUsername("Emilly");
        p2.setNome("Emilly Marquetti");
        p2.setEmail("emillymarqueti07@gmail.com");
        p2.setTelefone("11 88888-8888");
        p2.setDataNascimento(LocalDate.parse("2006-10-10"));
        mapPessoas.put(p2.getUsername(), p2);

        PessoaDto p3 = new PessoaDto();
        p3.setId(contador.incrementAndGet());
        p3.setUsername("Ana");
        p3.setNome("Ana Beatriz");
        p3.setEmail("anabeatriz@gmail.com");
        p3.setTelefone("11 77777-7777");
        p3.setDataNascimento(LocalDate.parse("2004-05-20"));
        mapPessoas.put(p3.getUsername(), p3);
    }

    @Override
    public List<PessoaDto> obterPessoas() {
        return new ArrayList<>(mapPessoas.values());
    }

    @Override
    public Optional<PessoaDto> obterPessoa(String username) {
        return Optional.ofNullable(mapPessoas.get(username));
    }

    @Override
    public PessoaDto incluirNovaPessoa(PessoaDto pessoa) {
        pessoa.setId(contador.incrementAndGet());
        mapPessoas.put(pessoa.getUsername(), pessoa);
        return pessoa;
    }

    @Override
    public PessoaDto alterarPessoa(String username, PessoaAlteracaoDto pessoaAlteracao) {
        return null;
    }

    @Override
    public void removerPessoa(String username) {
        mapPessoas.remove(username);
    }
}