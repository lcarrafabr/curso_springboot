package curso.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages="curso.springboot.model")//Scaneia os pacotes informados para entidades
@ComponentScan(basePackages= {"curso.*"})//escaneia os controllers
@EnableJpaRepositories(basePackages={"curso.springboot.repository"})//Faz o springboot reconhecer os repositories
@EnableTransactionManagement //Ativa as transações
@EnableWebMvc //Usar quando for criar a tela de login
public class SpringbootApplication implements WebMvcConfigurer{//Implementar o WebMvcConfigurer quando for criar a tela de login

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
	
	
	/**Criar o addViewControllers quando for criar a tela de login*/
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		 //Pode apagar tudo o que tiver dentro
		registry.addViewController("/login").setViewName("/login");
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
	}
	
	

}
