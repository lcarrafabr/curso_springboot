package curso.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages="curso.springboot.model")//Scaneia os pacotes informados para entidades
@ComponentScan(basePackages= {"curso.*"})//escaneia os controllers
@EnableJpaRepositories(basePackages={"curso.springboot.repository"})//Faz o springboot reconhecer os repositories
@EnableTransactionManagement //Ativa as transações
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
