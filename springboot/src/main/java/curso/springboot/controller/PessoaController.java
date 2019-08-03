package curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	
	/**Metodo para salvar
	 * Com as alterações salva e ja mostra na tela.
	 * Se for um cadastro onde não tenha uma table, usar o que está comentado*/
	@RequestMapping(method=RequestMethod.POST, value="/salvarpessoa")
	//public String salvar(Pessoa pessoa) {
	public ModelAndView salvar(Pessoa pessoa) {
		
		pessoaRepository.save(pessoa);
		
		//-------------------- Essa parte so existe depois que existir o model and view ---------------------
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");//Vou manter na tela cadastro/cadastropessoa
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);//pessoas está no html <tr th:each = "pessoa : ${pessoas}">
		//-----------------------------------------------------------------------------------------------------
		
		//return "cadastro/cadastropessoa";//Apos salvar retorne para a mesma pagina
		return andView;
	}
	
	/**ModelAndView liga o modelo a tela (estou usando para fazer a lista*/
	@RequestMapping(method=RequestMethod.GET, value="/listapessoas")
	public ModelAndView pessoas() {
		
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");//Vou manter na tela cadastro/cadastropessoa
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);//pessoas está no html <tr th:each = "pessoa : ${pessoas}">
		
		return andView;
	}
	
}
