package br.com.vr.miniautorizador.service;

import java.math.BigDecimal;

import br.com.vr.miniautorizador.model.dto.CartaoNovoDTO;
import br.com.vr.miniautorizador.model.dto.TransacaoNovaDTO;

public interface CartaoService {

    CartaoNovoDTO criarCartao(CartaoNovoDTO cartaoNovoDTO);

    BigDecimal consultarSaldoCartao(Long numeroCartao);

    String criarTransacao(TransacaoNovaDTO transacaoNova);

}
