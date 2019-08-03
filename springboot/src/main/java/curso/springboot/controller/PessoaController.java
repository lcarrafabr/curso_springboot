package curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository; //Após criar o Repository pessoa, tem que vir na controller e fazer a injeção de dependencia
	
	/**Metodo para enviar para a pagina de cadastro*/
	@RequestMapping(method=RequestMethod.GET, value="/cadastropessoa")
	public String inicio() {
		
		return "cadastro/cadastropessoa";
	}
	
	/**Metodo para salvar*/
	@RequestMapping(method=RequestMethod.POST, value="/salvarpessoa")
	public String salvar(Pessoa pessoa) {
		
		pessoaRepository.save(pessoa);
		
		return "cadastro/cadastropessoa";//Apos salvar retorne para a mesma pagina
	}
	
}
