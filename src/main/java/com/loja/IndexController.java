package com.loja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.loja.model.Categoria;
import com.loja.model.Produto;
import com.loja.repository.CategoriaRepository;

@Controller
public class IndexController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	 /* @RequestMapping("/")
	  public ModelAndView listacategoria() {
		
		Iterable<Categoria> categoriaIte = categoriaRepository.findAll();
		ModelAndView viewCaminho = new ModelAndView("/index");
		viewCaminho.addObject("categoria", categoriaIte);// Enviando para o formulario

		return viewCaminho;
	}
	

	@RequestMapping("/")
	public String index() {
		Iterable<Categoria> categoriaIte = categoriaRepository.findAll();
		ModelAndView viewCaminho = new ModelAndView("/index");
		viewCaminho.addObject("categoria", categoriaIte);// Enviando para o formulario
		
		return "index";
	}*/
	


	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	

	
}
