package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository; //Após criar o Repository pessoa, tem que vir na controller e fazer a injeção de dependencia
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	/**Metodo para enviar para a pagina de cadastro*/
	@RequestMapping(method=RequestMethod.GET, value="/cadastropessoa")
	//public String inicio() {
	public ModelAndView inicio() {//Se for retornar apenas a pagina usar a linha de cima, se precisar que retorne dados usar essa
		
		//------------- INCLUIR ESSE CODIGO QUANDO FOR EDITAR -------------------------
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		//----------------------------------------------------------------------------
		
		/**Essas linhas abaixo são para carregar os dados automaticamente ao abrir o fomulario*/
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		modelAndView.addObject("pessoas", pessoasIt);
		
		return modelAndView;
	}
	
	/**Metodo para salvar
	 * Com as alterações salva e ja mostra na tela.
	 * Se for um cadastro onde não tenha uma table, usar o que está comentado*/
	@RequestMapping(method=RequestMethod.POST, value="**/salvarpessoa")//Dois ** ignoram tudo o que existe antes (util para editar)
	//public String salvar(Pessoa pessoa) {
	//public ModelAndView salvar(Pessoa pessoa) {//Sem validador
	public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {//Com validador
		
		
		/**Se houver validador se campos vaios usar as linhas do IF*/
		if(bindingResult.hasErrors()) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
			modelAndView.addObject("pessoas", pessoasIt);//pessoas está no html <tr th:each = "pessoa : ${pessoas}">
			modelAndView.addObject("pessoaobj", pessoa);
			
			List<String> msg = new ArrayList<String>();
			
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); //O getDefaultMessage pega as mensagem das anotações nos models @NotEmpty e outras
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		
		pessoaRepository.save(pessoa);
		
		//-------------------- Essa parte so existe depois que existir o model and view ---------------------
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");//Vou manter na tela cadastro/cadastropessoa
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);//pessoas está no html <tr th:each = "pessoa : ${pessoas}">
		andView.addObject("pessoaobj", new Pessoa());//Após salvar será necessário passar um objeto vazio (foi obrigatorio após fazer alterações para editar
		//-----------------------------------------------------------------------------------------------------
		
		//return "cadastro/cadastropessoa";//Apos salvar retorne para a mesma pagina
		return andView;
	}
	
	/**Metodo para listar*/
	/**ModelAndView liga o modelo a tela (estou usando para fazer a lista*/
	@RequestMapping(method=RequestMethod.GET, value="/listapessoas")
	public ModelAndView pessoas() {
		
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");//Vou manter na tela cadastro/cadastropessoa
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);//pessoas está no html <tr th:each = "pessoa : ${pessoas}">
		andView.addObject("pessoaobj", new Pessoa());//Após salvar será necessário passar um objeto vazio (foi obrigatorio após fazer alterações para editar
		
		return andView;
	}
	
	/**Metodo Editar*/
	/**GetMapping é a mesma coisa do requestMapping porpem já informo que o tipo é GET*/
	@GetMapping("editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {//no pathvariable uso o id que coloquei no html na grade editar <td><a th:href="@{/editarpessoa/{idpessoa}(idpessoa=${pessoa.id})}">Editar</a></td>
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);//O findById retona um Optional.
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", pessoa.get());
		
		return modelAndView;
	}
	
	/**metodo Excluir*/
	/**GetMapping é a mesma coisa do requestMapping porpem já informo que o tipo é GET*/
	@GetMapping("removerpessoa/{idpessoa}")
	public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {//no pathvariable uso o id que coloquei no html na grade editar <td><a th:href="@{/editarpessoa/{idpessoa}(idpessoa=${pessoa.id})}">Editar</a></td>
		
		pessoaRepository.deleteById(idpessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
	}
	
	/**Pesquisar por pessoa*/
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		modelAndView.addObject("pessoaobj", new Pessoa());
		
		return modelAndView;
	}
	
	
	@GetMapping("telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {//no pathvariable uso o id que coloquei no html na grade editar <td><a th:href="@{/editarpessoa/{idpessoa}(idpessoa=${pessoa.id})}">Editar</a></td>
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);//O findById retona um Optional.
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa.get());
		
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));//Colocar para carregar todos os telefones ao abrir a tela
		
		return modelAndView;
	}
	
	@PostMapping("**/addfonepessoa/{pessoaid}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		
		/**Validaçãoes pelo lado do servidor*/
		if(telefone != null && (telefone.getNumero() != null && telefone.getNumero().isEmpty()) || telefone.getNumero() == null) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("pessoaobj", pessoa);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
			
			List<String> msg = new ArrayList<>();
			msg.add("O numero de telefone deve ser informado!");
			modelAndView.addObject("msg", msg);
			
			return modelAndView;
		}
		
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid)); //Necessario para c arregar a lista de telefones por pessoa (necessario atualiar repository)
 		
		
		return modelAndView;
	}
	
	
	/**metodo Excluir*/
	/**GetMapping é a mesma coisa do requestMapping porpem já informo que o tipo é GET*/
	@GetMapping("removertelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idtelefone) {//no pathvariable uso o id que coloquei no html na grade editar <td><a th:href="@{/editarpessoa/{idpessoa}(idpessoa=${pessoa.id})}">Editar</a></td>
		
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();//carrego o objeto pessoa para a variavel pessoa.
		
		telefoneRepository.deleteById(idtelefone);//Deleto o telefone
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");//Volto para a mesma tela
		modelAndView.addObject("pessoaobj", pessoa);//carrego a pessoa novamente
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));//carrega os telefones dessa pessoa
		
		return modelAndView;
	}
	
	/**Metodo Editar*/
	/**GetMapping é a mesma coisa do requestMapping porpem já informo que o tipo é GET*/
	@GetMapping("editartelefone/{idtelefone}")
	public ModelAndView editarTelefone(@PathVariable("idtelefone") Long idtelefone) {//no pathvariable uso o id que coloquei no html na grade editar <td><a th:href="@{/editarpessoa/{idpessoa}(idpessoa=${pessoa.id})}">Editar</a></td>
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idtelefone);//O findById retona um Optional.
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(idtelefone));//carrega os telefones dessa pessoa
		
		return modelAndView;
	}
	
}
