package com.loja.security;
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

import com.loja.security.ImplementacaoUserDetails;


@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementacaoUserDetails implementacaoUserDetails;
	
	@Override //Configurar as solicitações de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable() 
		.authorizeRequests() //Permitir restringir acessos
		.antMatchers(HttpMethod.GET, "/**", "/cadastrarUsuario/" ).permitAll() 
		
		.anyRequest().authenticated()
		.and().formLogin().permitAll() //Permite qualquer usuario
		.loginPage("/login")//Pagina de login 
		.defaultSuccessUrl("/") //Pagina que irá depois do login 
		.failureUrl("/login") //Se falhar o login
		.and().logout().logoutSuccessUrl("/login") //Mapeia a URL de sair do sistama e invalida o usuario de identificar
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override //Cria autenticação do usuario com banco de dados ou em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDetails)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
	}
	
	@Override //Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("/Nome_da_pasta/**");
	}
	
}
