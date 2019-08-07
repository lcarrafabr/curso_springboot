package curso.springboot.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails { // Implemntar o User Details do Spring security (Depois clicar na lampada
												// e colocr os metodos)

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String login;

	private String senha;
	
	/**Usar OneToMany após criar o model de Roles pois um uruario pode ter varios Roles*/
	@OneToMany(fetch=FetchType.EAGER)//usar eager para sempre carregar
	@JoinTable(name="usuarios_role",
	joinColumns= @JoinColumn(name="usuario_id", referencedColumnName="id", table="usuario"),
	inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName="id", 
	table="role"))//Cria tabela de acesso do usuario
	private List<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return roles;//Inserir os roles após criar o role
	}

	@Override
	public String getPassword() {

		return senha;
	}

	@Override
	public String getUsername() {

		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;// Passar para true por enquanto (enquanto não usa)
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;// Passar para true por enquanto (enquanto não usa)
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;// Passar para true por enquanto (enquanto não usa)
	}

	@Override
	public boolean isEnabled() {
		return true;// Passar para true por enquanto (enquanto não usa)
	}

}
