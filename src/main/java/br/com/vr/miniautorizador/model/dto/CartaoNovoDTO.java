package br.com.vr.miniautorizador.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoNovoDTO implements Serializable {

    private Long numeroCartao;
    private String senha;
    
}
