package com.loja.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.loja.model.Produto;

import com.loja.model.Compra;
import com.loja.model.ItensCompra;

import com.loja.model.Categoria;
import com.loja.repository.CategoriaRepository;

import com.loja.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	private Long categoria;
	
	//private static String imgCarrossel = "C:\\Users\\GPI-005\\Documents\\workspace-spring-tool-suite-4-4.16.1.RELEASE\\loja\\src\\main\\resources\\templates\\img";
	
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	private List<ItensCompra> itensCompras = new ArrayList<ItensCompra>();
	private Compra compra = new Compra();
	
	private void calculatTotal() {
		compra.setValorTotal(0.);
		for (ItensCompra it : itensCompras) {
			compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
		}
	}
	
	
	

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroProduto")
	public ModelAndView cadastroProduto() {
		ModelAndView andView = new ModelAndView("page/cadastroProduto");
		
		Iterable<Produto> produtoIte = produtoRepository.findAll();
		andView.addObject("produto", produtoIte);
		andView.addObject("obj", new Produto());
		
		return andView;
	}
	

	//Salvar com foto
	@RequestMapping(method = RequestMethod.POST, value = "/*/salvarProduto", consumes = {"multipart/form-data"})
	public String salvarProduto(Produto produto, final MultipartFile file) throws IOException {
	
		System.out.println("chegou");
		
		if(file.getSize() > 0) {
			produto.setImagem(file.getBytes());
		}
		
		produtoRepository.save(produto);
		
		return "redirect:/cadastroProduto";
	}
	//Retornar Foto
	
	@GetMapping("/imagem/{idprod}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("idprod") Long idprod) {
		Produto produto = produtoRepository.findById(idprod).get();
		return produto.getImagem();
	}
	
	
	//Editar
	

	@GetMapping("/editarproduto/{idproduto}") //Nova anotação para GET
	public ModelAndView editar(@PathVariable("idproduto") Long idproduto) {
		
		
		Optional<Produto> produto = produtoRepository.findById(idproduto);
		
		ModelAndView andView = new ModelAndView( "page/cadastroProduto"); //direcionar para tela
		andView.addObject("obj", produto.get());
		

		Iterable<Produto> produtoIte = produtoRepository.findAll();
		andView.addObject("produto", produtoIte);
		
		return andView;
	}
	
	//Excluir
	@GetMapping("/deletarproduto/{idproduto}") //Nova anotação para GET
	public String deletar(@PathVariable("idproduto") Long idproduto) {
		
		produtoRepository.deleteById(idproduto);
		ModelAndView andView = new ModelAndView( "/cadastroProduto"); //direcionar para tela
		
		andView.addObject("obj", new Produto());
		return "redirect:/cadastroProduto";
	}
	
	
	//AÇÕES
	
	
	@RequestMapping("/produto")
	public ModelAndView produto(Long idcate) {
		ModelAndView andView = new ModelAndView("produto");

		idcate = this.categoria;

		andView.addObject("liProd", produtoRepository.getProdutos(idcate));// Enviando para o formulario

		return andView;
	}
	

	
	

	@GetMapping("/produto/{idcate}") // Nova anotação para GET
	public String produtoID(@PathVariable("idcate") Long idcate) {

		this.categoria = idcate;

		System.out.println(idcate);
		Produto prod = produtoRepository.findById(categoria).orElse(null); // Buscar no Banco de dados
		

		ModelAndView andView = new ModelAndView("/produto"); // direcionar para tela
		andView.addObject("liProd", prod);// Enviar o produto novamente para a tela

		return "redirect:/produto";
	}
	
	@PostMapping("/pesquisarproduto")
	public String pesquisarproduto(@RequestParam("nomeBusca") String nomeBusca) {
		
		ModelAndView andView = new ModelAndView( "/produto"); //direcionar para tela
		andView.addObject("liProd",produtoRepository.findPessoa(nomeBusca));
		//andView.addObject("pessoaobj", new Pessoa());
		return "redirect:/produto";
	}
	
	
	@GetMapping("/addCarrinho/{idprod}")
	public String addCarrinho( @PathVariable("idprod") Long idprod) {

		Optional<Produto> prod = produtoRepository.findById(idprod); // Buscar o produto no Banco de dados
		Produto produto = prod.get();

		int controle = 0;

		for (ItensCompra it : itensCompras) { // Pecorrer lista
			if (it.getProduto().getId().equals(produto.getId())) {
				
				it.setQuantidade(it.getQuantidade() + 1);
				it.setValorTotal(0.);
				it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				controle = 1;
				
				break;
			}
		}
		if (controle == 0) {
			ItensCompra item = new ItensCompra();
			item.setNome(produto.getNome());
			item.setProduto(produto);
			item.setValorUnitario(produto.getValor());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));
			itensCompras.add(item);
		}

		ModelAndView andViewCAR = new ModelAndView("page/carrinho");
		andViewCAR.addObject("listaItens", itensCompras);// Enviar o produto novamente para a tela

		Long idcate = this.categoria;
		ModelAndView andView = new ModelAndView("/produto"); // Direciona para a pagina
		andView.addObject("liProd", produtoRepository.getProdutos(idcate));// Enviando para o formulario

		return "redirect:/produto";
	}
	

	@RequestMapping(value = "/carrinho")
	public ModelAndView listaCarrinho() {
		ModelAndView andViewCAR = new ModelAndView("page/carrinho");

		calculatTotal();
		andViewCAR.addObject("compra", compra);

		andViewCAR.addObject("listaItens", itensCompras);// Enviar o produto novamente para a tela
		return andViewCAR;
	}

	@GetMapping(value = "/alterarQuantidade/{id}/{acao}")
	public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {

		for (ItensCompra it : itensCompras) { // Pecorrer lista
			if (it.getProduto().getId().equals(id)) {// Se meu produto+id for igual o parametro
				if (acao.equals(1)) {
					it.setQuantidade(it.getQuantidade() + 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				} else if (acao == 0) {
					it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					 it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()) );
					
				}
				break;
			}
		}

		ModelAndView andViewCAR = new ModelAndView("page/carrinho");
		andViewCAR.addObject("listaItens", itensCompras);// Enviar o produto novamente para a tela

		return "redirect:/carrinho";
	}
	@GetMapping(value = "/deletarItem/{id}")
	public String deletarItem(@PathVariable Long id) {

		for (ItensCompra it : itensCompras) { // Pecorrer lista
			if (it.getProduto().getId().equals(id)) {// Se meu produto+id for igual o parametro
				
				
				itensCompras.remove(it);
				
				break;
			}
			
		}
		
		
		ModelAndView andViewCAR = new ModelAndView("page/carrinho");
		andViewCAR.addObject("listaItens", itensCompras);// Enviar o produto novamente para a tela
		return "redirect:/carrinho";
	}
	
	@GetMapping(value = "/deletarTudo")
	public String deletarItem() {

		for (ItensCompra it : itensCompras) { // Pecorrer lista
			
			 while (!it.equals(0))
		        {
		        	itensCompras.remove(it);
		        	it.setValorTotal(0.);
		        	
		        }
	
			 System.out.println("deletou tudo");	

		}

		return "redirect:/produto";
	}
	

	@GetMapping(value = "/finalizarCarrinho")
	public String finalizarCar() {

		for (ItensCompra it : itensCompras) { // Pecorrer lista
			
			 while (!it.equals(0))
		        {
		        	itensCompras.remove(it);
		        	
		        }
	
			 System.out.println("deletou tudo");	

		}
		
		return "redirect:/finalizar";
	}
	
	@RequestMapping("/finalizar")
	public String finalizar() {
		
		
		return "page/finalizar";
	}
	

	
}
