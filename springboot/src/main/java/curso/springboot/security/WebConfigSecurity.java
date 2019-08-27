package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailService implementacaoUserDetailService; // Usar essa linha quando for validar no banco
	
	@Override //Configura as solicitações de acesso por HTTP
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf()
		.disable()//Desabilita as configurações padrão de memoria do Spring.
		.authorizeRequests()//Permitir restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll()//Qualquer usuario acessa a pagina inicial
		.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN")//Travei a pagina cadastropessoa somente para admin (não informa o ROLE_)
		.anyRequest().authenticated()
		.and().formLogin().permitAll()//Permite qualquer usuário no formulario de login
		.loginPage("/login")//Somente usar essa linha quando existir uma pagina de login (se não usar o spring cria uma pagina de login
		.defaultSuccessUrl("/cadastropessoa")//Quando o login for realizado com sucesso ir direto para a pagina cadastro pessoa
		.failureUrl("/login?error=true")//Se falhar, voltar para a pagina de login e enviar um parametro error=true
		//.and().logout()//Mapeia URL de Logout e invalida usuario autenticado (Usar quando for logout em memoria
		.and().logout().logoutSuccessUrl("/cadastro/login")//Quando for deslogado com sucesso, voltar a pagina de login criada por mim
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override //Cria autenticação do usuario com banco de dados ou em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		/**usar as linhas abaixo para validar no banco de dados o usuario*/
		auth.userDetailsService(implementacaoUserDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
		
		/**usar as linhas comentadas quando for usar validação em memoria*/
		//auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()) // se for usar em memoria
		//auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()) //Usar essa linha  para usar senha criptografada
		//.withUser("vitiazze")
		//.password("123")
		//.roles("ADMIN");
	}
	
	@Override //Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/materialize/**");//Para que o usuario possa ter carregados os CSS e JS
	}

}
