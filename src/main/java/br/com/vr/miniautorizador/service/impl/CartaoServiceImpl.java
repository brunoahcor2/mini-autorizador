package br.com.vr.miniautorizador.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.model.dto.CartaoNovoDTO;
import br.com.vr.miniautorizador.model.dto.TransacaoNovaDTO;
import br.com.vr.miniautorizador.model.entity.Cartao;
import br.com.vr.miniautorizador.repository.CartaoRepository;
import br.com.vr.miniautorizador.service.CartaoService;

@Service
public class CartaoServiceImpl implements CartaoService {

    @Autowired
    private CartaoRepository repository;

    @Autowired
    private ModelMapper mm;

    @Override
    public CartaoNovoDTO criarCartao(CartaoNovoDTO cartaoNovoDTO) {        
        Cartao cartaoNovo = mm.map(cartaoNovoDTO, Cartao.class);
        cartaoNovo.setValor(new BigDecimal(500.0));
        try {
            Cartao cartao = repository.save(cartaoNovo);
            return mm.map(cartao, CartaoNovoDTO.class);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw dataIntegrityViolationException;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public BigDecimal consultarSaldoCartao(Long numeroCartao) {
        Optional<Cartao> cartaoOptional = repository.findByNumeroCartao(numeroCartao);
        if(cartaoOptional.isPresent()){
            Cartao cartao = cartaoOptional.get();
            return cartao.getValor().setScale(2, BigDecimal.ROUND_HALF_EVEN);
        } 
        return null;
    }

    @Override
    public String criarTransacao(TransacaoNovaDTO transacaoNova) {
        Optional<Cartao> cartaoOptional = repository.findByNumeroCartao(transacaoNova.getNumeroCartao());
        if(!cartaoOptional.isPresent()){
            return "CARTAO_INEXISTENTE";
        }
        if(!transacaoNova.getSenhaCartao().equals(cartaoOptional.get().getSenha())){
            return "SENHA_INVALIDA";
        }
        if(transacaoNova.getValor().compareTo(cartaoOptional.get().getValor()) == 1){
            return "SALDO_INSUFICIENTE";
        }
        return "OK";
    }

}
