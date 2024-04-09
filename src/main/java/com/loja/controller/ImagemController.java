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
import com.loja.model.Imagem;
import com.loja.model.Produto;
import com.loja.repository.ImagemRepository;



@Controller
public class ImagemController {

		@Autowired
		private ImagemRepository imagemRepository;
		
		@RequestMapping(method = RequestMethod.GET, value = "/ajustesImagens")
		public ModelAndView imagens() {
			ModelAndView andView = new ModelAndView("page/ajustesImagens");

			
			return andView;
		}
		
		//Salvar com foto
				@RequestMapping(method = RequestMethod.POST, value = "/salvarFoto", consumes = {"multipart/form-data"})
				public String salvarFoto(Imagem imagem, final MultipartFile file) throws IOException {
				
					System.out.println("chegou");
					
					if(file.getSize() > 0) {
						imagem.setImagens(file.getBytes());
					}
					
					imagemRepository.save(imagem);
					
					return "page/ajustesImagens";
				}
			
				//Carrossel
				
				//Slider 1
				@GetMapping("/img")
				@ResponseBody
				public byte[] img() {
					long id = 1;
					Imagem imagem = imagemRepository.findById(id).get();
					return imagem.getImagens();
				}
				
				//Slider 2
				@GetMapping("/img2")
				@ResponseBody
				public byte[] img2() {
					long id = 2;
					Imagem imagem = imagemRepository.findById(id).get();
					return imagem.getImagens();
				}
				
				//Slider 3
				@GetMapping("/img3")
				@ResponseBody
				public byte[] img3() {
					long id = 3;
					Imagem imagem = imagemRepository.findById(id).get();
					return imagem.getImagens();
				}
}
