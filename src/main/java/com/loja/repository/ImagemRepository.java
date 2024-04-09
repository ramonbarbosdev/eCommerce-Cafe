package com.loja.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.loja.model.Imagem;

@Repository
@Transactional
public interface ImagemRepository extends CrudRepository<Imagem, Long> {

	
}
