package com.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.loja.model.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends CrudRepository<Produto, Long> {


	@Query("select p from Produto p where p.categoria.id = ?1")
	public List<Produto> getProdutos(Long categoriaid);
	

	@Query("select p from Produto p where p.nome like %?1%")
	List<Produto> findPessoa(String nome);
	
	
}
