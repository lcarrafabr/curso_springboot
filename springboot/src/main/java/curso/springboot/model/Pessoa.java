package curso.springboot.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length=100)
	@NotNull(message="O nome não pode ser nulo")
	@NotEmpty(message="O campo nome não pode ser vazio")
	private String nome;
	
	@Column(length=210)
	@NotNull(message="O sobrenome não pode ser nulo")
	@NotEmpty(message="O campo sobrenome não pode ser vazio")
	private String sobrenome;
	
	/**Criar essa lista após criar a classe de relação. Se um para muitos, criar essa linha após existir a classe muitos*/
	@OneToMany(mappedBy="pessoa", orphanRemoval=true, cascade=CascadeType.ALL) //MappedBy mapeia para o nome da variavel em que criou na tela telefones (private Pessoa pessoa;)
	private List<Telefone> telefones; //Usado para relacionar um para muitos (no caso uma pessoa para muitos telefones)
	
	@Min(value = 18, message="A idade não pode ser menor que 18 anos")
	private int idade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	public int getIdade() {
		return idade;
	}
	
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	
	public List<Telefone> getTelefones() {
		return telefones;
	}

}
