package br.com.vr.miniautorizador.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.model.dto.CartaoNovoDTO;
import br.com.vr.miniautorizador.model.dto.TransacaoNovaDTO;
import br.com.vr.miniautorizador.service.CartaoService;

@RestController
public class AutorizadorController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping(value = "/cartoes")
    public ResponseEntity<?> criarCartao(@RequestBody CartaoNovoDTO cartaoNovoDTO) {
        try {
            return new ResponseEntity<>(cartaoService.criarCartao(cartaoNovoDTO), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            return new ResponseEntity<>(cartaoNovoDTO, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/cartoes/{numeroCartao}")
    public ResponseEntity<?> consultarSaldoCartao(@PathVariable Long numeroCartao) {
        BigDecimal retorno = cartaoService.consultarSaldoCartao(numeroCartao);
        if(retorno == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        }        
    }

    @PostMapping(value = "/transacoes")
    public ResponseEntity<?> criarTransacao(@RequestBody TransacaoNovaDTO transacaoNova) {    
        String retorno = cartaoService.criarTransacao(transacaoNova);
        if(HttpStatus.OK.name().equals(retorno)){
            return new ResponseEntity<>(HttpStatus.OK.name(), HttpStatus.CREATED);
        } 
        return new ResponseEntity<>(retorno, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
