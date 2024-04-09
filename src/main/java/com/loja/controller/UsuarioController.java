package com.loja.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.loja.model.Produto;
import com.loja.model.Usuario;
import com.loja.repository.LoginRepository;





@Controller
public class UsuarioController {
	
	
	@Autowired
	private LoginRepository loginRepository;
	
	
	
	@RequestMapping(value = "/cadastrarUsuario")
	public String cadastrarUsuario() {
	
		
		
		 	
	
		
		return "cadastrarUsuario";
	}
	
	@PostMapping(value = "/salvarCadastro")
	public String salvarCadastro(Usuario usuario) {
		
		
		
			
			
			System.out.println(usuario);
			
			loginRepository.save(usuario);
		
			 
		
		
		
		return "redirect:/cadastrarUsuario";
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/*/ajustesUsuario")
	public ModelAndView ajustesUsuario(Usuario usuario) {
	
		ModelAndView andView = new ModelAndView("page/ajustesUsuario");
		andView.addObject("obj", new Usuario()); //Para não da erro no Editar

		Iterable<Usuario> usuarioIte = loginRepository.findAll();
		andView.addObject("usuario", usuarioIte);
		//andView.addObject("pessoaobj", new Pessoa());
		
		return andView;
	}
	

	@PostMapping(value = "/*/salvarUsuario")
	public String salvar(Usuario usuario) {
		
		ModelAndView andView = new ModelAndView("page/ajustesUsuario");
		
		System.out.println(usuario);
		
		loginRepository.save(usuario);
		
		return "redirect:/page/ajustesUsuario";
	}
	
	
	
	@GetMapping("/editarusuario/{idusuario}") //Nova anotação para GET
	public ModelAndView editar(@PathVariable("idusuario") Long idusuario) {
		
		
		Optional<Usuario> usuario = loginRepository.findById(idusuario);
		
		ModelAndView andView = new ModelAndView( "page/ajustesUsuario"); //direcionar para tela
		andView.addObject("obj", usuario.get());
		

		Iterable<Usuario> usuarioIte = loginRepository.findAll();
		andView.addObject("usuario", usuarioIte);
		
		return andView;
	}
	
	@GetMapping("/deletarusuario/{idusuario}") //Nova anotação para GET
	public String deletar(@PathVariable("idusuario") Long idusuario) {
		
		loginRepository.deleteById(idusuario);
		ModelAndView andView = new ModelAndView( "page/ajustesUsuario"); //direcionar para tela
		//andView.addObject("pessoas",loginRepository.findAll());
		//andView.addObject("pessoaobj", new Pessoa());
		return "redirect:/page/ajustesUsuario";
	}
	
}
