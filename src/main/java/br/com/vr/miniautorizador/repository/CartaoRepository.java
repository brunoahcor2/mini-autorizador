package br.com.vr.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vr.miniautorizador.model.entity.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    
}
