package com.loja.controller;

import java.io.IOException;
import java.util.Optional;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.loja.model.Categoria;
import com.loja.model.Produto;
import com.loja.repository.CategoriaRepository;

@Controller
public class CategoriaController {

	private static String caminho = "C:\\Users\\Ramon\\Documents\\workspace-spring-tool-suite-4-4.16.1.RELEASE\\loja\\src\\main\\resources\\static\\img";
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	

	
	@RequestMapping("/")
	public ModelAndView home() {
		Iterable<Categoria> categoriaIte = categoriaRepository.findAll();
		ModelAndView viewCaminho = new ModelAndView("home");
		viewCaminho.addObject("categoria", categoriaIte);// Enviando para o formulario
		
		return viewCaminho;
	}
	

	
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroCategoria")
	public ModelAndView categoria() {
		ModelAndView andView = new ModelAndView("page/cadastroCategoria");

		Iterable<Categoria> categoriaIte = categoriaRepository.findAll();
		andView.addObject("categoria", categoriaIte);
		andView.addObject("obj", new Produto());
		
		return andView;
	}
	
	//Salvar com foto
		@RequestMapping(method = RequestMethod.POST, value = "/salvarCategoria", consumes = {"multipart/form-data"})
		public String salvarCategoria(Categoria categoria, final MultipartFile file) throws IOException {
		
			System.out.println("chegou");
			
			if(file.getSize() > 0) {
				categoria.setImagem(file.getBytes());
			}
			
			categoriaRepository.save(categoria);
			
			return "redirect:/cadastroCategoria";
		}
	
		//Retornar Foto
		
		@GetMapping("/imagemcategoria/{idcat}")
		@ResponseBody
		public byte[] exibirImagem(@PathVariable("idcat") Long idcat) {
			Categoria categoria = categoriaRepository.findById(idcat).get();
			return categoria.getImagem();
		}
		
		//Editar
		

		@GetMapping("/editarcategoria/{idcategoria}") //Nova anotação para GET
		public ModelAndView editar(@PathVariable("idcategoria") Long idcategoria) {
			
			
			Optional<Categoria> categoria = categoriaRepository.findById(idcategoria);
			
			ModelAndView andView = new ModelAndView( "page/cadastroCategoria"); //direcionar para tela
			andView.addObject("obj", categoria.get());
			

			Iterable<Categoria> categoriaIte = categoriaRepository.findAll();
			andView.addObject("categoria", categoriaIte);
			
			return andView;
		}
		//Excluir
		@GetMapping("/deletarcategoria/{idcategoria}") //Nova anotação para GET
		public String deletar(@PathVariable("idcategoria") Long idcategoria) {
			
			categoriaRepository.deleteById(idcategoria);
			ModelAndView andView = new ModelAndView( "page/cadastroCategoria"); //direcionar para tela
			
			andView.addObject("obj", new Produto());
			return "redirect:/cadastroCategoria";
		}
		
		
		
		
}
