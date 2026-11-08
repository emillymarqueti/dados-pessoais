
package br.senac.tads.dsw.dados_pessoais;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import br.senac.tads.dsw.dados_pessoais.validacao.SenhasIguais;

@SenhasIguais
public class PessoaDto {

	private Integer id;

	@NotBlank(message = "O username é obrigatório")
	@Size(max = 64, message = "O username deve ter no máximo 64 caracteres")
	private String username;

	@NotBlank(message = "O nome é obrigatório")
	@Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
	private String nome;

	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "Informe um e-mail válido")
	@Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
	private String email;

	@Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
	private String telefone;

	@NotNull(message = "A data de nascimento é obrigatória")
	@PastOrPresent(message = "A data de nascimento não pode ser futura")
	private LocalDate dataNascimento;

	@NotBlank(message = "A senha é obrigatória")
	private String senha;

	@NotBlank(message = "A confirmação da senha é obrigatória")
	private String senhaRepeticao;

	private List<String> conhecimentos;

	public PessoaDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaRepeticao() {
		return senhaRepeticao;
	}

	public void setSenhaRepeticao(String senhaRepeticao) {
		this.senhaRepeticao = senhaRepeticao;
	}

	public List<String> getConhecimentos() {
		return conhecimentos;
	}

	public void setConhecimentos(List<String> conhecimentos) {
		this.conhecimentos = conhecimentos;
	}
}
