package br.com.vr.miniautorizador.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoNovaDTO implements Serializable {

    private Long numeroCartao;
    private String senhaCartao;
    private BigDecimal valor;
    
}
