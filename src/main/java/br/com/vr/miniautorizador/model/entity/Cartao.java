package br.com.vr.miniautorizador.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartao_sequence")
    @SequenceGenerator(name = "cartao_sequence", sequenceName = "seq_cartao")
    private Long id;

    @Column(unique = true, nullable = false)
    private Long numeroCartao;

    private String senha;

    private BigDecimal valor;

}
