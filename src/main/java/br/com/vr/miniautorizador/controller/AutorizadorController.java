package br.com.vr.miniautorizador.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.model.dto.CartaoNovoDTO;
import br.com.vr.miniautorizador.model.dto.TransacaoNovaDTO;

@RestController
public class AutorizadorController {

    @PostMapping(value = "/cartoes")
    public ResponseEntity<?> criarCartao(@RequestBody CartaoNovoDTO cartaoNovo) {
        return new ResponseEntity<>(cartaoNovo, HttpStatus.CREATED);
    }

    @GetMapping(value = "/cartoes/{numeroCartao}")
    public ResponseEntity<?> consultarSaldoCartao(@PathVariable Long numeroCartao) {
        BigDecimal retorno = new BigDecimal(495.15).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    @PostMapping(value = "/transacoes")
    public ResponseEntity<?> criarTransacao(@RequestBody TransacaoNovaDTO transacaoNova) {
        return new ResponseEntity<>(HttpStatus.OK.name().toString(), HttpStatus.OK);
    }

}
