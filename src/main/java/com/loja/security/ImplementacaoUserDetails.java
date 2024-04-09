package com.loja.security;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loja.model.Usuario;
import com.loja.repository.LoginRepository;

@Service
@Transactional
public class ImplementacaoUserDetails  implements UserDetailsService{
	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Usuario usuario = loginRepository.findUserByLogin(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuario não foi encontrado");
		}
		
		
		return new org.springframework.security.core.userdetails.User(usuario.getLogin(), 
				usuario.getPassword(), 
				usuario.isEnabled(), 
				true, true , true,
				usuario.getAuthorities() );
				
				
	}
}
