package br.senac.tads.dsw.dados_pessoais;

import java.util.List;
import java.util.Optional;
import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public List<PessoaDto> obterPessoas() {
        return pessoaService.obterPessoas();
    }

    @GetMapping("/{username}")
    public PessoaDto obterPessoa(@PathVariable("username") String username) {
        Optional<PessoaDto> optPessoa = pessoaService.obterPessoa(username);
        if (optPessoa.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optPessoa.get();
    }

    @PostMapping("/sem-validacao")
    public ResponseEntity<?> incluirNovo(@RequestBody PessoaDto pessoa) {
        pessoaService.incluirNovaPessoa(pessoa);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/pessoas/{username}")
                .buildAndExpand(pessoa.getUsername())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping
    public ResponseEntity<?> incluirNovoComValidacao(@RequestBody @Valid PessoaDto pessoa) {
        pessoaService.incluirNovaPessoa(pessoa);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/pessoas/{username}")
                .buildAndExpand(pessoa.getUsername())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{username}")
    public PessoaDto alterarPessoa(@PathVariable("username") String username, @Valid @RequestBody PessoaAlteracaoDto dto) {
        return pessoaService.alterarPessoa(username, dto);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPessoa(@PathVariable("username") String username) {
        pessoaService.removerPessoa(username);
    }
}