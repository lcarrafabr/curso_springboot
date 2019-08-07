package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Usuario;
import curso.springboot.repository.UsuarioRepository;

@Service
@Transactional
public class ImplementacaoUserDetailService implements UserDetailsService{//Implementar UserDetailService e aplicar na lampada o unico metodo 

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findUserLogin(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario não foi encontrado!");
		}
		
		//return usuario; Usar esse antes de ter os roles
		return new User(usuario.getLogin(), 
				usuario.getSenha(), 
				usuario.isEnabled(), 
				usuario.isAccountNonExpired(), 
				usuario.isCredentialsNonExpired(),
				usuario.isAccountNonLocked(),
				usuario.getAuthorities()
				);
	}

}
